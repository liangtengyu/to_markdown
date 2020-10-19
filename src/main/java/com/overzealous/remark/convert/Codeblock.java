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
import com.overzealous.remark.util.BlockWriter;
import com.overzealous.remark.util.StringUtils;
import org.jsoup.nodes.Element;

/**
 * Handles preformatted sections (pre), renders them as code blocks.
 *
 * @author Phil DeJarnett
 */
public class Codeblock extends AbstractNodeHandler {

	/**
	 * Converts a pre-formatted block of code.
	 * Depending on the options, this may render as a block with four spaces added to the beginning,
	 * or as a fenced code block.
	 *
	 * @param parent The previous node walker, in case we just want to remove an element.
	 * @param node	  Node to handle
	 * @param converter Parent converter for this object.
	 */
	@Override
	public void handleNode(NodeHandler parent, Element node, DocumentConverter converter) {
		BlockWriter out;
		Options.FencedCodeBlocks fenced = converter.options.getFencedCodeBlocks();
		if(fenced.isEnabled()) {
			String fence = StringUtils.multiply(fenced.getSeparatorCharacter(),
													   converter.options.fencedCodeBlocksWidth);
			out = converter.output;
			converter.output.startBlock();
			out.println(fence);
			out.write(converter.cleaner.cleanCode(node));
			out.println();
			out.print(fence);
			converter.output.endBlock();
		} else {
			converter.output.startBlock();
			out = new BlockWriter(converter.output);
			out.print("```java");
			out.print("\r\n");
			out.write(converter.cleaner.cleanCode(node));
			out.print("\r\n");
			out.print("```");
			converter.output.endBlock();
		}
	}
}
