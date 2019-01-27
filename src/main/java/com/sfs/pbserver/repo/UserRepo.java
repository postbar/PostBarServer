package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "FROM User a WHERE  a.email = ?1 AND a.valid = 1")
    User findUserByEmail(String email);

    @Query(value = "FROM User a WHERE a.valid = 0")
    Page<User> findUsers(Pageable pageable);

    @Query(value = "FROM User a WHERE a.valid = 0")
    Page<User> findFreezeUsers(Pageable pageable);

    @Query(value = "FROM User a WHERE a.id = ?1 AND a.valid = 1")
    User findUserById(Integer id);

    @Query(value = "FROM User a WHERE a.id = ?1 AND a.valid = 0")
    User findFreezeUserById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User a SET a.valid = 0 WHERE  a.id = ?1 AND a.valid = 1")
    int deleteUserById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User a SET a.valid = 0 WHERE  a.email = ?1 AND a.valid = 1")
    int deleteUserByEmail(String email);


    //简单模糊搜索
    @Query(value = "FROM User a WHERE (0 < LOCATE(?1, a.name)) AND a.valid = 1")
    Page<User> findUsersByName(String name,Pageable pageable);
}
