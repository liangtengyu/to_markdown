package com.liangtengyu.markdown.service;

import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.entity.MD;
import org.springframework.stereotype.Service;

/**
 * @Author: lty
 * @Date: 2021/5/24 15:20
 */
@Service
public interface FilelistService {
    JSONObject getFileList(Integer id);

    void delete(Integer id);

    JSONObject update(JSONObject data);

    MD select(Integer id);

    long count();
}
