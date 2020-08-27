package com.liangtengyu.markdown.service.Impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class V2exHandleService extends MarkDownService {

    @Override
    protected Document getHtmlContent(Document document) {
        Elements topic_content = document.getElementsByClass("topic_content");
        return Jsoup.parse(topic_content.html());
    }
}
