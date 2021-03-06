package com.cchao.pinbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class PostboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostboxApplication.class, args);
    }
}
