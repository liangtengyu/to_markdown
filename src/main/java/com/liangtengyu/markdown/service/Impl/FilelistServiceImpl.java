package com.liangtengyu.markdown.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liangtengyu.markdown.dao.MDDao;
import com.liangtengyu.markdown.dao.PICDao;
import com.liangtengyu.markdown.entity.MD;
import com.liangtengyu.markdown.entity.PIC;
import com.liangtengyu.markdown.service.FilelistService;
import com.liangtengyu.markdown.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author: lty
 * @Date: 2021/5/24 15:23
 */
@Service
@Slf4j
public class FilelistServiceImpl implements FilelistService {
    @Autowired
    PICDao picDao;
    @Autowired
    MDDao mdDao;
    @Autowired
    SettingService settingService;

    @Override
    public JSONObject getFileList(Integer id) {
        List<MD> findbyid = mdDao.findAll(new PageRequest(id, 5)).getContent();
        JSONObject re = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        for (MD md : findbyid) {
            JSONObject one_md = new JSONObject();
            one_md.put("title", md.getTITLE());
            one_md.put("pname", md.getPNAME());
            one_md.put("id", md.getID());
            List<String> piclists = picDao.findbyPname(md.getPNAME());
            one_md.put("pics", piclists);
            jsonArray.add(one_md);
        }
        re.put("data", jsonArray);
        return re;
    }

    @Override
    public void delete(Integer id) {

        MD one = mdDao.getOne(id);
        String pname = one.getPNAME();
        List<PIC> picbyPname = picDao.findPicbyPname(pname);
        for (PIC pic : picbyPname) {
            picDao.delete(pic);
            deleteLocalPic(pic.getPATH());
        }
        deleteLocalmd(one.getSavePath());
        mdDao.deleteById(id);

        log.info("删除文章 ID: " + id);

    }
    //删除本地的markdown文件.
    private void deleteLocalmd(String savePath) {
        File file = new File(savePath);
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            log.info("删除markdown" + savePath +" "+ delete) ;
        } else {
            log.info("删除markdown" + savePath + "错误!!!文件不存在");
        }
    }

    //删除本地的图片.
    private void deleteLocalPic(String path) {
        path = path.substring(41-12,41+4);
        Map<String, String> settings = settingService.getSettings();
        String image_save_path = settings.get("Image_Save_Path");
         image_save_path = image_save_path +"/"+ path;
        System.out.println(image_save_path);
        File file = new File(image_save_path);
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            log.info("删除图片" + path +" "+ delete) ;
        } else {
            log.info("删除图片" + path + "错误!!!文件不存在");
        }
    }

    @Override
    public JSONObject update(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject select(Integer id) {
        return null;
    }

    @Override
    public JSONObject count(Integer id) {
        return null;
    }

}
//MD加入表 本地存储的文件名  和 实际文章的url