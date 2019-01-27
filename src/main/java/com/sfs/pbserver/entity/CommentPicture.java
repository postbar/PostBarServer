package com.sfs.pbserver.entity;

import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class CommentPicture extends AbstracEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Comment comment;

    @Column(nullable = false)
    private Integer ordre; // 图片标号

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content; //base64 图片列表

    public CommentPicture() {

    }

    public CommentPicture(Comment comment, Integer ordre, String content) {
        this.comment = comment;
        this.ordre = ordre;
        this.content = content;
    }
}
