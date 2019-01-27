package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRole,Integer> {
    @Query(value = "FROM UserRole ac WHERE ac.user.id = ?1 AND valid = 1")
    Page<UserRole> findUserRolesByAdminId(Integer userId, Pageable pageable);

    @Query(value = "FROM UserRole ac WHERE ac.user.id = ?1 AND valid = 1")
    List<UserRole> findUserRoleListByUserId(Integer userId);

    @Query(value = "FROM UserRole ac WHERE ac.user.email = ?1 AND valid = 1")
    List<UserRole> findUserRoleListByUserEmail(String email);

    @Query(value = "FROM UserRole ac WHERE ac.role.id = ?1 AND valid = 1")
    Page<UserRole> findUserRolesByRoleId(Integer roleId, Pageable pageable);

    @Query(value = "FROM UserRole ac WHERE ac.id = ?1 AND valid = 1")
    UserRole findUserRoleById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserRole ac SET ac.valid = 0 WHERE ac.id = ?1 AND ac.valid = 1")
    int deleteUserRoleById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserRole ac SET ac.valid = 0 WHERE ac.user.id = ?1 AND ac.valid = 1")
    int deleteUserRoleByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE UserRole ac SET ac.valid = 0 WHERE ac.role.id = ?1 AND ac.valid = 1")
    int deleteUserRoleByRoleId(Integer roleId);
}
