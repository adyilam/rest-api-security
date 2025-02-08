package com.javaFeatures.employeeAPI.repository;

import com.javaFeatures.employeeAPI.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);

    Employee findSalaryById(Long id);
}

