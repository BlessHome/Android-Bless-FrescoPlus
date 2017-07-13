package com.bless.fresco.exception;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco 非法参数异常
 *
 * </pre>
 */
public class FIllegalArgumentException extends IllegalArgumentException {
    /**
     * Instantiates a new F illegal argument exception.
     *
     * @param detailMessage the detail message
     */
    public FIllegalArgumentException(String detailMessage) {
        super(detailMessage);
    }
}
