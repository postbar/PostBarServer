package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class User extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String avatar;  //头像

    @Column(length = 20,nullable = false)
    private String name;

    @Column(length = 255,nullable = false)
    private String salt;

    @Column(length = 255,nullable = false)
    private String password;

    @Column(length = 255,nullable = false)
    private String email;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }
}
