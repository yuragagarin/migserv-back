package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.EnvUnitOperName;

import java.util.Optional;

@Repository
public interface EnvUnitOperNameRepository extends JpaRepository<EnvUnitOperName, Short>, JpaSpecificationExecutor {

    Optional<EnvUnitOperName> findFirstByName(String name);

}

