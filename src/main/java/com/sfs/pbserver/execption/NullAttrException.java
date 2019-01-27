package com.sfs.pbserver.execption;


import com.sfs.pbserver.base.ResultEnum;

/**
 * 空属性异常
 */
public class NullAttrException extends RuntimeException {
    private int code = ResultEnum.NULL_ATTR.getCode();
    private static String msg = ResultEnum.NULL_ATTR.getMsg();

    public NullAttrException() {
        super(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
