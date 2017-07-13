package com.bless.fresco.exception;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco 运行时异常
 *
 * </pre>
 */
public class FRuntimeException extends RuntimeException {
    /**
     * Instantiates a new F runtime exception.
     *
     * @param detailMessage the detail message
     */
    public FRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Instantiates a new F runtime exception.
     *
     * @param detailMessage the detail message
     * @param throwable     the throwable
     */
    public FRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        initCause(throwable);
    }
}
