package ru.job4j.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Account Ids for employee
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
@Entity
@Table(name = "employee_account")
public class AccountId implements Serializable {

    @Id
    @Column(name = "employee_id")
    private Integer employeeId;

    @Id
    @Column(name = "account_id")
    private Integer accountId;

    public AccountId() { }

    public AccountId(Integer employeeId, Integer accountId) {
        this.employeeId = employeeId;
        this.accountId = accountId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountId)) {
            return false;
        }
        AccountId accountId1 = (AccountId) o;
        return employeeId.equals(accountId1.employeeId) && accountId.equals(accountId1.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, accountId);
    }
}
