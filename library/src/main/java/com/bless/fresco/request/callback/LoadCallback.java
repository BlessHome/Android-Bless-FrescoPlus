package com.bless.fresco.request.callback;

import android.net.Uri;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      加载图片的回调
 *
 * </pre>
 */
public interface LoadCallback<T> {

    /**
     * On success.
     *
     * @param uri    the uri
     * @param result the result
     */
    void onSuccess(Uri uri, T result);

    /**
     * On failure.
     *
     * @param uri       the uri
     * @param throwable the throwable
     */
    void onFailure(Uri uri, Throwable throwable);

    /**
     * On cancel.
     *
     * @param uri the uri
     */
    void onCancel(Uri uri);
}
