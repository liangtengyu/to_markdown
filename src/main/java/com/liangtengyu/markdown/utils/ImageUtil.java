package com.liangtengyu.markdown.utils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

/**
 * @Author: lty
 * @Date: 2021/4/7 09:50
 */
public class ImageUtil {


    public static void byte2image(byte[] data, String path){
        if(data.length<3||path.equals("")) {
            return;
        }
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * byte数组到16进制字符串
     * @param data
     * @return
     */
    public String byte2string(byte[] data){
        if(data==null||data.length<=1) {
            return "0x";
        }
        if(data.length>200000) {
            return "0x";
        }
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        //byte数组转化成十进制
        for(int k=0;k<data.length;k++){
            buf[k] = data[k]<0?(data[k]+256):(data[k]);
        }
        //十进制转化成十六进制
        for(int k=0;k<buf.length;k++){
            if(buf[k]<16) {
                sb.append("0"+Integer.toHexString(buf[k]));
            } else {
                sb.append(Integer.toHexString(buf[k]));
            }
        }
        return "0x"+sb.toString().toUpperCase();
    }
}
