package com.example.Bookify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookifyApplication.class, args);
	}


}
