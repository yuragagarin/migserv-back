package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.WorkActPos;
import ru.migplus.site.dto.workAct.WorkActPosViewDto;

import java.util.List;

@Repository
public interface WorkActPosRepository extends JpaRepository<WorkActPos, Long>, JpaSpecificationExecutor {

    @Query(value = "select distinct t.id, t.name as type,t.descr, t.date from\n" +
            "(select wap.work_act_id, wap.id, wapt.name, euo.date_fn date, c.name || ', ' || eq.name || ', ' || eut.name || ', ' || eu.code as descr\n" +
            "from work_act_poss wap \n" +
            "join work_act_pos_types wapt on wapt.id = wap.type_id and wapt.code = 'change'\n" +
            "join env_unit_opers euo on euo.env_unit_id = wap.unit_id and euo.id = wap.oper_id\n" +
            "join env_units eu on eu.id = euo.env_unit_id\n" +
            "join consumers c on c.id = eu.consumer_id \n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id \n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "join users u on u.id = euo.user_id\n" +
            "where (:workActId = 0 OR wap.work_act_id = :workActId)\n" +
            "and (:userId = 0 OR u.id = :userId)\n" +
            "union\n" +
            "select wap.work_act_id, wap.id, wapt.name, euo.date_st date, c.name || ', ' || eq.name || ', ' || eut.name || ', ' || eu.code as descr\n" +
            "from work_act_poss wap \n" +
            "join work_act_pos_types wapt on wapt.id = wap.type_id and wapt.code = 'verify'\n" +
            "join env_unit_opers euo on euo.env_unit_id = wap.unit_id and euo.id = wap.oper_id\n" +
            "join env_units eu on eu.id = euo.env_unit_id\n" +
            "join consumers c on c.id = eu.consumer_id \n" +
            "join env_unit_types eut on eut.id = eu.env_unit_type_id \n" +
            "join equips eq on eq.id = eu.equip_id\n" +
            "join users u on u.id = euo.user_id\n" +
            "where (:workActId = 0 OR wap.work_act_id = :workActId)\n" +
            "and (:userId = 0 OR u.id = :userId)\n" +
            "union\n" +
            "select wap.work_act_id, wap.id, wapt.name, es.date_fn date, c.name || ', ' || eq.name || ', ' || eqo.name   as descr\n" +
            "from work_act_poss wap \n" +
            "join work_act_pos_types wapt on wapt.id = wap.type_id and wapt.code = 'equip'\n" +
            "join equip_servs es on  es.id = wap.oper_id\n" +
            "join equips eq on eq.id = wap.unit_id\n" +
            "join consumers c on c.id = eq.consumer_id \n" +
            "join equip_opers eqo on eqo.id = es.equip_oper_id\n" +
            "join users u on u.id = es.user_id\n" +
            "where (:workActId = 0 OR wap.work_act_id = :workActId)\n" +
            "and (:userId = 0 OR u.id = :userId)\n" +
            ") t\n" +

            "order by t.id", nativeQuery = true)
    List<WorkActPosViewDto> getAll(@Param("workActId") long workActId, @Param("userId") long userId);
}
