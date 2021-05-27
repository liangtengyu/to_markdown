package com.liangtengyu.markdown.controller;


import cn.hutool.core.util.IdUtil;
import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.ResolveService;
import com.liangtengyu.markdown.service.SaveFileService;
import com.liangtengyu.markdown.service.SettingService;
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
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/resolve")
public class RequestController {




    @Autowired
    SaveFileService saveFileService;

    @Autowired
    SettingService settingService;

    /**
     * 获取文章
     * @param markDown
     * @return
     */
    @PostMapping("/mark")
    @ResponseBody
    @CrossOrigin
    public Map<String, String> mark(@RequestBody MarkDown markDown, HttpServletRequest request){
        String id = IdUtil.simpleUUID();
        fillUp(markDown,id);
        Map<String,String> resultMap = new HashMap<>();
        String result = null;
        try {
            log.info("开始解析 请求地址为: "+markDown.getBlogUrl()+" 请求ID: "+ id);
            result = ResolveService.get(markDown);
            resultMap.put("code","0");
            resultMap.put("markdown",result);
            log.info(saveFileService.saveToFile(result,id,markDown));
            log.info("解析完成 返回markdown结果 "+ id);
            log.info("-------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code","-1");
            resultMap.put("markdown","");
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }

    private void fillUp(MarkDown markDown, String id) {
        Map<String, String> settings = settingService.getSettings();
        markDown.setImagePath(settings.get("Image_Save_Path"));
        markDown.setImageName(settings.get("Image_DEFAULT_NAME"));
        markDown.setImageUrl(settings.get("Image_Proxy_Path"));
        markDown.setId(id);
    }


}
