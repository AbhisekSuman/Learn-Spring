package com.kodnest.EmpoloyeeDataApp.Services;

import com.kodnest.EmpoloyeeDataApp.Entity.Employee;
import com.kodnest.EmpoloyeeDataApp.Repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpService {

    @Autowired
    EmpRepository repository;

    public Employee getData(int id) {
        return repository.findById(id).get();
    }
}
