package com.bless.fresco.request.supplier;

import android.graphics.drawable.Animatable;

import com.bless.fresco.logger.FrescoLogger;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      获取图片控制器的监听器的供应者
 *
 * </pre>
 */
public class FetchImageControllerListenerSupplier extends BaseControllerListener<ImageInfo> {
    private com.bless.fresco.request.callback.FetchCallback<ImageInfo> FetchCallback;

    private FetchImageControllerListenerSupplier(com.bless.fresco.request.callback.FetchCallback<ImageInfo> callback) {
        this.FetchCallback = callback;
    }

    /**
     * New instance fetch image controller listener supplier.
     *
     * @param callback the callback
     * @return the fetch image controller listener supplier
     */
    public static FetchImageControllerListenerSupplier newInstance(com.bless.fresco.request.callback.FetchCallback<ImageInfo> callback) {
        return new FetchImageControllerListenerSupplier(callback);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        super.onFailure(id, throwable);
        if(FetchCallback ==null)
            return ;
        FrescoLogger.getLogger().log(throwable.getLocalizedMessage());
        FetchCallback.onFailure(throwable);
    }

    @Override
    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        if(FetchCallback ==null || imageInfo == null)
            return;
        FrescoLogger.getLogger().log("Bitmap:[height="+imageInfo.getHeight()+",width="+imageInfo.getWidth()+"]");
        FetchCallback.onSuccess(imageInfo);
    }
}
