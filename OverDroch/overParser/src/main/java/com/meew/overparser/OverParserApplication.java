package com.meew.overparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
@SpringBootApplication
@AutoConfiguration
@Configuration
@ImportResource({"classpath:applicationContext.xml"})
public class OverParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(OverParserApplication.class, args);
    }

}
