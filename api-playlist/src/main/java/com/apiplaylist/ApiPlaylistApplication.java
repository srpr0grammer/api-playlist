package com.apiplaylist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.apiplaylist.repository", "com.apiplaylist.security", ...})
public class ApiPlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPlaylistApplication.class, args);
	}

}
