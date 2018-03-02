package com.kutaybezci.apartment.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.kutaybezci.apartment"})
public class ShellApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShellApplication.class, args);
	}
}
