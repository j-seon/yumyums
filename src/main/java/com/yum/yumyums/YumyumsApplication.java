package com.yum.yumyums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YumyumsApplication {

	public static void main(String[] args) {
		SpringApplication.run(YumyumsApplication.class, args);
	}
}
