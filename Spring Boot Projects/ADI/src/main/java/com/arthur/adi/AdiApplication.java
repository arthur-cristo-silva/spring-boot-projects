package com.arthur.adi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdiApplication.class, args);
    }

}
