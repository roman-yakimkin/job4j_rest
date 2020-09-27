package ru.job4j.auth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The employee entity class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
@Entity
@Table(name = "employee")
public class Employee {
    private static final String PERSON_API = "http://localhost:8080/person/";

    private static RestTemplate rest = new RestTemplate();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "tin")
    private long taxpayerIndividualNumber;

    @Column(name = "hired")
    private Calendar hireDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Set<AccountId> accountIds = new HashSet<>();

    private static int genId = 1;

    public static int uniqueId() {
        return genId++;
    }

    public static Employee of(int id, String firstName, String lastName, long taxpayerIndividualNumber, Calendar hideDate) {
        Employee employee = new Employee();
        employee.id = id;
        employee.firstName = firstName;
        employee.lastName = lastName;
        employee.taxpayerIndividualNumber = taxpayerIndividualNumber;
        employee.hireDate = hideDate;
        return  employee;
    }

    public static Employee of(String firstName, String lastName, long taxpayerIndividualNumber, Calendar hireDate) {
        Employee employee = new Employee();
        employee.id = uniqueId();
        employee.firstName = firstName;
        employee.lastName = lastName;
        employee.taxpayerIndividualNumber = taxpayerIndividualNumber;
        employee.hireDate = hireDate;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getTaxpayerIndividualNumber() {
        return taxpayerIndividualNumber;
    }

    public void setTaxpayerIndividualNumber(long taxpayerIndividualNumber) {
        this.taxpayerIndividualNumber = taxpayerIndividualNumber;
    }

    public Calendar getHireDate() {
        return hireDate;
    }

    public void setHireDate(Calendar hireDate) {
        this.hireDate = hireDate;
    }

    public Set<Person> getAccounts() {
        Set<Person> people = rest.exchange(
                PERSON_API,
                HttpMethod.GET, null, new ParameterizedTypeReference<Set<Person>>() { }
        ).getBody();
        people = (people != null)
                ? people.stream().filter(person -> accountIds.contains(new AccountId(this.id, person.getId()))).collect(Collectors.toSet())
                : Collections.emptySet();
        return people;
    }

    public void setAccounts(Set<Person> accounts) {
//        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
