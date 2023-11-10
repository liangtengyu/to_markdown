package com.liangtengyu.markdown.service.Impl;

import cn.hutool.http.HttpUtil;
import com.liangtengyu.markdown.config.AppBean;
import com.liangtengyu.markdown.config.ThreadPoolConfig;
import com.liangtengyu.markdown.dao.PICDao;
import com.liangtengyu.markdown.entity.MarkDown;
import com.liangtengyu.markdown.entity.PIC;
import com.liangtengyu.markdown.service.HandleService;
import com.liangtengyu.markdown.utils.ImageUtil;
import com.liangtengyu.markdown.utils.MarkDownUtil;
import com.overzealous.remark.Remark;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public abstract class MarkDownService implements HandleService {

    @Override
    public String getBlogContent(MarkDown markDown) {

        // 1. 获取 Document
        Document document = getDocument(markDown.getBlogUrl());

        // 2.提取 Document 中的博文信息
        document = getHtmlContent(markDown,document);

        // 3.下载图片，并进行替换
        String htmlContent = convertHtml(markDown, document);

        // 转换为 markdown
        Remark remark = new Remark();

        return remark.convert(htmlContent);
    }


    /**
     * <p>1、转换为 HTML 类型</p>
     * <p>2、需要将文件中的图片下载到本地，且替换图片地址</p>
     * <p>3、处理代码块，因为 markdown 转换时没有给代码块加 ```</p>
     */
    protected String convertHtml(MarkDown markDown, Document document)  {
        if ((markDown.getImagePath() != null && !"".equals(markDown.getImagePath())) &&
                (markDown.getImageUrl() != null && !"".equals(markDown.getImageUrl()))) {

            // 处理图片
            try {
                handleImg(markDown, document);
            } catch (InterruptedException e) {
                log.info("图片处理异常.");
                e.printStackTrace();
            }
        } else {
            log.info("没有图片需要下载.图片处理结束.");
        }

        return document.html();
    }

    /**
     * 处理代码块，主要是在代码块中的前面增加 `<p>```</p>` 即可
     *
     * @param document
     */
    protected void handlePre(Document document) {

    }

    /**
     * 处理图片
     *
     * @param markDown
     * @param document
     */
    private void handleImg(MarkDown markDown, Document document) throws InterruptedException {
        // 获取所有的 img 标签
        Elements elements = document.getElementsByTag("img");

        if (elements != null && elements.size() > 0) {

            // 新建文件
            File pathFile = new File(markDown.getImagePath());

            if (!pathFile.exists() && !pathFile.mkdirs()) {
                throw new RuntimeException("新建目录失败...");
            }

            doHandleImg(elements, markDown);
        }
    }


    public void doHandleImg(Elements elements, MarkDown markDown) throws InterruptedException {
        String id = markDown.getId();




        long l = System.currentTimeMillis();
        CountDownLatch cdl = new CountDownLatch(elements.size());
        Semaphore semaphore = new Semaphore(15);
        ExecutorService threadPool = ThreadPoolConfig.getThreadPool();
        AtomicReference<String> imageUrl = new AtomicReference<>();
        String imageSrc = "";

        for (Element element : elements) {
            threadPool.execute(() -> {
                //线号量获取
                try {
                    semaphore.acquire();
                    String url = element.attr("src");
                    if (url.startsWith("data:image/svg+xml;utf8")) {
                        semaphore.release();
                        return;
                    }

                    String name = UUID.randomUUID().toString().split("-")[0];
                    // 下载图片
                    String fileName = downImage(markDown, element, name);


                    String path = markDown.getImageUrl() + "/" + fileName;
                    imageUrl.set(path);
                    // 替换地址
                    element.attr("src", imageUrl.get());
                    element.attr("alt", fileName);
                    //下载完毕 保存数据库
                    PIC pic = new PIC();
                    pic.setCreateTime(new Date());
                    pic.setPNAME(id);
                    pic.setPATH(path);
                    PICDao picdao = AppBean.getBean(PICDao.class);
                    picdao.save(pic);


                    semaphore.release();
                } catch (IOException e) {
                    System.out.println(imageSrc + "下载图片失败,cause by :" + e.getMessage());
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        log.info("下载图片总耗时: "+(System.currentTimeMillis()-l)+"ms");
    }

    /**
     * 下载图片
     *
     * @param markDown
     * @param element
     * @param name
     * @return
     * @throws IOException
     */

    public String downImage(MarkDown markDown, Element element, String name) throws IOException {
        String fileName = markDown.getImageName() + "_" + name + ".png";
        File imageFile = MarkDownUtil.getImageFile(markDown.getImagePath(), fileName);
        String imageSrc = element.attr("src");


        imageSrc = handleLinkStyle(markDown, element, imageSrc);
        if (imageSrc == null) return "";


        doDownloadImages(imageFile, imageSrc);
        return fileName;
    }


    public void doDownloadImages(File imageFile, String imageSrc) {
        //下载图片⬇️
        log.info("catch picture :{}", imageSrc);
        byte[] bytes = HttpUtil.downloadBytes(imageSrc);
        ImageUtil.byte2image(bytes, imageFile.getPath());
    }

    /**
     * 处理成统一的链接样式 如http://xxx.com/123/1.png
     *
     * @param markDown
     * @param element
     * @param imageSrc
     * @return
     */
    private String handleLinkStyle(MarkDown markDown, Element element, String imageSrc) {
        if (imageSrc.startsWith("data:image/svg+xml;utf8")) {
            log.info("ignore svg type images");
            return null;
        }


        // 如果不存在 src，则尝试获取 data-src
        if (imageSrc == null || "".equals(imageSrc.trim())) {
            imageSrc = element.attr("data-src");
        }

        // 如果不存在 data-src，则尝试获取 data-original-src
        if (imageSrc == null || "".equals(imageSrc.trim())) {
            imageSrc = element.attr("data-original-src");
            imageSrc = "https:" + imageSrc;//简书 Https
        }

        if (imageSrc.startsWith("/")) {
            imageSrc = markDown.getWebsite() + ".com" + imageSrc;
        }

        // 有些图片没有 http
        if (imageSrc.startsWith("//")) {
            imageSrc = "https:" + imageSrc;

        }

        return imageSrc;
    }

//    /**
//     * 获取 Document 对象
//     *
//     * @param blogUrl
//     * @return
//     */
//    private Document getDocument(String blogUrl) {
//        try {
//            return Jsoup.connect(blogUrl).get();
//        } catch (IOException e) {
//            throw new RuntimeException("解析地址，获取 Document 对象失败..", e);
//        }
//    }

    /**
     * 获取 Document 对象
     *
     * @param blogUrl
     * @return
     */
    private Document getDocument(String blogUrl) {
        return Jsoup.parse(HttpUtil.downloadString(blogUrl, "utf-8"));
    }


    /**
     * 每个网站的结构不同，需要各个子类完成解析
     *
     * @param markDown
     * @param document
     * @return
     */
    protected abstract Document getHtmlContent(MarkDown markDown, Document document);
}
