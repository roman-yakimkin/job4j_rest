package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.domain.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * The report controller class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportCtrl {

    @Autowired
    private RestTemplate rest;

    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @GetMapping("/")
    public List<Report> findAll() {
        List<Report> result = new ArrayList<>();
        List<Person> people = rest.exchange(
                API,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() { }
        ).getBody();
        for (Person person : people) {
            Report report = Report.of(1, "First", person);
            result.add(report);
        }
        return result;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
