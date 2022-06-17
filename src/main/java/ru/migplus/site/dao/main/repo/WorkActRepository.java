package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.WorkAct;
import ru.migplus.site.dto.workAct.WorkActViewDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkActRepository extends JpaRepository<WorkAct, Long>, JpaSpecificationExecutor {
    Optional<WorkAct> getFirstByOrderByIdDesc();

    @Query(value = "select wa.id,wa.num,opers_cr.fio createUser, opers_cr.date_st createDate\n" +
            ", opers_comf.fio confirmUser, opers_comf.date_st confirmDate \n" +
            ", case when opers_comf.date_st is not null then '1' else '0' end status\n" +
            "from work_acts wa\n" +
            "left join lateral(select date_st,fio(u.surname,u.name,u.patronymic) fio  \n" +
            "from work_act_opers wao\n" +
            "join users u on u.id = wao.user_id\n" +
            "where work_act_id = wa.id and wao.name = 'Создание' \n" +
            "order by wao.id desc limit 1) opers_cr on true\n" +
            "left join lateral(select wao.date_st, r.name,fio(u.surname,u.name,u.patronymic) fio \n" +
            "from work_act_opers wao\n" +
            "join users u on u.id = wao.user_id\n" +
            "join user_roles ur on ur.user_id = u.id\n" +
            "join roles r on r.id = ur.role_id\n" +
            "where work_act_id = wa.id and wao.name = 'Подтверждение' and r.name = 'Бухгалтер'\n" +
            "order by wa.id desc limit 1) opers_comf on true"
            , nativeQuery = true)
    List<WorkActViewDto> getAllAccauntant();

    @Query(value = "select wa.id,wa.num\n" +
            ", opers_cr.fio createUser\n" +
            ", opers_cr.date_st createDate\n" +
            ", opers_comf_acc.fio confirmAccountantUser \n" +
            ", opers_comf_acc.date_st confirmAccountantDate \n" +
            ", opers_comf_head.fio confirmUser \n" +
            ", opers_comf_head.date_st confirmDate \n" +
            ", case when opers_comf_head.date_st is not null then '1' else '0' end status\n" +
            "from work_acts wa\n" +
            "left join lateral(select date_st,fio(u.surname,u.name,u.patronymic) fio  \n" +
            "from work_act_opers wao\n" +
            "join users u on u.id = wao.user_id\n" +
            "where work_act_id = wa.id and wao.name = 'Создание' \n" +
            "order by wao.id desc limit 1) opers_cr on true\n" +
            "\n" +
            "left join lateral(select wao.date_st, r.name,fio(u.surname,u.name,u.patronymic) fio \n" +
            "from work_act_opers wao\n" +
            "join users u on u.id = wao.user_id\n" +
            "join user_roles ur on ur.user_id = u.id\n" +
            "join roles r on r.id = ur.role_id\n" +
            "where work_act_id = wa.id and wao.name = 'Подтверждение' and r.name = 'Бухгалтер'\n" +
            "order by wa.id desc limit 1) opers_comf_acc on true\n" +
            "\n" +
            "left join lateral(select wao.date_st, r.name,fio(u.surname,u.name,u.patronymic) fio \n" +
            "from work_act_opers wao\n" +
            "join users u on u.id = wao.user_id\n" +
            "join user_roles ur on ur.user_id = u.id\n" +
            "join roles r on r.id = ur.role_id\n" +
            "where work_act_id = wa.id and wao.name = 'Подтверждение' and r.name in('Начальник сервисной службы','Начальник газовой службы')\n" +
            "order by wa.id desc limit 1) opers_comf_head on true"
            , nativeQuery = true)
    List<WorkActViewDto> getAllHeadman();
}
