package com.hayan.hello_traveler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HelloTravelerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloTravelerApplication.class, args);
	}

}
