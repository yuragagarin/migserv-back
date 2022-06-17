begin;

alter table equip_servs alter column date_st drop not null;

commit;

/*
begin;

alter table equip_servs alter column date_st set not null;

commit;
*/