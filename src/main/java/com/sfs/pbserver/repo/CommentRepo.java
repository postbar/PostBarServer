package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.Comment;
import com.sfs.pbserver.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommentRepo extends JpaRepository<Comment,Integer> {

    @Query(value = "FROM Comment a WHERE a.post.id = ?1 AND a.valid = 1")
    Page<Comment> findCommentByPostId(Integer postId,Pageable pageable);

}
