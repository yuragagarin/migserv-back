begin;

    update env_unit_oper_names set name = 'Снятие' where name = 'Замена';

    alter table env_unit_opers
    add column ref_env_unit_oper_id bigint;

    alter table env_unit_opers
    add constraint fk_env_unit_opers
    foreign key (ref_env_unit_oper_id)
    references env_unit_opers (id);

commit;

/*
begin;

    update env_unit_oper_names set name = 'Замена' where name = 'Снятие';

    alter table env_unit_opers
    drop constraint fk_env_unit_opers;

    alter table env_unit_opers
    drop ref_env_unit_oper_id;

commit;
*/