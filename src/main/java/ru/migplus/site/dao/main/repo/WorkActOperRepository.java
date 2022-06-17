package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.User;
import ru.migplus.site.domain.WorkAct;
import ru.migplus.site.domain.WorkActOper;

import java.util.List;

@Repository
public interface WorkActOperRepository extends JpaRepository<WorkActOper, Long> {

    WorkActOper findFirstByWorkActAndUserAndName(WorkAct workAct, User user, String name);

    @Query("select wao from WorkActOper wao join wao.workAct wa join wao.user u join u.roles r where r.name in :roleNames and wa.id=:workActId\n" +
            "and wao.name=:operName")
    WorkActOper findByWorkActIdAndRoleName(@Param("workActId") long workActId, @Param("roleNames") List<String> roleNames, @Param("operName") String operName);

}
