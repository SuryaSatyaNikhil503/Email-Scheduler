package com.project.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailShedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailShedulerApplication.class, args);
	}

}
