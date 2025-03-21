package com.festapp.FestApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.festapp.FestApplication")
@EntityScan(basePackages = "com.festapp.FestApplication.models")
public class FestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FestApplication.class, args);
	}

}
