# --- !Ups

UPDATE priority SET color =  '#dc3545' WHERE id = 1;
UPDATE priority SET color =  '#ffc107' WHERE id = 2;
UPDATE priority SET color = '##198754' WHERE id = 3;
UPDATE priority SET color =  '#0d6efd' WHERE id = 4;

# --- !Downs
UPDATE priority SET color = '#A31012' WHERE id = 1;
UPDATE priority SET color = '#f55306' WHERE id = 2;
UPDATE priority SET color = '#050394' WHERE id = 3;
UPDATE priority SET color =   '#0fff' WHERE id = 4;