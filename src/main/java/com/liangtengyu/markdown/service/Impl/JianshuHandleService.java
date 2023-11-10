package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public  class JianshuHandleService extends MarkDownService {

    @Override
    protected synchronized Document getHtmlContent(MarkDown markDown, Document document) {
        //默认为专栏
        Elements root = document.getElementsByTag("article");
        return Jsoup.parse(root.html());
    }
}
