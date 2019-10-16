package com.mywebproject.mywebproject;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value="com.mywebproject.*")
@MapperScan("com.mywebproject.dao")
public class MywebprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MywebprojectApplication.class, args); 
	}
}