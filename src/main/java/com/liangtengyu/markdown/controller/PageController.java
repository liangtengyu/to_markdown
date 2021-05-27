package com.liangtengyu.markdown.controller;

import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.entity.MD;
import com.liangtengyu.markdown.service.FilelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
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


    //delete
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Integer delete(@PathVariable("id")Integer id) {
         filelistService.delete(id);
         return 1;
    }


    //update
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject update(@RequestBody JSONObject data) {
        return filelistService.update(data);
    }

    //select
    @RequestMapping("/select/{id}")
    @ResponseBody
    public MD select(@PathVariable("id")Integer id) {
        return filelistService.select(id);
    }




    //count
    @RequestMapping("/count")
    @ResponseBody
    public long count() {
        return filelistService.count();
    }
}