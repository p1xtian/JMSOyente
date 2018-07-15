package com.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
public class DemoJmsOyenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJmsOyenteApplication.class, args);
	}
}
