package com.liangtengyu.markdown.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.config.ApplicationConfig;
import com.liangtengyu.markdown.dao.SETTINGDao;
import com.liangtengyu.markdown.dao.UserTemplateDao;
import com.liangtengyu.markdown.entity.SETTING;
import com.liangtengyu.markdown.entity.UserTemplate;
import com.liangtengyu.markdown.service.SettingService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: lty
 * @Date: 2021/3/3 14:24
 */
@Service
@Slf4j
public class SettingServiceImpl implements SettingService {

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    SETTINGDao settingDao;

    @Autowired
    UserTemplateDao userTemplateDao;



    @Override
    public Map<String, String> getSettings() {
        List<SETTING> list = settingDao.findAll();
        HashMap<String, String> kvMap = new HashMap<>();
        for (SETTING setting : list) {
            String configName = setting.getConfigName();
            String configValue = setting.getConfigValue();
            kvMap.put(configName, configValue);
            //判断如果 是Image_Save_Path 直接赋值给图片代理
            if ("Image_Save_Path".equals(configName)) {
                updateProxyPath(configValue);
            }
        }

        log.info("配置信息加载完成..");
        return kvMap;
    }


    /**
     * 更新代理方法.
     * @param configValue
     */
    private void updateProxyPath(String configValue) {
//        if (!configValue.endsWith("/")) {//少/的加/
//            configValue += "/";
//        }
//
//        log.info("将图片映射地址更新为:"+configValue);
    }

    @Override
    public int setSettings(JSONObject j) {

//    {"User_template_id":"1","MD_Save_Path":"./mds","Image_Save_Path":"./pics"
//            ,"Image_Proxy_Path":"http://localhost:9999/images","Image_DEFAULT_NAME":"pic"}
        updateProxyPath(j.getString("Image_Save_Path"));
        Set<Map.Entry<String, Object>> entries = j.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            settingDao.updateByName(entry.getKey(),entry.getValue().toString());
        }
        return 1;
    }

    @Override
    public UserTemplate getOneUserTemplate(Integer id) {
        return userTemplateDao.getOne(id);
    }
}
