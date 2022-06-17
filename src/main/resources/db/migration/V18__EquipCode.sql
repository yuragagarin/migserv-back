begin;

alter table equips
add column code varchar(30);

commit;

/*
begin;

alter table equips
drop code;

commit;
*/