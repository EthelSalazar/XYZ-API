package com.DietWater.dietwaterxyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DietWaterXyzApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietWaterXyzApplication.class, args);
	}

}
