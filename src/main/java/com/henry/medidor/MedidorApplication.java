package com.henry.medidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedidorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedidorApplication.class, args);
	}

}
