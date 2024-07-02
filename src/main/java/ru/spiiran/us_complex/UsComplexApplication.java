package ru.spiiran.us_complex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class UsComplexApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsComplexApplication.class, args);
	}

}
