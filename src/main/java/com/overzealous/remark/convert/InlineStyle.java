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

package com.overzealous.remark.convert;

import com.overzealous.remark.Options;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles various inline styling (italics and bold), such as em, i, strong, b, span, and font tags.
 * @author Phil DeJarnett
 */
public class InlineStyle extends AbstractNodeHandler {

	private static final char ITALICS_WRAPPER = '*';
	private static final String BOLD_WRAPPER = "**";

	private static final Pattern ITALICS_PATTERN = Pattern.compile("font-style:\\s*italic", Pattern.CASE_INSENSITIVE);
	private static final Pattern BOLD_PATTERN = Pattern.compile("font-weight:\\s*bold", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern INWORD_CHARACTER = Pattern.compile("\\w");
	
	private static final Pattern SPACE_CONTENT_SPACE = Pattern.compile("^(\\s*+)(.*?)(\\s*)$", Pattern.DOTALL);

	private int italicDepth = 0;
	private int boldDepth = 0;

	/**
	 * Renders inline styling (bold, italics) for the given tag.  It handles implicit styling ({@code em}, {@code strong}) as
	 * well as explicit styling via the {@code style} attribute.
	 * <p>This object keeps track of the depth of the styling, to prevent recursive situations like this:</p>
	 *
	 * <blockquote>{@code <em>hello <em>world</em></em>}</blockquote>
	 *
	 * <p>A naive method would be render the example incorrectly (the output would be {@code *hello **world*})</p>
	 *
	 * @param parent The previous node walker, in case we just want to remove an element.
	 * @param node	  Node to handle
	 * @param converter Parent converter for this object.
	 */
	public void handleNode(NodeHandler parent, Element node, DocumentConverter converter) {
		if(checkInnerBlock(node)) {
			// not valid to have an inline node around block nodes, so we have to
			// simply ignore them.
			// just recurse like it's not here.
			converter.walkNodes(parent, node);
		} else {
			Rules rules = checkInword(node, converter);
			if(rules.emphasisPreserved) {
				checkTag(node, rules);
	
				if(rules.bold || rules.italics) {
					handleStyled(parent, node, converter, rules);
				} else {
					converter.walkNodes(this, node, converter.inlineNodes);
				}
			} else { // emphasis has been disabled for this section
				// mark as if emphasis was already processed
				italicDepth++;
				boldDepth++;
				converter.walkNodes(this, node, converter.inlineNodes);
				italicDepth--;
				boldDepth--;
			}
		}
	}

	@Override
	public void handleTextNode(TextNode node, DocumentConverter converter) {
		// Override to provide special handling for ignoring
		// leading or trailing all-space nodes.
		if((node.previousSibling() != null && node.nextSibling() != null) ||
				   node.text().trim().length() != 0) {
			super.handleTextNode(node, converter);
		}
	}

	/**
	 * Minor class to hold onto the styling rules for this class.
	 */
	private class Rules {
		boolean emphasisPreserved = true;
		boolean addSpacing = false;
		boolean italics = false;
		boolean bold = false;
	}

	/**
	 * Handles dealing with a styled node (one that has markers on either side).
	 * 
	 * <p>It's unique because we have to deal with leading and trailing spaces, among other issues.</p>
	 * 
	 * @param parent The previous node walker, in case we just want to remove an element.
	 * @param node	  Node to handle
	 * @param converter Parent converter for this object.
	 * @param rules The styling rules that are active
	 */
	private void handleStyled(NodeHandler parent, Element node, DocumentConverter converter, Rules rules) {
		// prevent double styling
		if(rules.bold) { boldDepth++; }
		if(rules.italics) { italicDepth++; }
		String content = converter.getInlineContent(this, node, true);
		if(rules.bold) { boldDepth--; }
		if(rules.italics) { italicDepth--; }
		
		// only proceed if we have content
		if(content.length() > 0) {
		
			
			Matcher parts = SPACE_CONTENT_SPACE.matcher(content);
			if(parts.find()) {
				// write any leading space
				converter.output.write(parts.group(1));
				
				// don't write the markers if the content ends up empty
				if(parts.group(2).length() > 0) {

					// write content
					converter.output.write(parts.group(2));
					

				}
				
				// write any trailing space
				converter.output.write(parts.group(3));
					
			} // else, something weird happened, like (1 == 0)
		}
	}

	/**
	 * Check to see if there is a block-level node somewhere inside this node.
	 * 
	 * @param node Current node
	 * @return True is there is a block inside this node (which would be invalid HTML)
	 */
	private boolean checkInnerBlock(Element node) {
		boolean blockExists = false;
		for(final Element child : node.children()) {
			blockExists = child.isBlock() || checkInnerBlock(child);
			if(blockExists) {
				break;
			}
		}
		return blockExists;
	}

	/**
	 * Handles the situation where InWordEmphasis needs to be manipulated.
	 *
	 * <p>This isn't a terribly intelligent check - it merely looks for the
	 * situation where a styled node is immediately <em>followed</em> by a
	 * text node, and that text node starts with a word character.</p>
	 *
	 * @param node The current node (should be an inline-styled node)
	 * @param converter The current converter
	 * @return flags for checking.
	 */
	private Rules checkInword(Element node, DocumentConverter converter) {
		Rules result = new Rules();
		Options.InWordEmphasis iwe = converter.options.getInWordEmphasis();
		if(!iwe.isEmphasisPreserved() || iwe.isAdditionalSpacingNeeded()) {
			// peek behind for inline styling
			Node n = node.previousSibling();
			if(n != null && n instanceof TextNode) {
				TextNode tn = (TextNode)n;
				String text = tn.text();
				if(INWORD_CHARACTER.matcher(text.substring(text.length()-1)).matches()) {
					result.emphasisPreserved = iwe.isEmphasisPreserved();
					result.addSpacing = iwe.isAdditionalSpacingNeeded();
				}
			}
			// peek ahead for inline styling
			n = node.nextSibling();
			if(n != null && n instanceof TextNode) {
				TextNode tn = (TextNode)n;
				if(INWORD_CHARACTER.matcher(tn.text().substring(0,1)).matches()) {
					result.emphasisPreserved = iwe.isEmphasisPreserved();
					result.addSpacing = iwe.isAdditionalSpacingNeeded();
				}
			}
		}
		return result;
	}

	/**
	 * Check the styling rules that may or may not apply to this tag.
	 * @param node The node to look at
	 * @param rules The rules object to hold the result
	 */
	private void checkTag(Element node, Rules rules) {
		String tn = node.tagName();
		if(tn.equals("i") || tn.equals("em")) {
			rules.italics = (italicDepth == 0);
		} else if(tn.equals("b") || tn.equals("strong")) {
			rules.bold = (boldDepth == 0);
		} else {
			// check inline-style
			if(node.hasAttr("style")) {
				String style = node.attr("style");
				if(ITALICS_PATTERN.matcher(style).find()) {
					rules.italics = (italicDepth == 0);
				}
				if(BOLD_PATTERN.matcher(style).find()) {
					rules.bold = (boldDepth == 0);
				}
			}
		}
	}

	/**
	 * Render the starting styling tag as necessary.
	 * 
	 * @param style Rules to render
	 * @param leadingSpaces Leading spaces string (if any)
	 * @param converter parent converter
	 */
	private void start(Rules style, String leadingSpaces, DocumentConverter converter) {
		if(style.addSpacing &&
				   (italicDepth == 0 || boldDepth == 0) &&
				   (leadingSpaces == null || leadingSpaces.length() == 0)) {
			converter.output.write(' ');
		}
		if(style.italics) {
			if(italicDepth == 0) {
				converter.output.write(ITALICS_WRAPPER);
			}
		}
		if(style.bold) {
			if(boldDepth == 0) {
				converter.output.write(BOLD_WRAPPER);
			}
		}
	}

	/**
	 * Render the ending tag as necessary.
	 * 
	 * @param style Rules to render
	 * @param trailingSpaces Trailing spaces (if any)
	 * @param converter parent converter
	 */
	private void end(Rules style, String trailingSpaces, DocumentConverter converter) {
		if(style.bold) {
			if(boldDepth == 0) {
				converter.output.write(BOLD_WRAPPER);
			}
		}
		if(style.italics) {
			if(italicDepth == 0) {
				converter.output.write(ITALICS_WRAPPER);
			}
		}
		if(style.addSpacing &&
					(italicDepth == 0 || boldDepth == 0) &&
					(trailingSpaces == null || trailingSpaces.length() == 0)) {
			converter.output.write(' ');
		}
	}
}
