package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.migplus.site.domain.Status;
import ru.migplus.site.domain.User;
import ru.migplus.site.dto.user.UserViewDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    @Query(value = "select u.id,u.username,fio(u.surname,u.name,u.patronymic) fio,u.phone_number phoneNumber,u.email,up.name post, c.name consumer,u.updated,fio(upar.surname,upar.name,upar.patronymic) masterFio,r.name roleName from users u\n" + //fio(u.surname,u.name,u.patronymic) fio(upar.surname,upar.name,upar.patronymic)
            "join user_posts up on up.id = u.user_post_id\n" +
            "left join consumers c on c.id = u.consumer_id\n" +
            "left join users upar on upar.id = u.user_id\n" +
            "left join user_roles ur on ur.user_id = u.id\n" +
            "left join roles r on r.id = ur.role_id\n" +
            "where u.status = 'ACTIVE'\n" +
            "order by u.surname,u.name,u.patronymic,u.username", nativeQuery = true)
    List<UserViewDto> getAll();

    @Query("select u from User u where u.username = ?1 and u.status='ACTIVE'")
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findFirstByUsernameAndStatus(String username, Status status);

    Optional<User> findFirstByIdAndStatus(long id, Status status);

}
