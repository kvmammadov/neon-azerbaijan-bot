package com.kv.ms.bot.neonazerbaijan.dao.repository;

import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<PostEntity, Long> {

//    Boolean findByPostId(String postId);

    Boolean existsByPostId(String postId);

    PostEntity findByPostId(String postId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM POSTS p WHERE p.is_published = FALSE ORDER BY p.id DESC LIMIT 1")
    PostEntity findLastPostIdWherePublishedIsFalse();
}