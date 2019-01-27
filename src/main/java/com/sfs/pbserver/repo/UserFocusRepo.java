package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.UserFocus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserFocusRepo extends JpaRepository<UserFocus,Integer> {

    @Query(value = "FROM UserFocus a WHERE a.userFrom.email = ?1 AND a.valid = 1")
    Page<UserFocus> findUserFocusByEmail(String email, Pageable pageable);

    @Query(value = "FROM UserFocus a WHERE a.userFrom.id = ?1  AND a.userTo.id = ?2 AND a.valid = 1")
    UserFocus findUserFocusByIdId(Integer userFromId,Integer userToId);

    @Query(value = "FROM UserFocus a WHERE a.userFrom.id = ?1  AND a.userTo.id = ?2")
    UserFocus findUserFocusByIdIdNoValidation(Integer userFromId,Integer userToId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserFocus a SET a.valid = ?3 WHERE  a.userFrom.id = ?1  AND a.userTo.id = ?2")
    int updateUserFocusByIdId(Integer userFromId,Integer userToId,Integer valid);

}
