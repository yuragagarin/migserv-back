package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.domain.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor {
    Optional<Role> findByName(String name);

    List<Role> getAllById(Integer roleId);

    @Modifying
    @Query(value = "delete from user_roles where user_id =:userId", nativeQuery = true)
    @Transactional
    void deleteRolesByUserId(Long userId);


}
