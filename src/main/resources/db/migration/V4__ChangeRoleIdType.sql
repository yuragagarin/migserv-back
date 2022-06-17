begin;
alter table roles
alter column id set data type integer;

alter table role_permissions
alter column role_id set data type integer;

alter table user_roles
alter column role_id set data type integer;
commit;

/*
begin;
alter table roles
alter column id set data type bigint;

alter table role_permissions
alter column role_id set data type bigint;

alter table user_roles
alter column role_id set data type bigint;
commit;
*/