package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class CommentLike extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Comment comment;

    @OneToOne
    private User user;
}
