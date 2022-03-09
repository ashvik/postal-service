package com.postal.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Postal Detail APIs", version = "1.0", description = "APIs to pull postal " +
        "details for given city or area."))
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
