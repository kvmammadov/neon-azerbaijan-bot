package com.kv.ms.bot.neonazerbaijan.dao.repository;

import com.kv.ms.bot.neonazerbaijan.dao.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT p.post_id FROM POSTS p WHERE p.published = false and ROW = 1 order by p.post_id desc")
    PostEntity findLastPostIdWherePublishedIsFalse();
}