package com.xyj.exception;


import com.xyj.builder.ResultEntity;

public interface ExceptionCause<T extends Exception> {

    /**
     * 创建异常
     * @return
     */
    T exception(Object... args);

    ResultEntity result();
}
