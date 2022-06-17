package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.EnvUnit;
import ru.migplus.site.domain.EnvUnitOper;
import ru.migplus.site.domain.EnvUnitOperName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnvUnitOperRepository extends JpaRepository<EnvUnitOper, Long>, JpaSpecificationExecutor {

    Optional<EnvUnitOper> findFirstByEnvUnitIdAndDateFnNotNull(Long unitId);

    Optional<EnvUnitOper> findFirstByEnvUnitIdAndNameEquals(Long unitId, EnvUnitOperName name);

    Optional<EnvUnitOper> findFirstByEnvUnitAndDateStGreaterThan(EnvUnit envUnit, LocalDateTime date);

    Optional<EnvUnitOper> findFirstByEnvUnitIdAndNameAndDateFnIsNull(Long unitId, EnvUnitOperName name);

    Optional<EnvUnitOper> findFirstByEnvUnitAndDateStLessThanOrderByDateStDesc(EnvUnit envUnit, LocalDateTime date);

    List<EnvUnitOper> findByEnvUnitAndNameEquals(EnvUnit envUnit, EnvUnitOperName name);

    Optional<EnvUnitOper> findFirstByEnvUnitAndNameEqualsAndDateFnNotNullAndDateStGreaterThan(EnvUnit envUnit, EnvUnitOperName name, LocalDateTime date);
}

