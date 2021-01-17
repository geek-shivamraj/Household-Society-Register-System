package com.srvcode.koel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.srvcode.koel.*"})
public class ProjectKoelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectKoelApplication.class, args);
	}

}
