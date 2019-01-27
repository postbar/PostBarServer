package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.Comment;
import com.sfs.pbserver.entity.PostPicture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostPictureRepo extends JpaRepository<PostPicture,Integer> {

    @Query(value = "FROM PostPicture a WHERE a.post.id = ?1 AND a.ordre = ?2 AND a.valid = 1")
    PostPicture findPostPictureByPostIdAndOrderId(Integer postId,Integer orderId);

}
