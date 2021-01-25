package com.liangtengyu.markdown.service;

import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.Impl.MarkDownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: lty
 * @Date: 2020/8/21 16:30
 */
@Slf4j
public class ResolveService {
    private MarkDownService markDownService;

    public ResolveService(MarkDownService markDownService) {
        this.markDownService = markDownService;
    }

    public String get (MarkDown markDown){
        return markDownService.getBlogContent(markDown);
    }
}