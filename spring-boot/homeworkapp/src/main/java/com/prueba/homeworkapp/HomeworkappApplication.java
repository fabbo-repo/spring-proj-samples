package com.prueba.homeworkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HomeworkappApplication {
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String NON_TEST_PROFILE = "!test";

    public static void main(String[] args) {
        SpringApplication.run(HomeworkappApplication.class, args);
    }
}
