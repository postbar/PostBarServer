package com.sfs.pbserver.base;

import com.sfs.pbserver.execption.BaseException;

public class ResultHandler {
    /**
     * 成功时将object对象转化为ResultBean对象
     *
     * @param o
     * @return
     */
    public static ResultBean ok(Object o) {

        return new ResultBean(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), o);
    }


    /**
     * 成功时将object对象转化为ResultBean对象
     *
     * @param message
     * @return
     */
    public static ResultBean ok(ResultEnum message) {

        return new ResultBean(message.getCode(), message.getMsg(), message);
    }


    /**
     * 使用枚举列举错误类型
     *
     * @param e
     * @return
     */
    public static ResultBean error(BaseException e) {
        return new ResultBean(e.getCode(),e.getMessage(), null);
    }

    /**
     * 使用枚举列举错误类型
     *
     * @param error
     * @return
     */
    public static ResultBean error(ResultEnum error) {
        return new ResultBean(error.getCode(), error.getMsg(), null);
    }

    public static ResultBean error(String e) {
        return new ResultBean(ResultEnum.EXCEPTION.getCode(), e, null);
    }
}
