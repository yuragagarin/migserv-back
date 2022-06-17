package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.ParamType;


@Repository
public interface ParamTypeRepository extends JpaRepository<ParamType, Long> {
}
