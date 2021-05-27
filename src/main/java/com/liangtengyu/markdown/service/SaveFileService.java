package com.liangtengyu.markdown.service;

import com.liangtengyu.markdown.entity.MarkDown;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: lty
 * @Date: 2020/12/28 14:39
 */
public interface SaveFileService {
    String saveToFile(String result, String id, MarkDown markDown) throws IOException;
    void saveToDatabase(String result,String id,String savePath,MarkDown markDown) throws IOException;
    void saveImagePath(String path);
}
