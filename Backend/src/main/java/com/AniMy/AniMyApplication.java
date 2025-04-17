package com.AniMy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AniMyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AniMyApplication.class, args);
	}

}
