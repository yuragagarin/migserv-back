package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.EquipOper;

@Repository
public interface EquipOperRepository extends JpaRepository<EquipOper, Long> {
}
