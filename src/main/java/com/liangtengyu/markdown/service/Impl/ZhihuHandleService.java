package com.liangtengyu.markdown.service.Impl;

import lombok.Synchronized;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public  class ZhihuHandleService extends MarkDownService {

    @Override
    protected synchronized Document getHtmlContent(Document document) {
        Element root = document.getElementById("root");
        Elements elementsByClass = root.getElementsByClass("RichText ztext Post-RichText");
        if (elementsByClass.size() == 0) {
            //说明是question
            Elements content = document.getElementsByClass("QuestionAnswer-content");
            Document parse = Jsoup.parse(content.html());
            Elements content_inner = parse.getElementsByClass("RichContent-inner");
            if (content_inner.size() > 0) {
                return Jsoup.parse(content_inner.html());
            } else {
                return Jsoup.parse(content.attr("error","解析知乎页面异常").html());
            }
        }
        return Jsoup.parse(elementsByClass.html());
    }
}
