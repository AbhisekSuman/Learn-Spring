package com.kodnest.EmpoloyeeDataApp.Controller;

import com.kodnest.EmpoloyeeDataApp.Entity.Employee;
import com.kodnest.EmpoloyeeDataApp.Services.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {

    @Autowired
    EmpService service;

    @GetMapping("/api/getUser/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return service.getData(id);
    }
}
