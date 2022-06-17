begin;
insert into permissions(code,name)
select 'catalog:m22','Меню: Справочники::Организации';

insert into menu_items(icon_name,level,name,ord,path,parent_id,permission_id)
select 'cil-factory',2,'Организации','10','/catalogs/consumers',(select id from menu_items where name = 'Справочники'),currval('permissions_id_seq');

insert into role_permissions
select (select id from roles where name = 'Все'),currval('permissions_id_seq');

insert into role_permissions
select (select id from roles where name = 'Администратор'),currval('permissions_id_seq');

insert into role_permissions
select (select id from roles where name = 'Директор СЦ'),currval('permissions_id_seq');
commit;

/*
begin;
delete from menu_items where permission_id = (select id from permissions where code = 'catalog:m22');
delete from role_permissions where permission_id = (select id from permissions where code = 'catalog:m22');
delete from permissions where code = 'catalog:m22';
commit;
*/