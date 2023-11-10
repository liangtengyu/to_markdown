package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public  class ZhihuHandleService extends MarkDownService {

    @Override
    protected synchronized Document getHtmlContent(MarkDown markDown, Document document) {
        //默认为专栏
        Element root = document.getElementById("root");
        Elements elementsByClass = root.getElementsByClass("Post-RichText");
        if (elementsByClass.size() == 0) {
            //是question
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
