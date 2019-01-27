package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.Bar;
import com.sfs.pbserver.entity.Post;
import com.sfs.pbserver.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface PostRepo extends JpaRepository<Post,Integer> {

    @Query(value = "FROM Post a WHERE  a.user.id = ?1 AND a.valid = 1")
    Page<Post> findPostsByUserId(Integer userId,Pageable pageable);

    @Query(value = "FROM Post a WHERE a.valid = 1")
    Page<Post> findNewPosts(Pageable pageable);//贴吧的新帖

    @Query(value = "FROM Post a WHERE a.id = ?1 AND a.valid = 1")
    Post findPostByPostId(Integer postId);

    @Query(value = "FROM Post a WHERE a.id = ?1 AND a.valid = 0")
    Post findFreezePostByPostId(Integer postId);

    @Query(value = "FROM Post a WHERE a.bar.id = ?1 AND a.valid = 1")
    Page<Post> findPostsByBarId(Integer barId,Pageable pageable);

    @Query(value = "FROM Post a WHERE a.bar.id = ?1 AND a.valid = 0")
    Page<Post> findFreezePostsByBarId(Integer barId,Pageable pageable);

    @Query(value = "FROM Post a WHERE a.valid = 1")
    Page<Post> findPosts(Pageable pageable);

    @Query(value = "FROM Post a WHERE a.valid = 0")
    Page<Post> findFreezePosts(Pageable pageable);

    @Query(value = "FROM Post a WHERE a.id = ?1 AND a.valid = 1")
    Post findPostById(Integer id);

    @Query(value = "FROM Post a WHERE a.id = ?1 AND a.valid = 0")
    Post findFreezePostById(Integer id);


    //简单模糊搜索
    @Query(value = "FROM Post a WHERE (0 < LOCATE(?1, a.content)) AND a.valid = 1")
    Page<Post> findPostsByContent(String name, Pageable pageable);
}
