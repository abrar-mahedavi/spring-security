package com.abu.securitybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Security basic application.
 */
@SpringBootApplication
// @ComponentScan("com.abu.securitybasic.controller") 	//Fix the package is outside scope then you have to specify it, use this annotation.
public class SecurityBasicApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
        SpringApplication.run(SecurityBasicApplication.class, args);
    }

}
