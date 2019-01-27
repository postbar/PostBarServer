package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.BarFocus;
import com.sfs.pbserver.entity.UserFocus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface BarFocusRepo extends JpaRepository<BarFocus,Integer> {

    @Query(value = "FROM BarFocus a WHERE a.user.email = ?1 AND a.valid = 1")
    Page<BarFocus> findBarFocusByEmail(String email, Pageable pageable);

    @Query(value = "FROM BarFocus a WHERE a.user.id = ?1  AND a.bar.id = ?2 AND a.valid = 1")
    BarFocus findBarFocusByIdId(Integer userId,Integer barId);

    @Query(value = "FROM BarFocus a WHERE a.user.id = ?1  AND a.bar.id = ?2")
    BarFocus findBarFocusByIdIdNoValiddation(Integer userId,Integer barId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE BarFocus a SET a.valid = ?3 WHERE  a.user.id = ?1  AND a.bar.id = ?2")
    int updateBarFocusByIdId(Integer userId,Integer barId,Integer valid);

    //简单模糊搜索
    @Query(value = "FROM BarFocus a WHERE (0 < LOCATE(?1, a.bar.name)) AND a.valid = 1")
    Page<BarFocus> findBarFocusesByName(String name,Pageable pageable);
}
