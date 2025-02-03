package com.example.accessingdatajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class AccessingDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataJpaApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(CustomerRepository repository){
		return(args -> {
			// save a customer
			repository.save(Customer.builder()
				.firstName("jungha")
				.lastName("kim")
				.createBy("SYSTEM")
				.createdDate(LocalDateTime.now())
				.lastModifiedBy("SYSTEM")
				.lastModifiedDate(LocalDateTime.now())
				.build());

			log.info("--------------------------------------------");
		});
	}


}
