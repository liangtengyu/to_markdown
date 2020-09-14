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

import com.overzealous.remark.util.BlockWriter;
import com.overzealous.remark.util.StringUtils;
import org.jsoup.nodes.Element;

/**
 * Handles header nodes (h1 through h6)
 * 
 * @author Phil DeJarnett
 */
public class Header extends AbstractNodeHandler {

	/**
	 * Renders a header node (h1..h6).  If enabled, also handles the headerID attribute.
	 *
	 * @param parent	The previous node walker, in case we just want to remove an element.
	 * @param node	  Node to handle
	 * @param converter Parent converter for this object.
	 */
	public void handleNode(NodeHandler parent, Element node, DocumentConverter converter) {
		int depth = Integer.parseInt(node.tagName().substring(1, 2));
		BlockWriter out = converter.output;
		out.startBlock();
		StringUtils.multiply(out, '#', depth);
		out.print(' ');
		out.print(converter.getInlineContent(this, node).replace("\n", " "));
		out.print(' ');
		if(converter.options.headerIds && node.hasAttr("id")) {
			out.printf("    {#%s}", node.attr("id"));
		}
		out.endBlock();
	}
}
