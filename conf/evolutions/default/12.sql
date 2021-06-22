# --- !Ups

alter table task add column is_notified boolean not null default false;
alter table users drop column telegram;

# --- !Downs

alter table task drop column is_notified;
alter table users add column telegram varchar null;