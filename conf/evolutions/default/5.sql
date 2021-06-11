# --- !Ups

INSERT INTO priority VALUES
    ("Hot", "Red"),
    ("Major", "Orange"),
    ("Minor", "Blue"),
    ("Trivial", "White")

# --- !Downs
delete from priority;