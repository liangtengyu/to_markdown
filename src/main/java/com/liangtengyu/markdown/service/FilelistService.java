package com.liangtengyu.markdown.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Author: lty
 * @Date: 2021/5/24 15:20
 */
@Service
public interface FilelistService {
    JSONObject getFileList(Integer id);
}
