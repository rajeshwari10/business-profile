package com.example.businessprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableHystrix
public class BusinessprofileApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessprofileApplication.class, args);
	}

}
