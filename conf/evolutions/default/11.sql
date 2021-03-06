# --- !Ups

create table if not exists notification(
    id bigint generated by default as identity(start with 1) not null primary key,
    user_id bigint not null,
    telegram varchar null,
    CONSTRAINT fk_user_notification FOREIGN KEY
    (user_id) REFERENCES users(id),
    before_minutes int not null,
    created timestamp not null,
    updated timestamp not null
);

# --- !Downs
drop table notification cascade;