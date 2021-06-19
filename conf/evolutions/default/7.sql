# --- !Ups

alter table task add column user_id bigint not null default 0;

# --- !Downs

alter table task drop column user_id;
