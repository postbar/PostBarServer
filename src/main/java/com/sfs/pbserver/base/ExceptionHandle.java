package com.sfs.pbserver.base;

import com.sfs.pbserver.execption.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕获类
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultBean handle(BaseException e) {

        System.out.println("ResultHandler.error(e);"+e);

        return ResultHandler.error(e);
    }

}
