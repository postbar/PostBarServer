package com.sfs.pbserver.entity;


import javafx.geometry.Pos;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class PostPicture extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Post post;

    @Column(nullable = false)
    private Integer ordre;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content; // 帖子内容

    public PostPicture() {

    }

    public PostPicture(Post post, Integer ordre, String content) {
        this.post = post;
        this.ordre = ordre;
        this.content = content;
    }
}
