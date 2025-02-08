package com.javaFeatures.employeeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Use http://localhost:8080/h2-console/login.jsp url to access H2 db.
 * Use the same credential we have used in application.properties file
 */
@SpringBootApplication
public class EmployeeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApiApplication.class, args);
    }

}
