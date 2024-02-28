package com.prueba.homeworkapp;

import com.prueba.homeworkapp.common.config.TimeZoneConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication
public class HomeworkappApplication {
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String NON_TEST_PROFILE = "!test";
    public static final String INT_TEST_PROFILE = "int-test";

    public static void main(String[] args) {
        TimeZone.setDefault(
                TimeZone.getTimeZone(TimeZoneConfig.DEFAULT_TIME_ZONE)
        );
        SpringApplication.run(HomeworkappApplication.class, args);
    }
}
