begin;

alter table env_units
drop verifying_date;

commit;

/*
begin;

alter table env_units
add column verifying_date timestamp with time zone;

commit;
*/