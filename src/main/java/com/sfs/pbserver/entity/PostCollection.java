package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class PostCollection {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Post post;

    @OneToOne
    private User user;
}
