package com.sfs.pbserver.repo;

import com.sfs.pbserver.entity.Bar;
import com.sfs.pbserver.entity.BarFocus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BarRepo extends JpaRepository<Bar,Integer> {

    @Query(value = "FROM Bar a WHERE a.id = ?1 AND a.valid = 1")
    Bar findBarById(Integer id);

    @Query(value = "FROM Bar a WHERE a.name = ?1")
    Bar findBarByName(String name);

    //简单模糊搜索
    @Query(value = "FROM Bar a WHERE (0 < LOCATE(?1, a.name)) AND a.valid = 1")
    Page<Bar> findBarsByName(String name,Pageable pageable);
}
