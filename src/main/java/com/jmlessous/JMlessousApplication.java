package com.jmlessous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class JMlessousApplication {

    public static void main(String[] args) {
        SpringApplication.run(JMlessousApplication.class, args);
    }
}
