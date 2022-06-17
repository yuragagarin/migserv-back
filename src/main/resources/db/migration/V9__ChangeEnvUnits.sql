begin;

alter table env_unit_types
drop descr;

alter table env_units
add column code varchar(30);

alter table env_units
alter column env_unit_type_id set data type integer;

alter table env_unit_types
alter column id set data type integer;

commit;

/*
begin;

alter table env_unit_types
add column descr character varying(200);

alter table env_units
drop code;

alter table env_units
alter column env_unit_type_id set data type bigint;

alter table env_unit_types
alter column id set data type bigint;

commit;
*/