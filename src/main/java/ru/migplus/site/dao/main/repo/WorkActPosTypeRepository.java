package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.WorkActPosType;

import java.util.Optional;

@Repository
public interface WorkActPosTypeRepository extends JpaRepository<WorkActPosType, Short> {
    Optional<WorkActPosType> findFirstById(Short id);
}
