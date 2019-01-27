package com.sfs.pbserver.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    String content;
    Integer ordre;
    List<String> images;

    public CommentVo() {
    }

    public CommentVo(String content, Integer ordre, List<String> images) {
        this.content = content;
        this.ordre = ordre;
        this.images = images;
    }
}
