package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.SafeEvent;
import ru.migplus.site.dto.rdParam.SafeEventTimelineDto;
import ru.migplus.site.dto.rdParam.SafeEventViewDto;
import ru.migplus.site.dto.rdParam.SafeParamStateDto;

import java.util.List;

@Repository
public interface SafeEventRepository extends JpaRepository<SafeEvent, Long> {

    @Query(value = "select se.id, rp.name, se.st_date stDate, se.fn_date fnDate,replace(replace(replace(to_char(AGE(se.fn_date,se.st_date),'DDд HH24ч MIм SSс'),'00д',''),'00ч',''),'00м','') duration\n" +
            "from safe_events se\n" +
            "left join rd_params rp on rp.id = se.rd_param_id\n" +
            "left join rd_job_confs rjc on rjc.id = rp.rd_job_conf_id\n" +
            "where rjc.consumer_id = :consumerId and se.name='ERR'\n" +
            "and datediff('minute',se.st_date::::timestamp,se.fn_date::::timestamp) > 5\n" +
            "order by se.st_date desc", nativeQuery = true)
    List<SafeEventViewDto> findByConsumerId(@Param("consumerId") long consumerId);

    @Query(value = "select se.id, rp.name, rc.name consumerName, case when se.name = 'OK' then 1 else 0 end state from safe_events se\n" +
            "join (select MAX(id) id from safe_events group by rd_param_id) max_params on max_params.id = se.id\n" +
            "left join rd_params rp on rp.id = se.rd_param_id\n" +
            "join rd_job_confs rjc on rjc.id = rp.rd_job_conf_id\n" +
            "join consumers rc on rc.id = rjc.consumer_id\n" +
            "where (:consumerId = 0 OR rc.id = :consumerId)\n" +
            "and (:userId = 0 OR (rc.serviceman_id = :userId OR rc.serviceman_gas_id = :userId))\n" +
            "order by rp.name", nativeQuery = true)
    List<SafeParamStateDto> getSafeParamStates(@Param("consumerId") long consumerId, @Param("userId") long userId);

    @Query(value = "select count(*) forRepairCnt from safe_events where fn_date is null and name='ERR'", nativeQuery = true)
    SafeEventViewDto findForRepairCnt();

    @Query(value = "select rp.name as paramName, case when se.name = 'ERR' and datediff('minute',se.st_date::::timestamp,se.fn_date::::timestamp) < 5 then 'OK' else se.name  end eventName, se.st_date as stDate, COALESCE(se.fn_date, now()) as fnDate\n" +
            "from safe_events se join rd_params rp on rp.id = se.rd_param_id\n" +
            "join rd_job_confs rjc on rjc.id = rp.rd_job_conf_id\n" +
            "where rjc.consumer_id = :consumerId \n" +
            "order by rp.name", nativeQuery = true)
    List<SafeEventTimelineDto> getTimelines(@Param("consumerId") long consumerId);

}
