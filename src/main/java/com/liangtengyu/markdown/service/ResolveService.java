package com.liangtengyu.markdown.service;

import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.Impl.*;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: lty
 * @Date: 2020/8/21 16:30
 */
@Slf4j
public  class ResolveService {

   static   HashMap<String, MarkDownService> serviceMap = new HashMap<>();

    private static final String WEIXIN = "weixin";
    private static final String CSDN = "csdn";
    private static final String CSDN_BLOG = "cnblogs";
    private static final String ZHIHU = "zhihu";
    private static final String JUEJIN = "juejin";
    private static final String SEGMENTFAULT = "segmentfault";
    private static final String JIANSHU = "jianshu";
    private static final String V_2_EX = "v2ex";
    private static final String YU_QUE = "yuque";



    public static String get (MarkDown markDown){
        String website = MarkDownUtil.getUrlOrigin(markDown);
        initMap(website);
        return serviceMap.get(website)
                .getBlogContent(markDown);

    }

    private static void initMap(String website) {
        if (!serviceMap.containsKey(website)) {
            log.info("Init MarkdownService for {}" ,website);
            if (WEIXIN.equals(website)) {
                serviceMap.put(website, new WeiXinHandleService());

            } else if (CSDN.equals(website)) {
                serviceMap.put(website, new CSDNHandleService());

            } else if (CSDN_BLOG.equals(website)) {
                serviceMap.put(website, new CsdnBlogHandleService());

            } else if (ZHIHU.equals(website)) {
                serviceMap.put(website,new ZhihuHandleService());

            } else if (JUEJIN.equals(website)) {
                serviceMap.put(website,new JuejinHandleService());

            }  else if (SEGMENTFAULT.equals(website)) {
                serviceMap.put(website,new SegmentFaultHandleService());

            }  else if (JIANSHU.equals(website)) {
                serviceMap.put(website,new JianshuHandleService());

            }else if (V_2_EX.equals(website)) {
                serviceMap.put(website,new V2exHandleService());

            }else if (YU_QUE.equals(website)) {
                serviceMap.put(website,new YuqueHandleService());

            } else {
                log.info("暂时还没有解决方案.");
                throw new RuntimeException("暂时还没有解决方案");
            }
        }
    }
}