package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.User;
import ru.migplus.site.dto.user.ServAlarmDto;

@Repository
public interface StateRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    @Query(value = "select\n" +
            "(select count(1)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id join env_unit_oper_names euon on euon.id = euo.name_id and date_part('day', euo.expir_date - current_timestamp) >= 59 and (:userId = 0 OR euo.user_id = :userId) and euo.date_fn is null and euon.name='Установка' and (:userId = 0 OR eu.consumer_id in(select id from consumers where serviceman_id = :userId or serviceman_gas_id = :userId))) envSuccessCnt,\n" +
            "(select count(1)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id join env_unit_oper_names euon on euon.id = euo.name_id and date_part('day', euo.expir_date - current_timestamp) < 59 and date_part('day', euo.expir_date - current_timestamp) > 29 and (:userId = 0 OR euo.user_id = :userId) and euo.date_fn is null and euon.name='Установка' and (:userId = 0 OR eu.consumer_id in(select id from consumers where serviceman_id = :userId or serviceman_gas_id = :userId))) envChangeWarningCnt,\n" +
            "(select count(1)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id join env_unit_oper_names euon on euon.id = euo.name_id and date_part('day', euo.expir_date - current_timestamp) < 59 and date_part('day', euo.expir_date - current_timestamp) > 29 and (:userId = 0 OR euo.user_id = :userId) and euo.date_fn is null and euon.name='Поверка' and (:userId = 0 OR eu.consumer_id in(select id from consumers where serviceman_id = :userId or serviceman_gas_id = :userId))) envVerifyWarningCnt,\n" +
            "(select count(1)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id join env_unit_oper_names euon on euon.id = euo.name_id and date_part('day', euo.expir_date - current_timestamp) < 29 and (:userId = 0 OR euo.user_id = :userId) and euo.date_fn is null and euon.name='Установка' and (:userId = 0 OR eu.consumer_id in(select id from consumers where serviceman_id = :userId or serviceman_gas_id = :userId))) envChangeDangerCnt,\n" +
            "(select count(1)  from env_units eu join env_unit_opers euo on euo.env_unit_id = eu.id join env_unit_oper_names euon on euon.id = euo.name_id and date_part('day', euo.expir_date - current_timestamp) < 29 and (:userId = 0 OR euo.user_id = :userId) and euo.date_fn is null and euon.name='Поверка' and (:userId = 0 OR eu.consumer_id in(select id from consumers where serviceman_id = :userId or serviceman_gas_id = :userId))) envVerifyDangerCnt,\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) >= 59 and (:userId = 0 OR user_id = :userId)) equipSuccessCnt,\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) < 59 and date_part('day', next_date - current_timestamp) > 29 and (:userId = 0 OR user_id = :userId)) equipWarningCnt,\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) <= 29 and (:userId = 0 OR user_id = :userId)) equipDangerCnt,\n" +
            "(select count(*) cnt from safe_events se join (select MAX(id) id from safe_events group by rd_param_id) max_params on max_params.id = se.id left join rd_params rp on rp.id = se.rd_param_id where se.name <> 'OK') safeParamDangerCnt," +
            "(select count(*) from safe_events se\n" +
            "join (select MAX(id) id from safe_events group by rd_param_id) max_params on max_params.id = se.id\n" +
            "left join rd_params rp on rp.id = se.rd_param_id\n" +
            "join rd_job_confs rjc on rjc.id = rp.rd_job_conf_id\n" +
            "join consumers rc on rc.id = rjc.consumer_id\n" +
            "where 1=1 and (:userId = 0 OR (rc.serviceman_id = :userId or rc.serviceman_gas_id = :userId))) safeParamAllCnt"
            , nativeQuery = true)
    ServAlarmDto getServAlarmStatuses(long userId);
}
