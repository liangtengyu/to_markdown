/*
 * Copyright 2011 OverZealous Creations, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.overzealous.remark;

import com.overzealous.remark.convert.DocumentConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The class that manages converting HTML to Markdown.
 *
 * <p>It is recommended that you saveToFile this class if it is going to be reused for better performance.  This class
 * is thread-safe, but can only process a single document concurrently.</p>
 *
 * <p><strong>Usage:</strong></p>
 * 
 * <p>Basic usage involves instantiating this class with a specific set of options, and calling one of the
 * {@code convert*} methods on some form of input.</p>
 * 
 * <p>Examples:</p>
 * 
 * <pre>
 * // Create a generic remark that converts to pure-Markdown spec. 
 * Remark remark = new Remark();
 * String cleanedUp = remark.convertFragment(inputString);
 * 
 * // Create a remark that converts to pegdown with all extensions enabled. 
 * Remark pegdownAll = new Remark(Options.pegdownAllExtensions());
 * cleanedUp = pegdownAll.convert(new URL("http://www.example.com"), 15000);
 * 
 * // stream the conversion
 * pegdownAll.withStream(System.out).convert(new URL("http://www.overzealous.com"), 15000);
 * </pre>
 * 
 *
 * @author Phil DeJarnett
 */
public class Remark {
	private final Cleaner cleaner;
	@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
	private final Options options;
	private final DocumentConverter converter;
	private final ReentrantLock converterLock = new ReentrantLock();
	private boolean cleanedHtmlEchoed = false;

	/**
	 * Creates a default, pure Markdown-compatible Remark instance.
	 */
	public Remark() {
		this(Options.markdown());
	}

	/**
	 * Creates a Remark instance with the specified options.
	 *
	 * @param options Specified options to use on this instance.  See the docs for the Options class for common options sets.
	 */
	public Remark(Options options) {
		this.options = options.getCopy();
		Whitelist whitelist = Whitelist.basicWithImages()
									  .addTags("div",
                                              "h1", "h2", "h3", "h4", "h5", "h6",
                                              "table", "tbody", "td", "tfoot", "th", "thead", "tr",
                                              "hr",
                                              "span", "font")
									  .addAttributes("th", "colspan", "align", "style")
									  .addAttributes("td", "colspan", "align", "style")
									  .addAttributes(":all", "title", "style");
        if(options.preserveRelativeLinks) {
            whitelist.preserveRelativeLinks(true);
        }
		if(options.abbreviations) {
			whitelist.addTags("abbr", "acronym");
		}
		if(options.headerIds) {
			for(int i=1; i<=6; i++) {
				whitelist.addAttributes("h"+i, "id");
			}
		}
		for(final IgnoredHtmlElement el : options.getIgnoredHtmlElements()) {
			whitelist.addTags(el.getTagName());
            if(!el.getAttributes().isEmpty()) {
                whitelist.addAttributes(el.getTagName(), el.getAttributes().toArray(new String[el.getAttributes().size()]));
            }
		}
		cleaner = new Cleaner(whitelist);

		if(options.getTables().isLeftAsHtml()) {
			// we need to allow the table nodes to be ignored
			// since they are automatically ignored recursively, this is the only node we worry about.
			options.getIgnoredHtmlElements().add(IgnoredHtmlElement.create("table"));
		}

		converter = new DocumentConverter(options);
	}

	/**
	 * Provides access to the DocumentConverter for customization.
	 *
	 * @return the configured DocumentConverter.
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public DocumentConverter getConverter() {
		return converter;
	}

	/**
	 * Returns true if the cleaned HTML document is echoed to {@code System.out}.
	 * @return true if the cleaned HTML document is echoed 
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public boolean isCleanedHtmlEchoed() {
		return cleanedHtmlEchoed;
	}

	/**
	 * To see the cleaned and processed HTML document, set this to true.  It will
	 * be rendered to {@code System.out} for debugging purposes.
	 * @param cleanedHtmlEchoed true to echo out the cleaned HTML document
	 */
	public void setCleanedHtmlEchoed(boolean cleanedHtmlEchoed) {
		this.cleanedHtmlEchoed = cleanedHtmlEchoed;
	}

	/**
	 * This class is used to handle conversions that convert directly to streams.
	 */
	private final class StreamRemark extends Remark {
		private final Remark remark;
		private final Writer writer;
		private final OutputStream os;

		private StreamRemark(Remark remark, Writer writer) {
			this.remark = remark;
			this.writer = writer;
			this.os = null;
		}
		private StreamRemark(Remark remark, OutputStream out) {
			this.remark = remark;
			this.writer = null;
			this.os = out;
		}

		@Override
		public Remark withWriter(Writer writer) {
			return remark.withWriter(writer);
		}

		@Override
		public Remark withOutputStream(OutputStream os) {
			return remark.withOutputStream(os);
		}

		@Override
		public String convert(Document doc) {
			return remark.processConvert(doc, writer, os);
		}
	}

	/**
	 * Use this method in a chain to handle streaming the output to a Writer.
	 * The returned class can be saved for repeated writing to the same streams.
	 *
	 * <p><strong>Note: The convert methods on the returned class will always return {@code null}.</strong></p>
	 *
	 * <p><strong>Note: It is up to the calling class to handle closing the writer!</strong></p>
	 *
	 * <p>Example:</p>
	 *
	 * <blockquote>{@code new Remark(options).withWriter(myWiter).convert(htmlText);}</blockquote>
	 *
	 * @param writer Writer to receive the converted output
	 * @return A Remark that writes to streams.
	 */
	@SuppressWarnings({"WeakerAccess"})
	public synchronized Remark withWriter(Writer writer) {
		if(writer == null) {
			throw new NullPointerException("Writer cannot be null.");
		}
		return new StreamRemark(this, writer);
	}

	/**
	 * Use this method in a chain to handle streaming the output to an OutputStream.
	 * The returned class can be saved for repeated writing to the same streams.
	 *
	 * <p><strong>Note: The convert methods on the returned class will always return {@code null}.</strong></p>
	 *
	 * <p><strong>Note: It is up to the calling class to handle closing the stream!</strong></p>
	 *
	 * <p>Example:</p>
	 *
	 * <blockquote>{@code new Remark(options).withOutputStream(myOut).convert(htmlText);}</blockquote>
	 *
	 * @param os OutputStream to receive the converted output
	 * @return A Remark that writes to streams.
	 */
	@SuppressWarnings({"WeakerAccess"})
	public synchronized Remark withOutputStream(OutputStream os) {
		if(os == null) {
			throw new NullPointerException("OutputStream cannot be null.");
		}
		return new StreamRemark(this, os);
	}

	/**
	 * Converts an HTML document retrieved from a URL to Markdown.
	 * @param url URL to connect to.
	 * @param timeoutMillis Maximum time to wait before giving up on the connection.
	 * @return Markdown text.
	 * @throws IOException If an error occurs while retrieving the document.
	 * @see org.jsoup.Jsoup#parse(URL, int)
	 */
	public String convert(URL url, int timeoutMillis) throws IOException {
		Document doc = Jsoup.parse(url, timeoutMillis);
		return convert(doc);
	}


	/**
	 * Converts an HTML file to Markdown.
	 * @param file The file to load.
	 * @return Markdown text.
	 * @throws IOException If an error occurs while loading the file.
	 * @see org.jsoup.Jsoup#parse(File, String, String)
	 */
	public String convert(File file) throws IOException {
		return convert(file, null);
	}


	/**
	 * Converts an HTML file to Markdown.
	 * @param file The file to load.
	 * @param charset The charset of the file (if not specified and not UTF-8). Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or fall back to {@code UTF-8} (which is often safe to do).
	 * @return Markdown text.
	 * @throws IOException If an error occurs while loading the file.
	 * @see org.jsoup.Jsoup#parse(File, String, String)
	 */
	@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
	public String convert(File file, String charset) throws IOException {
		return convert(file, charset, "");
	}


	/**
	 * Converts an HTML file to Markdown.
	 * @param file The file to load.
	 * @param charset The charset of the file (if not specified and not UTF-8). Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or fall back to {@code UTF-8} (which is often safe to do).
	 * @param baseUri The base URI for resolving relative links.
	 * @return Markdown text.
	 * @throws IOException If an error occurs while loading the file.
	 * @see org.jsoup.Jsoup#parse(File, String, String)
	 */
	public String convert(File file, String charset, String baseUri) throws IOException {
		Document doc = Jsoup.parse(file, charset, baseUri);
		return convert(doc);
	}


	/**
	 * Converts HTML in memory to Markdown.
	 * @param html The string to processConvert from HTML
	 * @return Markdown text.
	 * @see org.jsoup.Jsoup#parse(String, String)
	 */
	public String convert(String html) {
		return convert(html, "");
	}


	/**
	 * Converts HTML in memory to Markdown.
	 * @param html The string to processConvert from HTML
	 * @param baseUri The base URI for resolving relative links.
	 * @return Markdown text.
	 * @see org.jsoup.Jsoup#parse(String, String)
	 */
	@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
	public String convert(String html, String baseUri) {
		Document doc = Jsoup.parse(html, baseUri);
		return convert(doc);
	}


	/**
	 * Converts an HTML body fragment to Markdown.
	 * @param body The fragment string to processConvert from HTML
	 * @return Markdown text.
	 * @see org.jsoup.Jsoup#parseBodyFragment(String, String)
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public String convertFragment(String body) {
		return convertFragment(body, "");
	}


	/**
	 * Converts an HTML body fragment to Markdown.
	 * @param body The fragment string to processConvert from HTML
	 * @param baseUri The base URI for resolving relative links.
	 * @return Markdown text.
	 * @see org.jsoup.Jsoup#parseBodyFragment(String, String)
	 */
	public String convertFragment(String body, String baseUri) {
		Document doc = Jsoup.parseBodyFragment(body, baseUri);
		return convert(doc);
	}

	/**
	 * Converts an already-loaded JSoup Document to Markdown.
	 *
	 * @param doc Document to be processed
	 * @return Markdown text.
	 */
	@SuppressWarnings({"WeakerAccess"})
	public String convert(Document doc) {
		// Note: all convert methods should end up going through this method!
		return processConvert(doc, null, null);
	}

	/**
	 * Handles the actual conversion
	 * @param doc document to convert
	 * @param writer Optional Writer for output
	 * @param os Optional OutputStream for output
	 * @return String result if not using an output stream, else null
	 */
	private String processConvert(Document doc, Writer writer, OutputStream os) {
		String cleanString = Jsoup.clean(doc.html(), "https://www.baidu.com", Whitelist.relaxed().preserveRelativeLinks(true));
		Document parse = Jsoup.parse(cleanString);
		doc = parse;
		if(cleanedHtmlEchoed) {
			System.out.println("Cleaned and processed HTML document:");
			System.out.println(doc.toString());
			System.out.println();
		}
		String result = null;
		converterLock.lock();
		try {
			if(writer != null) {
				converter.convert(doc, writer);
			} else if(os != null) {
				converter.convert(doc, os);
			} else {
				result = converter.convert(doc);
			}
		} finally {
			converterLock.unlock();
		}
		return result;
	}
}
