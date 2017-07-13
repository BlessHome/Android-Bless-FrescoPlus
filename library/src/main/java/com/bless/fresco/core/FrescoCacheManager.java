package com.bless.fresco.core;

import android.net.Uri;

import com.bless.fresco.logger.FrescoLogger;
import com.bless.fresco.util.ParamCheckUtil;
import com.bless.fresco.request.callback.CacheCallback;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.datasource.BaseBooleanSubscriber;
import com.facebook.datasource.DataSource;

import java.text.DecimalFormat;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco缓存管理器
 *
 * </pre>
 */
public class FrescoCacheManager {

    private static volatile FrescoCacheManager mInstance;

    private FrescoCacheManager() {
    }

    /**
     * 获取实例.
     *
     * @return the instance
     */
    public static FrescoCacheManager getInstance() {
        if (mInstance == null) {
            synchronized (FrescoCacheManager.class) {
                if (mInstance == null) {
                    mInstance = new FrescoCacheManager();
                }
            }
        }
        return mInstance;
    }

    private long getHasUseDiskCacheSize() {
        return FrescoCore.getImagePipelineFactory().getMainFileCache().getSize();
    }

    /**
     * 获取磁盘缓存大小，单位为KB
     *
     * @return has use disk cache space size,unit 'KB'. Keep two decimal places,example:2.2222->2.22.
     */
    public String getHasUseDiskCacheSizeWithKB() {
        long size = getHasUseDiskCacheSize();
        if (size <= 0)
            return "0";
        return new DecimalFormat("0.00").format(size / 1024.0);
    }

    /**
     * 获取磁盘缓存大小，单位为MB
     *
     * @return has use disk cache space size,unit 'MB'. Keep two decimal places,example:2.2222 is 2.22.
     */
    public String getHasUseDiskCacheSizeWithMB() {
        long size = getHasUseDiskCacheSize();
        if (size <= 0)
            return "0";
        return new DecimalFormat("0.00").format((size / 1024.0) / 1024.0);
    }

    /**
     * 判断是否在磁盘缓存中，结果通过回调返回
     *
     * @param uri      uri
     * @param callback the callback
     * @return true if the image was found in the disk cache,false otherwise.
     */
    public void isInDiskCache(final Uri uri, final CacheCallback<Boolean> callback) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        DataSource<Boolean> dataSource = FrescoCore.getImagePipeline().isInDiskCache(uri);
        if (dataSource == null)
            return;
        dataSource.subscribe(new BaseBooleanSubscriber() {
            @Override
            protected void onNewResultImpl(boolean isFoundInDisk) {
                if (callback != null)
                    callback.onResult(isFoundInDisk);
            }

            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {
                if (callback != null)
                    callback.onResult(false);
            }
        }, UiThreadImmediateExecutorService.getInstance());
    }

    /**
     * 判断是否在位图内存缓存中
     *
     * @param uri uri
     * @return true if the image was found in the bitmap memory cache,false otherwise.
     */
    public boolean isInBitmapMemoryCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        return FrescoCore.getImagePipeline().isInBitmapMemoryCache(uri);
    }

    /**
     * 从内存缓存&磁盘缓存中删除
     *
     * @param uri The uri of the image will to remove,include memory and disk cache.
     */
    public void removeFromMemoryAndDiskCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoCore.getImagePipeline().evictFromCache(uri);
    }

    /**
     * 从内存缓存中删除
     *
     * @param uri The uri of the image will to remove,only include memory cache.
     */
    public void removeFromMemoryCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoCore.getImagePipeline().evictFromMemoryCache(uri);
    }

    /**
     * 从磁盘缓存中删除
     *
     * @param uri The uri of the image will to remove,only include disk cache.
     */
    public void removeFromDiskCache(Uri uri) {
        FrescoLogger.getLogger().log(uri);
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        FrescoCore.getImagePipeline().evictFromDiskCache(uri);
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCaches() {
        FrescoCore.getImagePipeline().clearDiskCaches();
    }

    /**
     * 清除内存缓存(包括位图缓存和编码图像缓存)。
     */
    public void clearMemoryCaches() {
        FrescoCore.getImagePipeline().clearMemoryCaches();
    }
}
