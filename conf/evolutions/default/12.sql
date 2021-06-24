# --- !Ups

alter table task add column is_notified boolean not null default false;

# --- !Downs

alter table task drop column is_notified;
