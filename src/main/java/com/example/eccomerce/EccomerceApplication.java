package com.example.eccomerce;

//import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EccomerceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EccomerceApplication.class, args);
        System.out.println("Eccomerce Application Started");
	}

}
