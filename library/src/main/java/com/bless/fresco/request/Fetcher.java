package com.bless.fresco.request;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.bless.fresco.request.callback.FetchCallback;
import com.bless.fresco.request.callback.LoadCallback;
import com.facebook.imagepipeline.image.ImageInfo;


/**
 * The interface Fetcher.
 *
 * @param <T> the type parameter
 */
public interface Fetcher<T> {

    /**
     * Fetch.
     *
     * @param view the view
     * @param uri  the uri
     */
    void fetch(T view, Uri uri);

    /**
     * Fetch.
     *
     * @param view    the view
     * @param uriPath the uri path
     */
    void fetch(T view, String uriPath);

    /**
     * Fetch.
     *
     * @param view  the view
     * @param resId the res id
     */
    void fetch(T view, @DrawableRes int resId);

    /**
     * Fetch.
     *
     * @param view     the view
     * @param resId    the res id
     * @param callback the callback
     */
    void fetch(T view, @DrawableRes int resId, FetchCallback<ImageInfo> callback);

    /**
     * Fetch.
     *
     * @param view     the view
     * @param uri      the uri
     * @param callback the callback
     */
    void fetch(T view, Uri uri, FetchCallback<ImageInfo> callback);

    /**
     * Fetch.
     *
     * @param view     the view
     * @param uriPath  the uri path
     * @param callback the callback
     */
    void fetch(T view, String uriPath, FetchCallback<ImageInfo> callback);

    /**
     * Fetch.
     *
     * @param uri      the uri
     * @param callback the callback
     */
    void fetch(Uri uri, LoadCallback<Bitmap> callback);

    /**
     * Fetch.
     *
     * @param uriPath  the uri path
     * @param callback the callback
     */
    void fetch(String uriPath, LoadCallback<Bitmap> callback);
}
