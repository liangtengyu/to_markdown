**方式1** 🦁


线上使用:

[>>>访问线上服务器<<<](http://markdown.liangtengyu.com:9999)

---

**方式2** 🐵

1.  Git clone
2.  idea 或 eclipse 打开项目
3.  访问 http://127.0.0.1:9999

---

**方式3** 🦄

docker方式
```shell
docker pull 843328437/markdown_resolve:latest
```
[运行前将 [/Users/tengyu/Desktop/docker/]  替换为你保存图片的路径]
```shell
docker run -p 9999:9999 -v /Users/tengyu/Desktop/docker/mds:/ROOT/mds -v /Users/tengyu/Desktop/docker/pics:/ROOT/pics --name markdown 843328437/markdown_resolve:latest
```

访问 http://127.0.0.1:9999

---

**方式4** 🦄(初始版本，后续未更新)

1. 下载exe文件
2. 运行(需要jre环境)
3. 访问 http://127.0.0.1:9999


[Download](../windows/tomarkdown.rar)

---