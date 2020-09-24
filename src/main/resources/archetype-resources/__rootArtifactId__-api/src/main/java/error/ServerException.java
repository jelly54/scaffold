package com.jelly.boot.error;

import java.text.MessageFormat;

/**
 * 服务异常
 *
 * @author guodongzhang
 */
public class ServerException extends RuntimeException {

    private static final long serialVersionUID = 8224074865591050440L;
    private Integer errno;

    public ServerException() {
        super(ErrorStatus.INTERNAL_EXCEPTION.message());
        this.errno = ErrorStatus.INTERNAL_EXCEPTION.code();
    }

    public ServerException(ErrorStatus errorEnum) {
        super(errorEnum != null ? errorEnum.message() : ErrorStatus.INTERNAL_EXCEPTION.message());
        this.errno = errorEnum != null ? errorEnum.code() : ErrorStatus.INTERNAL_EXCEPTION.code();
    }

    public ServerException(ErrorStatus errorEnum, String... message) {
        super((errorEnum != null ? MessageFormat.format(errorEnum.message(), message) : MessageFormat.format("{0}", message)));
        this.errno = errorEnum != null ? errorEnum.code() : ErrorStatus.INTERNAL_EXCEPTION.code();
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

}