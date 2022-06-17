package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.dao.main.repo.filter.EnvUnitFilter;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.dto.envUnit.*;

import java.util.List;

@Repository
public interface EnvUnitRepository extends JpaRepository<EnvUnit, Long>, EnvUnitFilter/*, QuerydslPredicateExecutor<EnvUnit>, QuerydslBinderCustomizer<QEnvUnit>*/ {

    /*@Override
    default void customize(
            QuerydslBindings bindings, QEnvUnit root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.including(root.number);
    }*/

    @Query(value = "select distinct eu.id, eu.number, eu.env_unit_type_id envUnitTypeId, eu.code, eut.name envUnitType, euo.date_st, eu.work_time workTime" +
            ", eu.passport,eut.category envUnitCategory, eu.serial_num serialNum  from env_units eu \n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id\n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id\n" +
            "where euon.name = 'Поступление'\n" +

            "and (cast(:code as varchar) is null or eu.code = cast(:code as varchar))\n" +
            "and (cast(:number as varchar) is null or eu.number = cast(:number as varchar))\n" +
            "and (cast(:serialNum as varchar) is null or eu.serial_num = cast(:serialNum as varchar))\n" +
            "and (cast(:passport as varchar) is null or eu.passport = cast(:passport as varchar))\n" +
            "and (cast(:envUnitCategory as varchar) is null or eut.category = cast(:envUnitCategory as varchar))\n" +
            "and euo.date_fn is null\n" +
            "order by euo.date_st desc", nativeQuery = true)
    List<EnvUnitViewDto> getIncomes(@Param("code") String code, @Param("number") String number,
                                    @Param("serialNum") String serialNum, @Param("passport") String passport,
                                    @Param("envUnitCategory") String envUnitCategory);

    @Query(value = "select eu.id,c.name consumerName, eq.name equipName, eut.name envUnitType, eu.number, euo.date_st installDate, eu.passport, eu.code, eu.serial_num serialNum, euo.id envUnitOperId \n" +
            "from env_units eu \n" +
            "join consumers c on c.id = eu.consumer_id \n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id \n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id\n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "where euon.name = 'Установка' and (:userId = 0 OR euo.user_id =:userId) \n" +
            "and (:consumerId = 0 OR c.id = :consumerId)\n" +
            "and euo.date_fn is null\n" +
            "order by euo.date_st desc", nativeQuery = true)
    List<EnvUnitViewDto> getSetups(@Param("userId") long userId, @Param("consumerId") long consumerId);

    @Query(value = "select eu.id,c.name consumerName, eq.name equipName, eut.name envUnitType,eu.code, eut.id envUnitTypeId, eu.number, euo.date_st installDate" +
            ", euo.expir_date expirationDate,eut.category envUnitCategory, eu.serial_num serialNum" +
            ",(case when date_part('day', euo.expir_date - current_timestamp) >= 59 then 'success' \n" +
            "when date_part('day', euo.expir_date - current_timestamp) < 59 and date_part('day', euo.expir_date - current_timestamp) > 29 then 'risk' \n" +
            "when date_part('day', euo.expir_date - current_timestamp) <= 29 then 'danger'\n" +
            "end) status\n" +
            "from env_units eu\n" +
            "join consumers c on c.id = eu.consumer_id\n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id\n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id\n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "where euon.name = 'Установка' and  euo.date_fn is null\n" +
            "and (:consumerId = 0 OR c.id = :consumerId)\n" +
            "and (:userId = 0 OR euo.user_id = :userId)\n" +
            "and (cast(:code as varchar) is null or eu.code = cast(:code as varchar))\n" +
            "and (cast(:number as varchar) is null or eu.number = cast(:number as varchar))\n" +
            "and (cast(:serialNum as varchar) is null or eu.serial_num = cast(:serialNum as varchar))\n" +
            "and (cast(:passport as varchar) is null or eu.passport = cast(:passport as varchar))\n" +
            "order by status asc,euo.date_st desc", nativeQuery = true)
    List<EnvUnitViewDto> getChanges(@Param("code") String code, @Param("number") String number,
                                    @Param("serialNum") String serialNum, @Param("passport") String passport,
                                    @Param("consumerId") long consumerId, @Param("userId") long userId);

    @Query(value = "select eu.id,c.name consumerName, eq.name equipName, eut.name envUnitType,eu.number,euo.date_st installDate,\n" +
            "euo.expir_date expirationDate, euo.date_fn changeDate, eu.serial_num serialNum, \n" +
            "eu.code, (select max(id) from env_unit_opers where env_unit_id = eu.id) envUnitOperId \n" +
            ",(case when euo.date_fn>euo.expir_date then 'danger' else 'success' end) status, eu.passport\n" +
            "from env_units eu\n" +
            "join consumers c on c.id = eu.consumer_id\n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id\n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id and euo.date_fn is not null\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id and euon.name = 'Установка'\n" +
            "where  (:consumerId = 0 OR c.id = :consumerId)\n" +
            "and (:userId = 0 OR euo.user_id = :userId)\n" +
            "and (cast(:code as varchar) is null or eu.code = cast(:code as varchar))\n" +
            "and (cast(:number as varchar) is null or eu.number = cast(:number as varchar))\n" +
            "and (cast(:serialNum as varchar) is null or eu.serial_num = cast(:serialNum as varchar))\n" +
            "and (cast(:passport as varchar) is null or eu.passport = cast(:passport as varchar))\n" +
            "order by euo.date_fn desc", nativeQuery = true)
    List<EnvUnitViewDto> getHistChanges(@Param("code") String code, @Param("number") String number,
                                        @Param("serialNum") String serialNum, @Param("passport") String passport,
                                        @Param("consumerId") long consumerId, @Param("userId") long userId);

    @Query(value = "select\n" +
            "(select count(*)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id and date_part('day', euo.expir_date - current_timestamp) >= 59 and euo.user_id = :userId and euo.date_fn is null and not exists(select 1 from env_unit_opers where name = 'Замена') and env_unit_id = eu.id) successCnt,\n" +
            "(select count(*)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id and date_part('day', euo.expir_date - current_timestamp) < 59 and date_part('day', euo.expir_date - current_timestamp) > 29 and euo.user_id = :userId and euo.date_fn is null and not exists(select 1 from env_unit_opers where name = 'Замена' and env_unit_id = eu.id)) warningCnt,\n" +
            "(select count(*)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id and date_part('day', euo.expir_date - current_timestamp) <= 29 and euo.user_id = :userId and euo.date_fn is null and not exists(select 1 from env_unit_opers where name = 'Замена' and env_unit_id = eu.id)) dangerCnt"
            , nativeQuery = true)
    EnvUnitStatusesDto getStatuses(@Param("userId") long userId);

    @Query(value = "select eu.id, eq.name equipName, eut.name envUnitType, eu.number" +
            ", euo_verify.expir_date nextVerifyDate, eu.serial_num serialNum,eu.code" +
            ",(case when date_part('day', euo_verify.expir_date - current_timestamp) >= 59 then 'success' \n" +
            "when date_part('day', euo_verify.expir_date - current_timestamp) < 59 and date_part('day', euo_verify.expir_date - current_timestamp) > 29 then 'risk' \n" +
            "when date_part('day', euo_verify.expir_date - current_timestamp) <= 29 then 'danger'\n" +
            "end) status\n" +
            "from env_units eu\n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id\n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id\n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "left join lateral (select euo1.date_st,euo1.expir_date from env_unit_opers euo1\n" +
            "join env_unit_oper_names euon1 on euon1.id = euo1.name_id\n" +
            "where euo1.env_unit_id=eu.id and euon1.name='Поверка' and euo1.date_fn is null order by euo1.date_st desc limit 1)  euo_verify on true\n" +
            "where euon.name = 'Установка'\n" +
            "and (:consumerId = 0 OR eu.consumer_id = :consumerId)\n" +
            "and (:userId = 0 OR euo.user_id = :userId)\n" +
            "and eut.category='Прибор'\n" +
            "and (cast(:code as varchar) is null or eu.code = cast(:code as varchar))\n" +
            "and (cast(:number as varchar) is null or eu.number = cast(:number as varchar))\n" +
            "and (cast(:serialNum as varchar) is null or eu.serial_num = cast(:serialNum as varchar))\n" +
            "and (cast(:passport as varchar) is null or eu.passport = cast(:passport as varchar))\n" +
            "order by status asc, euo.id desc", nativeQuery = true)
    List<EnvUnitViewDto> getVerifications(@Param("code") String code, @Param("number") String number,
                                          @Param("serialNum") String serialNum, @Param("passport") String passport,
                                          @Param("consumerId") long consumerId, @Param("userId") long userId);

    @Query(value = "select eu.id,c.name consumerName, eq.name equipName, eut.name envUnitType, eu.number,euo.date_st installDate" +
            ", euo_verify.date_fn verifyDate, euo_verify.expir_date nextVerifyDate, eu.serial_num serialNum, eu.code," +
            "euo_verify.id envUnitOperId," +
            "(case when euo_verify.date_fn>euo_verify.expir_date then 'danger' else 'success' end) status\n" +
            "from env_units eu\n" +
            "join consumers c on c.id = eu.consumer_id\n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id\n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id\n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "left join lateral (select euo1.date_st,euo1.expir_date,date_fn, euo1.id  from env_unit_opers euo1\n" +
            "join env_unit_oper_names euon1 on euon1.id = euo1.name_id\n" +
            "where euo1.env_unit_id=eu.id and euon1.name='Поверка' and euo1.date_fn is not null order by euo1.date_st)  euo_verify on true\n" +
            "where euon.name = 'Установка'\n" +
            "and (:consumerId = 0 OR c.id = :consumerId)\n" +
            "and (:userId = 0 OR euo.user_id = :userId)\n" +
            "and eut.category='Прибор'\n" +
            "and euo_verify.date_fn is not null\n" +
            "order by euo_verify.date_fn desc", nativeQuery = true)
    List<EnvUnitViewDto> getHistVerifications(@Param("consumerId") long consumerId, @Param("userId") long userId);

    EnvUnit findByCode(String code);

    @Query(value = "select  to_char(max(euo.date_st),'mm')||'.'||to_char(max(euo.date_st),'yy') monthNum ,count(env_unit_id) cnt from env_unit_opers euo \n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id and euon.name = 'Установка'\n" +
            "where euo.date_st between date_trunc('month', CURRENT_DATE) - INTERVAL '12 month'\n" +
            "and CURRENT_DATE\n" +
            "group by date_trunc('month', euo.date_st)", nativeQuery = true)
    List<EnvUnitViewStatDto> getInstalledInMonthLast12Monthes();

    @Query(value = "select  to_char(max(euo.date_st),'mm')||'.'||to_char(max(euo.date_st),'yy') monthNum ,count(env_unit_id) cnt from env_unit_opers euo \n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id and euon.name = 'Списание'\n" +
            "where euo.date_st between date_trunc('month', CURRENT_DATE) - INTERVAL '12 month'\n" +
            "and CURRENT_DATE\n" +
            "group by date_trunc('month', euo.date_st)", nativeQuery = true)
    List<EnvUnitViewStatDto> getChangedInMonthLast12Monthes();

    @Query(value = "select  to_char(max(euo.date_st),'mm')||'.'||to_char(max(euo.date_st),'yy') monthNum ,count(env_unit_id) cnt from env_unit_opers euo \n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id and euon.name = 'Поверка'\n" +
            "where euo.date_st between date_trunc('month', CURRENT_DATE) - INTERVAL '12 month'\n" +
            "and CURRENT_DATE\n" +
            "group by date_trunc('month', euo.date_st)", nativeQuery = true)
    List<EnvUnitViewStatDto> getVerifyInMonthLast12Monthes();

    @Query(value = "select to_char(max(euo.date_st),'mm')||'.'||to_char(max(euo.date_st),'yy') monthNum ,count(env_unit_id) cnt from env_units eu\n" +
            "join env_unit_opers euo on euo.env_unit_id = eu.id and euo.date_fn is not null\n" +
            "join env_unit_oper_names euon on euon.id = euo.name_id and euon.name = 'Установка'\n" +
            "where euo.date_fn>euo.expir_date", nativeQuery = true)
    List<EnvUnitViewStatDto> getChangedExpiredInMonthLast12Monthes();

}
