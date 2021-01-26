package com.liangtengyu.markdown.service.Impl;

import com.liangtengyu.markdown.service.SaveFileService;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: lty
 * @Date: 2020/12/28 14:40
 */
@Service
public class SaveFileServiceImpl implements SaveFileService {
    //markdown 文件的存储路径
    @Value("${markdown.filePath}")
    String filePath;

    @Override
    public String save(String result) throws IOException {
        //通过此接口,将markdown保存为文本
        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        String markdown = MarkDownUtil.generatorFileName();
        File mdFile = new File(f, markdown);
        if (!mdFile.exists()) {
            mdFile.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(mdFile);
        outputStream.write(result.getBytes());
        outputStream.close();
        return "markdown file save success ";
    }
}
