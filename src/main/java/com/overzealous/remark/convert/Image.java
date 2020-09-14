package com.overzealous.remark.convert;


import com.overzealous.remark.util.BlockWriter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

/**
 * Handles img tags.
 * @author Phil DeJarnett
 */
public class Image extends AbstractNodeHandler {

    @Override
    public void handleNode(NodeHandler parent, Element node, DocumentConverter converter) {
            String url = converter.cleaner.cleanUrl(node.attr("src"));
            if (StringUtils.isNotBlank(url)) {
                String alt = node.attr("alt");
                converter.output.printf("![%s](%s)", alt, url);
            }

        }
}
