package com.liangtengyu.markdown.controller;


import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.ResolveService;
import com.liangtengyu.markdown.service.SaveFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/resolve")
public class RequestController {




    @Autowired
    SaveFileService saveFileService;


    /**
     * 获取文章
     * @param markDown
     * @return
     */
    @PostMapping("/mark")
    @ResponseBody
    @CrossOrigin
    public Map<String, String> mark(@RequestBody MarkDown markDown, HttpServletRequest request){
        Map<String,String> resultMap = new HashMap<>();
        String result = null;
        try {
            log.info("开始解析 请求地址为: "+markDown.getBlogUrl()+" 请求ID: "+request.getSession().getId());
            result = ResolveService.get(markDown);
            resultMap.put("code","0");
            resultMap.put("markdown",result);
            log.info(saveFileService.save(result));
            log.info("解析完成 返回markdown结果 "+request.getSession().getId());
            log.info("-------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code","-1");
            resultMap.put("markdown","");
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }




}
