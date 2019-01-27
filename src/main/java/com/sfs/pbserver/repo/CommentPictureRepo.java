package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.CommentPicture;
import com.sfs.pbserver.entity.PostPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommentPictureRepo extends JpaRepository<CommentPicture,Integer> {

    @Query(value = "FROM CommentPicture a WHERE a.comment.id = ?1 AND a.ordre = ?2 AND a.valid = 1")
    CommentPicture findCommentPictureByCommentIdAndOrderId(Integer commentId, Integer orderId);

}
