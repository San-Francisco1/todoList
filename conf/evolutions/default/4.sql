# --- !Ups

INSERT INTO priority(title, color) VALUES
    ('hot', '#A31012'),
    ('major', '#f55306'),
    ('minor', '#050394'),
    ('trivial', '#fff')

# --- !Downs
delete from priority;