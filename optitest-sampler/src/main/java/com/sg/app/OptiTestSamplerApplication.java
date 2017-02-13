package com.sg.app;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author Karoonakar
 *
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan
@EnableJms
public class OptiTestSamplerApplication {
	
	public static void main(String[] args) {
		 
		SpringApplication.run(OptiTestSamplerApplication.class, args);
	}
}