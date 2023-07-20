package com.prueba.homeworkapp;

import com.prueba.homeworkapp.core.config.EntityAuditor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = EntityAuditor.AUDITOR_BEAN_NAME)
public class HomeworkappApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkappApplication.class, args);
    }
}
