package com.cchao.pinbox;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class PostboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostboxApplication.class, args);
        new File("/www/file/upload/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd")).mkdirs();
    }
}
