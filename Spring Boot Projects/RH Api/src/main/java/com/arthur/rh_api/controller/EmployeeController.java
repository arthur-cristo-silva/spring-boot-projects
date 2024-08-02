package com.arthur.rh_api.controller;

import com.arthur.rh_api.entity.Employee;
import com.arthur.rh_api.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> list() {
        return employeeService.list();
    }

    @PostMapping
    public void create(@RequestBody Employee employee) {
        employeeService.create(employee);
    }

    @PutMapping
    public void update(@RequestBody Employee employee) {
        employeeService.update(employee);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }

    @GetMapping("{id}")
    public Employee findById(@PathVariable("id") Long id) {
        return employeeService.findById(id);
    }
}
