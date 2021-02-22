package com.liangtengyu.markdown.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: lty
 * @Date: 2020/12/28 14:39
 */
public interface SaveFileService {
    String saveToFile(String result) throws IOException;
    void saveToDatabase(String result) throws IOException;
    void saveImagePath(String path);
}
