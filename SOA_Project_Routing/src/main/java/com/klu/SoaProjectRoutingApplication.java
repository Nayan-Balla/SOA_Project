package com.klu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SoaProjectRoutingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoaProjectRoutingApplication.class, args);
	}

}
