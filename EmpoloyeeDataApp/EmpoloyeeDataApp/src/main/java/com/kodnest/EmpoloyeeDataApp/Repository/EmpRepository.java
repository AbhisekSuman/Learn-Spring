package com.kodnest.EmpoloyeeDataApp.Repository;

import com.kodnest.EmpoloyeeDataApp.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {

}
