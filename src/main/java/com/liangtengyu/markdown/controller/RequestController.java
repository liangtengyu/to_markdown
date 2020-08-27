package com.liangtengyu.markdown.controller;


import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.service.Impl.CSDNHandleService;
import com.liangtengyu.markdown.service.Impl.JianshuHandleService;
import com.liangtengyu.markdown.service.Impl.JuejinHandleService;
import com.liangtengyu.markdown.service.Impl.SegmentFaultHandleService;
import com.liangtengyu.markdown.service.Impl.V2exHandleService;
import com.liangtengyu.markdown.service.Impl.WeiXinHandleService;
import com.liangtengyu.markdown.service.Impl.ZhihuHandleService;
import com.liangtengyu.markdown.service.ResolveService;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import lombok.extern.slf4j.Slf4j;
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


    private static final String WEIXIN = "weixin";
    private static final String CSDN = "csdn";
    private static final String ZHIHU = "zhihu";
    private static final String JUEJIN = "juejin";
    private static final String SEGMENTFAULT = "segmentfault";
    private static final String JIANSHU = "jianshu";
    private static final String V_2_EX = "v2ex";

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
            ResolveService resolveService = getResolveService(markDown);
            result = resolveService.get(markDown);
            resultMap.put("code","0");
            resultMap.put("markdown",result);
            log.info("解析完成 返回markdown结果 "+request.getSession().getId());
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code","-1");
            resultMap.put("markdown","");
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }

    private ResolveService getResolveService(MarkDown markDown) {
        ResolveService resolveService;
        String website = MarkDownUtil.getUrlOrigin(markDown);
        if (WEIXIN.equals(website)) {
              resolveService = new ResolveService(new WeiXinHandleService());
        } else if (CSDN.equals(website)) {
            resolveService = new ResolveService(new CSDNHandleService());

        } else if (ZHIHU.equals(website)) {
            resolveService = new ResolveService(new ZhihuHandleService());

        } else if (JUEJIN.equals(website)) {
            resolveService = new ResolveService(new JuejinHandleService());

        }  else if (SEGMENTFAULT.equals(website)) {
            resolveService = new ResolveService(new SegmentFaultHandleService());


        }  else if (JIANSHU.equals(website)) {
            resolveService = new ResolveService(new JianshuHandleService());

        }else if (V_2_EX.equals(website)) {
            resolveService = new ResolveService(new V2exHandleService());

        } else {
            log.info("暂时还没有解决方案.");
            throw new RuntimeException("暂时还没有解决方案");
        }
        return resolveService;
    }

}
