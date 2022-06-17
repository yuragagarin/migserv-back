package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.EquipServ;
import ru.migplus.site.dto.equipServ.EquipServHistViewDto;
import ru.migplus.site.dto.equipServ.EquipServStatusesDto;
import ru.migplus.site.dto.equipServ.EquipServViewDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipServRepository extends JpaRepository<EquipServ, Long> {

    Optional<EquipServ> findFirstByDateFnIsNullAndEquipOperId(Long id);

    @Query(value = "select eqo.id equipOperId, eq.code, eq.name, eq.number, eqo.name operName, eqs.date_st date, eqs.next_date nextDate, \n" +
            "(case when date_part('day', eqs.next_date - current_timestamp) >= 59 then 'success' \n" +
            "when date_part('day', eqs.next_date - current_timestamp) < 59 and date_part('day', eqs.next_date - current_timestamp) > 29 then 'risk' \n" +
            "when date_part('day', eqs.next_date - current_timestamp) <= 29 then 'danger'\n" +
            "else 'zero' end) status\n" +
            "from equips eq \n" +
            "join equip_opers eqo on eq.id = eqo.equip_id \n" +
            "left join equip_servs eqs on eqs.equip_oper_id = eqo.id\n" +
            "where (:consumerId = 0 OR eq.consumer_id = :consumerId) \n" +
            "and (cast(:code as varchar) is null or eq.code = cast(:code as varchar))\n" +
            "and (cast(:name as varchar) is null or eq.name = cast(:name as varchar))\n" +
            "and (cast(:number as varchar) is null or eq.number = cast(:number as varchar))\n" +
            "and (cast(:operName as varchar) is null or eqo.name = cast(:operName as varchar))\n" +
            "and eq.status='ACTIVE' \n" +
            "and  eqs.date_fn is null\n" +
            "order by status,eqs.next_date, eq.name, eq.number", nativeQuery = true)
    List<EquipServViewDto> getServs(@Param("consumerId") long consumerId, @Param("code") String code, @Param("name") String name,
                                    @Param("number") String number, @Param("operName") String operName);

    @Query(value = "select\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) >= 59) successCnt,\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) < 59 and date_part('day', next_date - current_timestamp) > 29) warningCnt,\n" +
            "(select count(*) from equip_servs where date_fn is null and date_part('day', next_date - current_timestamp) <= 29) dangerCnt", nativeQuery = true)
    EquipServStatusesDto getStatuses();

    @Query(value = "select eq.id, eq.code, eq.name, eq.number equipNum, eqo.name operName, eqs.date_st dateSt, eqs.date_fn dateFn, eqs.next_date nextDate, eqs.id equipServId\n" +
            ",(case when eqs.next_date > eqs.date_fn then 'success'\n" +
            "else 'danger' end) status\n" +
            "from equips eq \n" +
            "join equip_opers eqo on eq.id = eqo.equip_id\n" +
            "left join equip_servs eqs on eqs.equip_oper_id = eqo.id\n" +
            "where eq.consumer_id=:consumerId\n" +
            "and eq.status='ACTIVE' \n" +
            "and  eqs.date_fn is not null\n" +
            "order by eqs.date_fn desc", nativeQuery = true)
    List<EquipServHistViewDto> getServHists(@Param("consumerId") long consumerId);
}
