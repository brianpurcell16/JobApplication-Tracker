package com.jobtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 * @SpringBootApplication is shorthand for:
 *   @Configuration       — marks this as a source of bean definitions
 *   @EnableAutoConfiguration — lets Spring Boot auto-configure based on dependencies
 *   @ComponentScan       — scans com.jobtracker and sub-packages for @Component classes
 */


@SpringBootApplication
public class JobTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobTrackerApiApplication.class, args);
	}

}
