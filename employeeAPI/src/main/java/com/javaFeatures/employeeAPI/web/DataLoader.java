package com.javaFeatures.employeeAPI.web;

import com.javaFeatures.employeeAPI.domain.Employee;
import com.javaFeatures.employeeAPI.domain.User;
import com.javaFeatures.employeeAPI.repository.EmployeeRepository;
import com.javaFeatures.employeeAPI.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole("USER");
            userRepository.save(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            Employee emp1 = new Employee();
            emp1.setName("Alex Smith");
            emp1.setEmail("alex.amith@gmail.com");
            emp1.setDepartment("Engineering");
            emp1.setPhoneNumber("123-435-678");
            emp1.setSalary(200000.00);
            employeeRepository.save(emp1);

            Employee emp2 = new Employee();
            emp2.setName("Helen Tomas");
            emp2.setEmail("helen.tomas@gmail.com");
            emp2.setDepartment("HR");
            emp2.setPhoneNumber("331-456-998");
            emp2.setSalary(300000.00);
            employeeRepository.save(emp2);

            System.out.println("Users and Employees added to H2 database!");
        }
    }
}