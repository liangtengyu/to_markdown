package com.liangtengyu.markdown.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.dao.MDDao;
import com.liangtengyu.markdown.dao.PICDao;
import com.liangtengyu.markdown.entity.MD;
import com.liangtengyu.markdown.service.FilelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lty
 * @Date: 2021/5/24 15:23
 */
@Service
public class FilelistServiceImpl implements FilelistService {
    @Autowired
    PICDao picDao;
    @Autowired
    MDDao mdDao;


    @Override
    public JSONObject getFileList(Integer id) {
        List<MD> findbyid = mdDao.findAll(new PageRequest(id,5)).getContent();
        JSONObject re = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        for (MD md : findbyid) {
            JSONObject one_md = new JSONObject();
            one_md.put("title", md.getTITLE());
            one_md.put("pname", md.getPNAME());
            List<String> piclists= picDao.findbyPname(md.getPNAME());
            one_md.put("pics", piclists);
            jsonArray.add(one_md);
        }
        re.put("data", jsonArray);
        return re;
    }
}
