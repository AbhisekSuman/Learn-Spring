package com.kodnest.employeeCrudSecurity.Repositories;

import com.kodnest.employeeCrudSecurity.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
