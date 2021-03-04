package com.liangtengyu.markdown.controller;


import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.entity.SETTING;
import com.liangtengyu.markdown.entity.UserTemplate;
import com.liangtengyu.markdown.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Slf4j
@CrossOrigin
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    SettingService settingService;


    @PostMapping("/get")
    @ResponseBody
    public Map<String, String> getConfig(){
        return settingService.getSettings();
    }



    @RequestMapping("/set")
    @ResponseBody
    public String set(@RequestBody JSONObject data) {
        settingService.setSettings(data);
        return "ok";
    }




}
