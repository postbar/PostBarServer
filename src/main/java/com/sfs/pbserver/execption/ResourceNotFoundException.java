package com.sfs.pbserver.execption;


import com.sfs.pbserver.base.ResultEnum;

/**
 * 未知资源(restful接口)
 */
public class ResourceNotFoundException extends RuntimeException {
    private int code = ResultEnum.RESOURCE_NOT_FOUND.getCode();
    private static String msg = ResultEnum.RESOURCE_NOT_FOUND.getMsg();

    public ResourceNotFoundException() {
        super(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
