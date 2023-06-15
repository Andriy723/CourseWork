package ua.lviv.iot.algo.part1.courseWork.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RestApplication {
	public static void main(final String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}
}
