package com.klu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SoaProjectNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoaProjectNotificationApplication.class, args);
	}

}
