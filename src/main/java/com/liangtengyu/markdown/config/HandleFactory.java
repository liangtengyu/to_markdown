package com.liangtengyu.markdown.config;

import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.HandleService;
import com.liangtengyu.markdown.service.Impl.CSDNHandleService;
import com.liangtengyu.markdown.service.Impl.WeiXinHandleService;

import java.util.HashMap;
import java.util.Map;


public class HandleFactory {

    private static class SingletonHolder{
        static HandleFactory singleton = new HandleFactory();
    }

    public static HandleFactory getInstance(){
        return SingletonHolder.singleton;
    }

    private static Map<String, HandleService> handleServiceMap = new HashMap<String, HandleService>(){
        {
            put("csdn",new CSDNHandleService());
            put("vx",new WeiXinHandleService());
        }
    };

    public String getMarkDown(MarkDown markDown){
        return handleServiceMap.get(markDown.getWebsite())
                .getBlogContent(markDown);
    }
}
