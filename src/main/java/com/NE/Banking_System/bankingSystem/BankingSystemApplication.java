package com.NE.Banking_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankingSystemApplication.class, args);
		System.out.println("POSTMAN IS RUNNING ON http://localhost:8080/");
		System.out.println("SWAGGER IS RUNNING ON http://localhost:8080/swagger-ui.html");
	}

}
