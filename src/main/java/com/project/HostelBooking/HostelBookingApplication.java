package com.project.HostelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.project.HostelBooking.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.project.HostelBooking.repositories.mongo")
public class HostelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostelBookingApplication.class, args);
	}

}
