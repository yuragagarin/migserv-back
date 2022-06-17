begin;

alter table roles
drop created;

commit;

/*
begin;

alter table roles
add column created timestamp with time zone;

commit;
*/