package com.AniMy;

import com.AniMy.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class AniMyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AniMyApplication.class, args);
	}

}
