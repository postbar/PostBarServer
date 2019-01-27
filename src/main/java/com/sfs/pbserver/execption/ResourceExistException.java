package com.sfs.pbserver.execption;

import com.sfs.pbserver.base.ResultEnum;

/**
 * 未知资源(restful接口)
 */
public class ResourceExistException extends RuntimeException {
    private int code = ResultEnum.RESOURCE_EXIST.getCode();
    private static String msg = ResultEnum.RESOURCE_EXIST.getMsg();

    public ResourceExistException() {
        super(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
