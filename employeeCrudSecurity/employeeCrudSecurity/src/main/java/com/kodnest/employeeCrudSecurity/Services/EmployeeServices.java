package com.kodnest.employeeCrudSecurity.Services;
import com.kodnest.employeeCrudSecurity.Entities.Employee;
import com.kodnest.employeeCrudSecurity.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServices {

    @Autowired
    EmployeeRepository repository;

    public Employee onboard(Employee employee) {
        return repository.save(employee);
    }

    public Employee getEmployee(int id) {
       return repository.findById(id).get();
    }

    public List<Employee> getAllEmployee() {
        return repository.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        return repository.save(employee);
    }

    public String  deleteEmployee(int id) {
        Employee employee = getEmployee(id);
        repository.delete(employee);
        return "Employee Deleted Successfully";
    }
}
