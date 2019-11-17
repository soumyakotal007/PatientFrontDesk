package com.frontdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${specialist.list.file.location}")
@EnableCaching
public class PatientFrontDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientFrontDeskApplication.class, args);
	}

}
