package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.domain.Report;
import ru.job4j.auth.repository.EmployeeRepository;
import ru.job4j.auth.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The employee control
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeCtrl {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestTemplate rest;

    @GetMapping("/")
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        employeeRepository.findAll().forEach(result::add);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = this.employeeRepository.findById(id);
        return new ResponseEntity<>(
                employee.orElse(new Employee()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }



}
