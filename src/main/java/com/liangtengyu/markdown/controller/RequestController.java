package com.liangtengyu.markdown.controller;


import com.liangtengyu.markdown.config.HandleFactory;
import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.HandleService;
import com.liangtengyu.markdown.service.Impl.MarkDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/resolve")
public class RequestController {


    /**
     * 获取文章
     * @param markDown
     * @return
     */
    @PostMapping("/mark")
    @ResponseBody
    @CrossOrigin
    public Map<String, String> mark(@RequestBody MarkDown markDown){
        Map<String,String> resultMap = new HashMap<>();
        String result = null;
        try {
            result = HandleFactory.getInstance().getMarkDown(markDown);
            resultMap.put("code","0");
            resultMap.put("markdown",result);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code","-1");
            resultMap.put("markdown","");
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }
}
