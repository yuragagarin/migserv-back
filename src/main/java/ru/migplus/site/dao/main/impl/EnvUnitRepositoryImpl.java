package ru.migplus.site.dao.main.impl;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.migplus.site.dao.main.repo.filter.EnvUnitFilter;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.domain.QEnvUnit;
import ru.migplus.site.domain.QEnvUnitOper;
import ru.migplus.site.dto.envUnit.EnvUnitDto;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EnvUnitRepositoryImpl implements EnvUnitFilter {

    private final EntityManager em;

    @Override
    public List<EnvUnit> getFilter(EnvUnitDto filter) {

        QEnvUnit envUnit = QEnvUnit.envUnit;
        QEnvUnitOper envUnitOper = QEnvUnitOper.envUnitOper;
        JPAQuery query = new JPAQuery(em);

        query.from(envUnit).innerJoin(envUnit.envUnitOpers, envUnitOper)
                .where(envUnitOper.dateFn.isNull());
        if (filter.getEnvUnitOperName() != null)
            query.where(envUnitOper.name.name.eq(filter.getEnvUnitOperName()));
        if (filter.getEnvUnitType() != null)
            query.where(envUnit.envUnitType.name.eq(filter.getEnvUnitType()));

        return query.fetch();
    }
}
