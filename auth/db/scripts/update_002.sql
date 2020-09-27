drop table if exists employee_account;
drop table if exists employee;

create table employee (
    id              serial primary key  not null,
    firstname       varchar(200) not null,
    lastname        varchar(200) not null,
    tin             bigint,
    hired           timestamp without time zone
);

create table employee_account (
    employee_id     int references employee(id),
    account_id      int references person(id)
);

insert into employee (id, firstname, lastname, tin, hired) values (1, 'Petr', 'Arsentev', 615402111222, '2020-01-03 00:00:00');
insert into employee (id, firstname, lastname, tin, hired) values (2, 'Ivan', 'Ivanov', 615401345678, '2012-02-08 00:00:00');

insert into employee_account (employee_id, account_id) values (1, 1), (1, 2), (2, 3);

