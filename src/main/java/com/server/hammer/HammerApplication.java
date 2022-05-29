package com.server.hammer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class HammerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HammerApplication.class, args);
    }

}
