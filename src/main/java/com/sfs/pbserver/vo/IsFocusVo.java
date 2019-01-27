package com.sfs.pbserver.vo;


import lombok.Data;

@Data
public class IsFocusVo {
    Boolean focused=false;
    public IsFocusVo(){

    }

    public IsFocusVo(Boolean focused){
        this.focused = focused;
    }
}
