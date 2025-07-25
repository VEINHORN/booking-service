package com.veinhorn.booking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BookingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookingSystemApplication.class, args);
	}
}
