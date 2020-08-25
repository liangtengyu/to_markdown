package com.liangtengyu.markdown.service.Impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public  class JuejinHandleService extends MarkDownService {

    @Override
    protected synchronized Document getHtmlContent(Document document) {
        Elements article = document.getElementsByClass("article-content");
        return Jsoup.parse(article.html().replaceAll("复制代码",""));
    }
}
