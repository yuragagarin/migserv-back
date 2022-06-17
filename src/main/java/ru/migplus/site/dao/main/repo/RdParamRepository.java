package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.RdParam;

@Repository
public interface RdParamRepository extends JpaRepository<RdParam, Long> {

    @Query(value = "select exists (\n" +
            "select from pg_tables\n" +
            "WHERE tablename  = :tableName)", nativeQuery = true)
    Boolean tableExists(String tableName);
}
