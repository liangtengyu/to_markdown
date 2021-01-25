package com.liangtengyu.markdown.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController  {
    @RequestMapping("/")
    public String toFirstHtml(){
        return "index";
    }
}