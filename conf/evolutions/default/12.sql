# --- !Ups

alter table task add column is_notified boolean not null default false;

# --- !Downs

<<<<<<< Updated upstream
alter table task drop column is_notified;
=======
alter table task drop column is_notified;
>>>>>>> Stashed changes
