package com.liangtengyu.markdown.controller;

import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.service.FilelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController  {
    @Autowired
    FilelistService filelistService;

    @RequestMapping("/")
    public String toFirstHtml(){
        return "index";
    }

    @RequestMapping("/filelist")
    @ResponseBody
    public JSONObject filelist(@RequestBody JSONObject data) {
        return filelistService.getFileList(data.getInteger("id"));
    }

}