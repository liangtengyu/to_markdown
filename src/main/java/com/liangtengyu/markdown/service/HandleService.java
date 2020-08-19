package com.liangtengyu.markdown.service;


import com.liangtengyu.markdown.entity.MarkDown;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface HandleService {

    /**
     * 获取博客内容
     *
     * @param markDown
     * @return
     */
    String getBlogContent(MarkDown markDown);



}
