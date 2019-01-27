package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Reply extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Comment comment;

    @OneToOne
    private User user;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String content;  //回复内容
}
