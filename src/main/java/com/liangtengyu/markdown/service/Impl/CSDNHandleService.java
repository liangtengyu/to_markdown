package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CSDNHandleService extends MarkDownService {


    @Override
    protected Document getHtmlContent(MarkDown markDown, Document document) {
        Element mainElement = document.getElementById("mainBox");

        // 不是 Markdown，则获取 HTML
        if(mainElement == null){
            mainElement = document.getElementById("htmledit_views");
        }

        String htmlContent = mainElement.getElementById("content_views").html();

        document = Jsoup.parse(htmlContent);

        // 去掉代码块中的行号
        Elements elements = document.getElementsByTag("pre");
        if(MarkDownUtil.elementsNotEmpty(elements)){

            Elements preNumbers = null;
            for(Element element : elements){
                preNumbers = element.getElementsByClass("pre-numbering");
                if(MarkDownUtil.elementsNotEmpty(preNumbers)){
                    for(Element preNumber : preNumbers){
                        // 删掉换行号
                        preNumber.remove();
                    }
                }
            }
        }

        return document;
    }
}
