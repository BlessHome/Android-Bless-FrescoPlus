package com.bless.fresco.request.supplier;

import android.graphics.Bitmap;

import com.facebook.imagepipeline.request.BasePostprocessor;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      对从服务器下载，或者本地获取的图片做些修改的后处理器基类
 *
 * </pre>
 */
public abstract class BaseProcessorSupplier extends BasePostprocessor {
    @Override
    public String getName() {
        return "BaseProcessorSupplier";
    }

    @Override
    public void process(Bitmap bitmap) {
        super.process(bitmap);
        processBitmap(bitmap);
    }

    /**
     * Process bitmap.
     *
     * @param bitmap the bitmap
     */
    public abstract void processBitmap(Bitmap bitmap);

}
