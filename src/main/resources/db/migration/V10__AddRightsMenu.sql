begin;
update permissions set name = replace(name,'>','::');

alter table permissions
add column type varchar(30);

update  permissions
set name = replace(name, 'Меню:', ''), type = 'Меню'
where name like 'Меню%';

insert into permissions(code,name,type)
select 'access:m1','Управление доступом','Меню';

insert into menu_items(level,name,ord,permission_id)
select 1,'Управление доступом',30,currval('permissions_id_seq');

insert into permissions(code,name,type)
select 'access:m1:1','Управление доступом::Разрешения','Меню';

insert into menu_items(icon_name,level,name,ord,path,parent_id,permission_id)
select 'cil-task',2,'Разрешения','5','/access/permissions',(select id from menu_items where name = 'Управление доступом'),currval('permissions_id_seq');

insert into permissions(code,name,type)
select 'access:m1:2','Управление доступом::Роли','Меню';

insert into menu_items(icon_name,level,name,ord,path,parent_id,permission_id)
select 'cil-people',2,'Роли','10','/access/roles',(select id from menu_items where name = 'Управление доступом'),currval('permissions_id_seq');

insert into permissions(code,name,type)
select 'access:m1:3','Управление доступом::Меню','Меню';

insert into menu_items(icon_name,level,name,ord,path,parent_id,permission_id)
select 'cil-stream',2,'Меню','15','/access/menu',(select id from menu_items where name = 'Управление доступом'),currval('permissions_id_seq');

insert into role_permissions(role_id,permission_id)
select (select id from roles where name = 'Администратор'),(select id from permissions where code = 'access:m1');

insert into role_permissions(role_id,permission_id)
select (select id from roles where name = 'Администратор'),(select id from permissions where code = 'access:m1:1');

insert into role_permissions(role_id,permission_id)
select (select id from roles where name = 'Администратор'),(select id from permissions where code = 'access:m1:2');

insert into role_permissions(role_id,permission_id)
select (select id from roles where name = 'Администратор'),(select id from permissions where code = 'access:m1:3');

commit;

/*
begin;

update  permissions
set name = 'Меню:'||name, type = 'Меню'
where type = 'Меню';

alter table permissions
drop type;

delete from role_permissions where permission_id = (select id from permissions where code = 'access:m1');
delete from role_permissions where permission_id = (select id from permissions where code = 'access:m1:1');
delete from role_permissions where permission_id = (select id from permissions where code = 'access:m1:2');
delete from role_permissions where permission_id = (select id from permissions where code = 'access:m1:3');

delete from menu_items where name = 'Меню';
delete from permissions where name = 'Меню:Управление доступом::Меню';
delete from menu_items where name = 'Роли';
delete from permissions where name = 'Меню:Управление доступом::Роли';
delete from menu_items where name = 'Разрешения';
delete from permissions where name = 'Меню:Управление доступом::Разрешения';
delete from menu_items where name = 'Управление доступом';
delete from permissions where name = 'Меню:Управление доступом';

commit;
*/