# --- !Ups

create table if not exists user(
    id bigint generated by default as identity(start with 1) not null primary key,
    firstname varchar not null,
    lastname varchar not null,
    email varchar not null,
    phone varchar not null,
    telegram varchar null,
    created date not null,
    updated date null,
    deleted date null
);

# --- !Downs
drop table user cascade;