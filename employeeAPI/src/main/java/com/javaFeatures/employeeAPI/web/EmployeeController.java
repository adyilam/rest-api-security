package com.javaFeatures.employeeAPI.web;

import com.javaFeatures.employeeAPI.domain.Employee;
import com.javaFeatures.employeeAPI.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployeeById(id));
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> existingEmployee = Optional.ofNullable(employeeService.getEmployeeById(id));

        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            employee.setSalary(updatedEmployee.getSalary());

            Employee savedEmployee = employeeService.updateEmployee(employee);
            return ResponseEntity.ok(savedEmployee);  // 200 OK with updated employee
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found if employee doesn't exist
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/salary/{id}")
    public ResponseEntity<Employee> getSalaryById(@PathVariable Long id) {
        Optional<Employee> employeeSalary = Optional.ofNullable(employeeService.getEmployeeById(id));
        return employeeSalary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
