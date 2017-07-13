package com.bless.fresco.request.callback;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      缓存的回调
 *
 * </pre>
 */
public interface CacheCallback<T> {
    /**
     * @param result The result of the image was found in disk cache.
     */
    void onResult(T result);
}
