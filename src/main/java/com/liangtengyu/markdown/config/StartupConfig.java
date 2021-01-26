package com.liangtengyu.markdown.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: lty
 * @Date: 2021/1/25 20:41
 */
@Slf4j
@Component
public class StartupConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("\n" +
                "  _____          __  __                    _         _                           \n" +
                " |_   _|   ___  |  \\/  |  __ _      _ _   | |__   __| |    ___   __ __ __ _ _    \n" +
                "   | |    / _ \\ | |\\/| | / _` |    | '_|  | / /  / _` |   / _ \\  \\ V  V /| ' \\   \n" +
                "  _|_|_   \\___/ |_|__|_| \\__,_|   _|_|_   |_\\_\\  \\__,_|   \\___/   \\_/\\_/ |_||_|  \n" +
                "_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"|_|\"\"\"\"\"| \n" +
                "\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-'\"`-0-0-' "
        +"\n" );
       log.info("服务启动完成! 请访问 ->>>>  http://127.0.0.1:9999");
    }
}
