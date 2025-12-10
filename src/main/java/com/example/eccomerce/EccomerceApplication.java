package com.example.eccomerce;

//import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EccomerceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EccomerceApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port", "8081");
        String contextPath = env.getProperty("contextPath");
        String basePackage = env.getProperty("basePackage");
        String appName = env.getProperty("appName");
        String appVersion = env.getProperty("appVersion");
        String email = env.getProperty("email");
        String host = env.getProperty("host");
//        port = "8080";
        System.out.println("App URL : http://localhost:" + port);
        System.out.println("Eccomerce Application Started");
	}

}
