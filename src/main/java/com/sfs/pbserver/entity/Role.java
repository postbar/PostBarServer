package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Role extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
