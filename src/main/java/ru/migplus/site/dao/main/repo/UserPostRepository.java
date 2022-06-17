package ru.migplus.site.dao.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.domain.UserPost;

import java.util.List;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Long> {

    @Modifying
    @Query(value = "alter sequence user_posts_id_seq restart with 1", nativeQuery = true)
    @Transactional
    void resetSeq();

    @Query(value = "select up from UserPost up  order by up.name")
        //where up.name <> 'Администратор'
    List<UserPost> findAll();
}
