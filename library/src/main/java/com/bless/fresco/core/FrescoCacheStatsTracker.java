package com.bless.fresco.core;

import com.bless.fresco.logger.FrescoLogger;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      图片缓存数据追踪
 *
 * </pre>
 */
public class FrescoCacheStatsTracker implements ImageCacheStatsTracker {
    private static volatile FrescoCacheStatsTracker instance;

    private FrescoCacheStatsTracker() {
    }

    public static FrescoCacheStatsTracker getInstance() {
        if (instance == null) {
            synchronized (FrescoCacheStatsTracker.class) {
                if (instance == null) {
                    instance = new FrescoCacheStatsTracker();
                }
            }
        }
        return instance;
    }

    @Override
    public void onBitmapCachePut() {
        FrescoLogger.getLogger().log("onBitmapCachePut");
    }

    @Override
    public void onBitmapCacheHit(CacheKey cacheKey) {
        FrescoLogger.getLogger().log("onBitmapCacheHit#CacheKey=" + cacheKey != null ? cacheKey.toString() : null);
    }

    @Override
    public void onBitmapCacheMiss() {
        FrescoLogger.getLogger().log("onBitmapCacheMiss");
    }

    @Override
    public void onMemoryCachePut() {
        FrescoLogger.getLogger().log("onMemoryCachePut");
    }

    @Override
    public void onMemoryCacheHit(CacheKey cacheKey) {
        FrescoLogger.getLogger().log("onMemoryCacheHit#CacheKey=" + cacheKey != null ? cacheKey.toString() : null);
    }

    @Override
    public void onMemoryCacheMiss() {
        FrescoLogger.getLogger().log("onMemoryCacheMiss");
    }

    @Override
    public void onStagingAreaHit(CacheKey cacheKey) {
        FrescoLogger.getLogger().log("onStagingAreaHit#CacheKey=" + cacheKey != null ? cacheKey.toString() : null);
    }

    @Override
    public void onStagingAreaMiss() {
        FrescoLogger.getLogger().log("onStagingAreaMiss");
    }

    @Override
    public void onDiskCacheHit() {
        FrescoLogger.getLogger().log("onDiskCacheHit");
    }

    @Override
    public void onDiskCacheMiss() {
        FrescoLogger.getLogger().log("onDiskCacheMiss");
    }

    @Override
    public void onDiskCacheGetFail() {
        FrescoLogger.getLogger().log("onDiskCacheGetFail");
    }

    @Override
    public void registerBitmapMemoryCache(CountingMemoryCache<?, ?> bitmapMemoryCache) {
        FrescoLogger.getLogger().log("registerBitmapMemoryCache");
    }

    @Override
    public void registerEncodedMemoryCache(CountingMemoryCache<?, ?> encodedMemoryCache) {
        FrescoLogger.getLogger().log("registerEncodedMemoryCache");
    }
}
