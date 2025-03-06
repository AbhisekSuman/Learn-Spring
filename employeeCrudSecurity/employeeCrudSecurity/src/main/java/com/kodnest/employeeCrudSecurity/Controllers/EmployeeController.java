package com.kodnest.employeeCrudSecurity.Controllers;


import com.kodnest.employeeCrudSecurity.Entities.Employee;
import com.kodnest.employeeCrudSecurity.Services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeServices services;

    @PostMapping("/employee/onboard")
    public Employee onboard(@RequestBody Employee employee) {
        return services.onboard(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return services.getEmployee(id);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return services.getAllEmployee();
    }

    @PutMapping("/employee/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return services.updateEmployee(employee);
    }

    @DeleteMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return services.deleteEmployee(id);
    }
}
