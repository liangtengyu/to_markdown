package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CsdnBlogHandleService extends MarkDownService {


    @Override
    protected Document getHtmlContent(MarkDown markDown, Document document) {
        Element mainElement = document.getElementById("cnblogs_post_body");


        String htmlContent = mainElement.html();

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
