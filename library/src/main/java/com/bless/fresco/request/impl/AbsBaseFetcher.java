package com.bless.fresco.request.impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.bless.fresco.request.Fetcher;
import com.bless.fresco.request.callback.FetchCallback;
import com.bless.fresco.request.callback.LoadCallback;
import com.bless.fresco.util.ParamCheckUtil;
import com.bless.fresco.util.UriUtil;
import com.facebook.imagepipeline.image.ImageInfo;


/**
 * The type Abs base fetcher.
 *
 * @param <T> the type parameter
 */
public abstract class AbsBaseFetcher<T> implements Fetcher<T> {
    @Override
    public void fetch(T view, Uri uri) {
        ParamCheckUtil.checkNotNull(view);
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(T view, String uriPath) {
        fetch(view, UriUtil.parseUri(uriPath));
    }

    @Override
    public void fetch(T view, Uri uri, FetchCallback<ImageInfo> callback) {
        ParamCheckUtil.checkNotNull(view);
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(T view, String uriPath, FetchCallback<ImageInfo> callback) {
        fetch(view,UriUtil.parseUri(uriPath),callback);
    }

    @Override
    public void fetch(T view, @DrawableRes int resId) {
        fetch(view,UriUtil.parseUriFromResId(resId));
    }

    @Override
    public void fetch(T view, @DrawableRes int resId, FetchCallback<ImageInfo> callback) {
        fetch(view,UriUtil.parseUriFromResId(resId),callback);
    }

    @Override
    public void fetch(Uri uri, LoadCallback<Bitmap> callback) {
        ParamCheckUtil.checkUriIsLegal(uri);
        beforeExecute(uri);
    }

    @Override
    public void fetch(String uriPath, LoadCallback<Bitmap> callback) {
        fetch(UriUtil.parseUri(uriPath), callback);
    }

    /**
     * Before execute object.
     *
     * @param uri the uri
     * @return the object
     */
    protected abstract Object beforeExecute(Uri uri);

}
