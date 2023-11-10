package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WeiXinHandleService extends MarkDownService {

    @Override
    protected Document getHtmlContent(MarkDown markDown, Document document) {
        Element element = document.getElementById("js_content");
        return Jsoup.parse(element.html());
    }
}
