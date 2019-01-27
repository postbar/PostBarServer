package com.sfs.pbserver.execption;

import com.sfs.pbserver.base.ResultEnum;

/**
 * Base异常
 */
public class BaseException extends RuntimeException{
    private int code = ResultEnum.FORBIDDEN_EXCEPTION.getCode();
    private static String msg = ResultEnum.FORBIDDEN_EXCEPTION.getMsg();

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code =  resultEnum.getCode();
        msg = resultEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
