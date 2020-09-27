package ru.job4j.auth.domain;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The report entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
public class Report {
    private int id;
    private String name;
    private Timestamp created;
    private Person person;

    public static Report of(int id, String name, Person person) {
        Report r = new Report();
        r.id = id;
        r.name = name;
        r.person = person;
        r.created = new Timestamp(System.currentTimeMillis());
        return r;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        Report report = (Report) o;
        return getId() == report.getId() && Objects.equals(getCreated(), report.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreated());
    }
}
