package com.liangtengyu.markdown.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lty
 * @Date: 2021/4/7 13:53
 */
@Component
public class ThreadPoolConfig {
   public static ExecutorService getThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("下载线程-").build();
       return new ThreadPoolExecutor(32, 64,
               0L, TimeUnit.MILLISECONDS,
               new LinkedBlockingQueue<Runnable>(255), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
