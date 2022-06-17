package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.domain.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor {

    @Query(value = "select p.code from users u\n" +
            "join user_roles ur on ur.user_id = u.id\n" +
            "join roles r on r.id = ur.role_id\n" +
            "left join role_permissions rp on rp.role_id = r.id\n" +
            "left join permissions p on p.id = rp.permission_id\n" +
            "where u.id = :userId", nativeQuery = true)
    List<String> getByUserId(@Param("userId") long id);

    @Query(value = "select p from Permission p join p.roles r where r.id=:roleId")
    List<Permission> getByRoleId(@Param("roleId") int id);

    List<Permission> findByIdIn(List<Integer> id);


    @Modifying
    @Query(value = "delete from role_permissions where role_id =:roleId", nativeQuery = true)
    @Transactional
    void deletePermissionsByRoleId(int roleId);

}
