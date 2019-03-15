package com.xyj.exception;

public class AdminException extends BusinessException {

    protected AdminException(BusinessException parent) {
        super(parent);
    }

    public static AdminException create(ExceptionCause exceptionCause, Object... args) {
        BusinessException businessException = BusinessException.create(exceptionCause, args);
        return new AdminException(businessException);
    }
}
