begin;

insert into users(email,name,password,patronymic,phone_number,status,surname,updated,username,user_post_id,user_id)
--select '','Антон',crypt('qwerty',gen_salt('bf', 8)),'Евгеньевич','','ACTIVE','Афанасьев',now(),'afanasev_ae',(select id from user_posts where name = 'Директор СЦ'),1;
select '','Антон','$2a$08$QGsXyj.UEZS6C8e1xrxLvesDQOnO4bvW1RBfCaDJjJH6Veg7FKXnG','Евгеньевич','','ACTIVE','Афанасьев',now(),'afanasev_ae',(select id from user_posts where name = 'Директор СЦ'),1;

insert into user_roles(user_id,role_id)
select currval('users_id_seq'),(select id from  roles where name = 'Директор СЦ');

insert into users(email,name,password,patronymic,phone_number,status,surname,updated,username,user_post_id,user_id)
--select '','Александр',crypt('qwerty',gen_salt('bf', 8)),'Викторович','','ACTIVE','Фролов',now(),'frolov_av',(select id from user_posts where name = 'Зам. директора СЦ'),1;
select '','Александр','$2a$08$Qo5SsJvWzet0JmQ35RieyOBbiJQVBmX6Y32H9ERDKsr.UUk0xW.Li','Викторович','','ACTIVE','Фролов',now(),'frolov_av',(select id from user_posts where name = 'Зам. директора СЦ'),1;

insert into user_roles(user_id,role_id)
select currval('users_id_seq'),(select id from  roles where name = 'Директор СЦ');

commit;

/*
begin;

delete from user_roles where user_id = (select max(id) from users);
delete from users where id = (select max(id) from users);

delete from user_roles where user_id = (select max(id) from users);
delete from users where id = (select max(id) from users);

commit;
*/