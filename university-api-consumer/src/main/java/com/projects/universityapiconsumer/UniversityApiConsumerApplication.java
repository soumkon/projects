package com.projects.universityapiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UniversityApiConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApiConsumerApplication.class, args);
	}

}
