package com.fengx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FengDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FengDemoApplication.class, args);
	}

}
