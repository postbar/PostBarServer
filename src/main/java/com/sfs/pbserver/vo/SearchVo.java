package com.sfs.pbserver.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchVo {
    String content;

    public SearchVo() {
    }

    public SearchVo(String content) {
        this.content = content;
    }
}
