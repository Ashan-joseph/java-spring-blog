package org.ashan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogApplication.class, args);
    }

}
