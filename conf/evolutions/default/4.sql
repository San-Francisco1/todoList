# --- !Ups

create table if not exists task(
    id bigint generated by default as identity(start with 1) not null primary key,
    title varchar not null,
    description text null,
    tasktype_id bigint not null,
    CONSTRAINT fk_task_tasktype FOREIGN KEY
    (tasktype_id) REFERENCES task_type(id),
    due_date timestamp not null,
    created timestamp not null,
    updated timestamp null,
    deleted timestamp null
);

# --- !Downs
drop table task cascade;