begin;

alter table env_units
add column work_time smallint;

commit;

/*
begin;

alter table consumers
drop work_time;

commit;
*/