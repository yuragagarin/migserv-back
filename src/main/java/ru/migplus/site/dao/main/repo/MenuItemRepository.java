package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.MenuItem;
import ru.migplus.site.dto.menu.MenuItemDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query(value = "select mi from MenuItem mi")
    List<MenuItemDto> getAll();

    List<MenuItem> findAllByOrderByName();

    @Query(value = "select mi from MenuItem mi join mi.permission p join p.roles r join r.users u where u.id = :userId")
    List<MenuItem> getByUserId(@Param("userId") long userId);

    @Query(value = "select mi from MenuItem mi join mi.menuItem p where mi.name = :name and p.id = :parentId")
    Optional<MenuItem> findFirstByNameAndParentId(String name, long parentId);

}
