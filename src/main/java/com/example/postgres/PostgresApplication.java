package com.example.postgres;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
@Log
public class PostgresApplication  {

	public static void main(String[] args) {
		SpringApplication.run(PostgresApplication.class, args);
	}

}