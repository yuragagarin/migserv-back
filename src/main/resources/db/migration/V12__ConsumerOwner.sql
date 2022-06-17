begin;

alter table consumers
add column serviceman_id bigint;

alter table consumers
add constraint fk_serviceman
foreign key (serviceman_id)
references users (id);

alter table consumers
add column serviceman_gas_id bigint;

alter table consumers
add constraint fk_serviceman_gas
foreign key (serviceman_gas_id)
references users (id);

commit;

/*
begin;

alter table consumers
drop constraint fk_serviceman;

alter table consumers
drop serviceman_id;

alter table consumers
drop constraint fk_serviceman_gas;

alter table consumers
drop serviceman_gas_id;

commit;
*/