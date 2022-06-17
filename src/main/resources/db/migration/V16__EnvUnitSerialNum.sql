begin;

alter table env_units
add column serial_num varchar(20);

commit;

/*
begin;

alter table env_units
drop serial_num;

commit;
*/