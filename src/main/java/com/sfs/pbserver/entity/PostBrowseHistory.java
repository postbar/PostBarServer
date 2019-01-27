package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class PostBrowseHistory extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Post post;

    @OneToOne
    private User user;
}
