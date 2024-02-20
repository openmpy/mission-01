package com.example.mission01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Mission01Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission01Application.class, args);
    }

}
