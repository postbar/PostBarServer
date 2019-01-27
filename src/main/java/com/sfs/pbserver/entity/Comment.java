package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Comment extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Post post;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String content;  //帖子内容(分段)

    @Column(nullable = false)
    private Integer ordre=0;
}
