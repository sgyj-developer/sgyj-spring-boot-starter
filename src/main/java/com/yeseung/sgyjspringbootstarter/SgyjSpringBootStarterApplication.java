package com.yeseung.sgyjspringbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.yeseung")
public class SgyjSpringBootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgyjSpringBootStarterApplication.class, args);
	}

}
