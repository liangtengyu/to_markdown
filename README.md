
#### ToMarkdown

>功能描述: 将HTTP页面 解析为Markdown格式 

>目前支持: 知乎,简书,知否(SegmentFault),掘金,CSDN博客,微信公众号,V2EX 一键解析
---

体验地址:   http://markdown.liangtengyu.com:9999

---
使用方法: 
1.  clone源码到本地 
2.  idea 或 eclipse 打开项目 配置jdk
3.  配置 application.yml中 保存位置和端口号
4.  运行打包项目

---
后端技术栈:
1. springboot v2.1.4.RELEASE
2. Jsoup 
3. Remark 
---
前端:
1. axios  请求组件
2. mavoneditor   markdown显示编辑组件
3. ant-design-vue  
---
 

前端项目目录:vue_project

界面截图

![pic](./readme_images/Snipaste_2020-10-19_15-16-27.png)
![pic](./readme_images/Snipaste_2020-10-19_15-16-40.png)
![pic](./readme_images/Snipaste_2020-10-19_15-16-52.png)


 
 新增特性:
 - 配置通过application.yml保存文件到目录👍 
 