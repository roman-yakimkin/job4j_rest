drop table if exists person;

create table person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000)
);

alter sequence if exists person_id_sql restart with 1;
