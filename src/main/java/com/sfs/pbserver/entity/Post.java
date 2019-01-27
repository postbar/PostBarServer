package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Post extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Bar bar;

    @OneToOne
    private User user;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String content;  //帖子内容(分段)

    @Column(nullable = false)
    private Integer ordre=0;

    public Post() {

    }

    public Post(Integer id) {
        this.id = id;
    }
}
