package com.bless.fresco.request.callback;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      获取图片的回调
 *
 * </pre>
 */
public interface FetchCallback<T> {

    /**
     * On success.
     *
     * @param result the result
     */
    void onSuccess(T result);

    /**
     * On failure.
     *
     * @param throwable the throwable
     */
    void onFailure(Throwable throwable);
}
