package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.Consumer;
import ru.migplus.site.domain.Status;
import ru.migplus.site.dto.consumer.ConsumerViewDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long>, JpaSpecificationExecutor {

    Optional<Consumer> findFirstByOrderByIdDesc();

    Optional<Consumer> findFirstByIdAndStatus(long id, Status status);

    @Query(value = "select exists (\n" +
            "select from pg_tables\n" +
            "WHERE tablename  = :tableName)", nativeQuery = true)
    Boolean tableExists(String tableName);

    @Query(value = "select id,name from consumers\n" +
            "where exists (select from pg_tables\n" +
            "where tablename = 'rd_consumer_'|| id )\n" +
            "and status = 'ACTIVE'", nativeQuery = true)
    List<ConsumerViewDto> findAllWithParams();

}
