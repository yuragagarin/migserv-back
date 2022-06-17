-- FUNCTION: public.fio(character varying, character varying, character varying)

-- DROP FUNCTION public.fio(character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public.fio(
	surname character varying,
	name character varying,
	patronymic character varying)
    RETURNS character varying
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$begin
   return surname || ' ' || LEFT(name, 1)  || '.' || LEFT(patronymic, 1) || '.';
end;
$BODY$;

ALTER FUNCTION public.fio(character varying, character varying, character varying)
    OWNER TO postgres;