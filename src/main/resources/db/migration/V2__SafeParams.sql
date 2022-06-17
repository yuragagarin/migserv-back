delete from safe_events;

delete from rd_params where trans_name in('safe_1','safe_2','safe_3');

insert into rd_params(status,updated,name,path_src,trans_name,update_user_id,param_type_id,rd_job_conf_id)
select 'ACTIVE', CURRENT_TIMESTAMP, 'Авария кот-я 1' ,'192.168.1.227:1829' , 'Avaria_kot_1', 1,1,2;

insert into rd_params(status,updated,name,path_src,trans_name,update_user_id,param_type_id,rd_job_conf_id)
select 'ACTIVE', CURRENT_TIMESTAMP, 'Авария кот-я 1' ,'192.168.1.227:1829' , 'Avaria_kot_2', 1,1,2;

insert into rd_params(status,updated,name,path_src,trans_name,update_user_id,param_type_id,rd_job_conf_id)
select 'ACTIVE', CURRENT_TIMESTAMP, 'Клапан газ' ,'192.168.1.227:1829' , 'Klapan_gaz', 1,1,2;

insert into rd_params(status,updated,name,path_src,trans_name,update_user_id,param_type_id,rd_job_conf_id)
select 'ACTIVE', CURRENT_TIMESTAMP, 'Электро' ,'192.168.1.227:1829' , 'Elektro', 1,1,2;