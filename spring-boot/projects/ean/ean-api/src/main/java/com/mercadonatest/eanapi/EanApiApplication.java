package com.mercadonatest.eanapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EanApiApplication {
	public static final String DEV_PROFILE = "dev";
	public static final String TEST_PROFILE = "test";
	public static final String NON_TEST_PROFILE = "!test";
	public static final String INT_TEST_PROFILE = "int-test";

	public static void main(String[] args) {
		SpringApplication.run(EanApiApplication.class, args);
	}

}
