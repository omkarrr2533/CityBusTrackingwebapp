package com.citybus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CityBusTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityBusTrackerApplication.class, args);
    }
}