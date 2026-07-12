package com.authservice.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
//import org.springframework.retry.annotation.EnableRetry;



@SpringBootApplication
//@EnableRetry
@ConfigurationPropertiesScan   // 👈 this enables binding of JwtProperties (auth.jwt.secret)

public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
