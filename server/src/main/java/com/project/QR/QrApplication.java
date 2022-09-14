package com.project.QR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QrApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrApplication.class, args);
	}

}
