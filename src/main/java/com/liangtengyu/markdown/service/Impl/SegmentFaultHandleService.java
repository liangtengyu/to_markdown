package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public  class SegmentFaultHandleService extends MarkDownService {

    @Override
    protected synchronized Document getHtmlContent(MarkDown markDown, Document document) {
        Elements article = document.getElementsByTag("article");
        String html = article.html();
        String data = html.replaceAll("src", "");
        String s = data.replaceAll("/img/remote/", "https://segmentfault.com/img/remote/");
        String src = s.replaceAll("data-", "src");
        return Jsoup.parse(src);
    }
}
