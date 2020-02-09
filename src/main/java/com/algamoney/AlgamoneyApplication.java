package com.algamoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.algamoney.config.property.AlgamoneyProperty;

@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyProperty.class)
public class AlgamoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamoneyApplication.class, args);
	}

}
