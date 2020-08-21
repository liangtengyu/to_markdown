package com.liangtengyu.markdown.controller;


import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.Impl.CSDNHandleService;
import com.liangtengyu.markdown.service.Impl.WeiXinHandleService;
import com.liangtengyu.markdown.service.ResolveService;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
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
            ResolveService resolveService = null;
            String website = MarkDownUtil.getUrlOrigin(markDown);
            if ("wx".equals(website)) {
                  resolveService = new ResolveService(new WeiXinHandleService());
            } else if ("csdn".equals(website)) {
                resolveService = new ResolveService(new CSDNHandleService());

            } else {
                log.info("暂时还没有解决方案.");
                throw new RuntimeException("暂时还没有解决方案");
            }

            result = resolveService.get(markDown);
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
