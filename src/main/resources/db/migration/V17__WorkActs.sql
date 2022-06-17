begin;

CREATE TABLE public.work_acts
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    num character varying(10),
	CONSTRAINT work_acts_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.work_acts
    OWNER to postgres;

--//////////////////////

CREATE TABLE public.work_act_pos_types
(
    id smallint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 32767 CACHE 1 ),
    code character varying(10) NOT NULL,
    name character varying(50) NOT NULL,
	CONSTRAINT work_act_pos_types_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.work_act_pos_types
    OWNER to postgres;

insert into work_act_pos_types(code,name) select 'change','Замена запчасти';
insert into work_act_pos_types(code,name) select 'verify','Поверка';
insert into work_act_pos_types(code,name) select 'equip','Обслуживание оборудования';

--//////////////////////

CREATE TABLE public.work_act_poss
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    type_id smallint NOT NULL,
	unit_id bigint NOT NULL,
	oper_id bigint NOT NULL,
	work_act_id bigint NOT NULL,
	CONSTRAINT work_act_poss_pkey PRIMARY KEY (id),
	CONSTRAINT fk_work_act FOREIGN KEY (work_act_id)
        REFERENCES public.work_acts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_work_act_pos_type FOREIGN KEY (type_id)
        REFERENCES public.work_act_pos_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.work_act_poss
    OWNER to postgres;

--//////////////////////

CREATE TABLE public.work_act_opers
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(20) NOT NULL,
	date_fn timestamp with time zone,
    date_st timestamp with time zone NOT NULL,
	work_act_id bigint NOT NULL,
	user_id bigint NOT NULL,
	CONSTRAINT work_act_opers_pkey PRIMARY KEY (id),
	CONSTRAINT fk_work_act FOREIGN KEY (work_act_id)
        REFERENCES public.work_acts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.work_act_opers
    OWNER to postgres;

commit;

/*
begin;

drop table work_act_poss;
drop table work_act_opers;
drop table work_acts;
drop table work_act_pos_types;

commit;
*/