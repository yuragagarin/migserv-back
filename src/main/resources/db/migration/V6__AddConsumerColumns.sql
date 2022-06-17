begin;

alter table consumers
drop trans_name;

alter table consumers
add column address character varying(250);

alter table consumers
add column designated character varying(100);

alter table consumers
add column designated_phone character varying(100);

commit;

/*
begin;

alter table consumers
add column trans_name character varying(200);

alter table consumers
drop address;

alter table consumers
drop designated;

alter table consumers
drop designated_phone;

commit;
*/