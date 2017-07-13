package com.bless.fresco;

import android.content.Context;

import com.bless.fresco.core.FrescoCacheStatsTracker;
import com.bless.fresco.core.FrescoConfig;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.ImageCacheStatsTracker;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.facebook.imagepipeline.producers.NetworkFetcher;

import java.util.Set;

/**
 * 相关配置
 */
final class FrescoFactory {

    private FrescoFactory() {
    }

    /**
     * Gets disk cache config builder.
     *
     * @param config the config
     * @return the disk cache config builder
     */
    public static DiskCacheConfig.Builder getDiskCacheConfigBuilder(FrescoConfig config) {

        DiskCacheConfig.Builder builder = DiskCacheConfig.newBuilder(config.getContext());

        // 缓存图片基路径
        builder.setBaseDirectoryPath(config.getBaseDirectoryPath());

        // 文件夹名
        builder.setBaseDirectoryName(config.getBaseDirectoryName());

        // 日志记录器用于日志错误的缓存
//        builder.setCacheEventListener(cacheErrorLogger);

        // 缓存事件侦听器
//        builder.setCacheEventListener(cacheEventListener);

        // 类将包含一个注册表的缓存减少磁盘空间的环境
//        builder.setDiskTrimmableRegistry(diskTrimmableRegistry);

        // 默认缓存的最大大小
        builder.setMaxCacheSize(config.getMaxDiskCacheSize());

        // 缓存的最大大小,使用设备时低磁盘空间
        builder.setMaxCacheSizeOnLowDiskSpace(config.getMaxCacheSizeOnLowDiskSpace());

        // 缓存的最大大小,当设备极低磁盘空间
        builder.setMaxCacheSizeOnVeryLowDiskSpace(config.getMaxCacheSizeOnVeryLowDiskSpace());

        return builder;
    }

    /**
     * Gets image pipeline config builder.
     *
     * @param config the config
     * @return the image pipeline config builder
     */
    public static ImagePipelineConfig.Builder getImagePipelineConfigBuilder(FrescoConfig config) {

        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(config.getContext());

        DiskCacheConfig diskCacheConfig = getDiskCacheConfigBuilder(config).build();

        builder.setBitmapsConfig(config.getBitmapConfig());
        builder.setImageCacheStatsTracker(FrescoCacheStatsTracker.getInstance());
        builder.setDownsampleEnabled(true);
        builder.setResizeAndRotateEnabledForNetwork(true);
        builder.setMainDiskCacheConfig(diskCacheConfig);
        builder.setProgressiveJpegConfig(getDefaultProgressiveJpegConfig());
        builder.setBitmapMemoryCacheParamsSupplier(getSupplierMemoryCacheParams(config));

        return builder;
    }

    /**
     * Gets image pipeline config builder.
     *
     * @param context                          the context
     * @param animatedImageFactory             图片加载动画
     * @param bitmapMemoryCacheParamsSupplier  内存缓存配置（一级缓存，已解码的图片）
     * @param cacheKeyFactory                  缓存Key工厂
     * @param encodedMemoryCacheParamsSupplier 内存缓存和未解码的内存缓存的配置（二级缓存）
     * @param executorSupplier                 线程池配置
     * @param imageCacheStatsTracker           统计缓存的命中率
     * @param imageDecoder                     图片解码器配置
     * @param isPrefetchEnabledSupplier        图片预览（缩略图，预加载图等）预加载到文件缓存
     * @param mainDiskCacheConfig              磁盘缓存配置（总，三级缓存）
     * @param memoryTrimmableRegistry          内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了
     * @param networkFetcher                   自定的网络层配置：如OkHttp，Volley
     * @param poolFactory                      线程池工厂配置
     * @param progressiveJpegConfig            渐进式JPEG图
     * @param requestListeners                 图片请求监听
     * @param resizeAndRotateEnabledForNetwork 调整和旋转是否支持网络图片
     * @param smallImageDiskCacheConfig        磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
     * @return image pipeline config builder
     */
    public static ImagePipelineConfig.Builder getImagePipelineConfigBuilder(
            Context context, AnimatedImageFactory animatedImageFactory,
            Supplier<MemoryCacheParams> bitmapMemoryCacheParamsSupplier,
            CacheKeyFactory cacheKeyFactory,
            Supplier<MemoryCacheParams> encodedMemoryCacheParamsSupplier,
            ExecutorSupplier executorSupplier,
            ImageCacheStatsTracker imageCacheStatsTracker,
            ImageDecoder imageDecoder,
            Supplier<Boolean> isPrefetchEnabledSupplier,
            DiskCacheConfig mainDiskCacheConfig,
            MemoryTrimmableRegistry memoryTrimmableRegistry,
            NetworkFetcher networkFetcher, PoolFactory poolFactory,
            ProgressiveJpegConfig progressiveJpegConfig,
            Set<RequestListener> requestListeners,
            boolean resizeAndRotateEnabledForNetwork,
            DiskCacheConfig smallImageDiskCacheConfig) {

        if (context == null)
            return null;
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);

        // 图片加载动画
        if (null != animatedImageFactory)
            builder.setAnimatedImageFactory(animatedImageFactory);

        // 内存缓存配置（一级缓存，已解码的图片）
        if (null != bitmapMemoryCacheParamsSupplier)
            builder.setBitmapMemoryCacheParamsSupplier(bitmapMemoryCacheParamsSupplier);

        // 缓存Key工厂
        if (null != cacheKeyFactory)
            builder.setCacheKeyFactory(cacheKeyFactory);

        // 内存缓存和未解码的内存缓存的配置（二级缓存）
        if (null != encodedMemoryCacheParamsSupplier)
            builder.setEncodedMemoryCacheParamsSupplier(encodedMemoryCacheParamsSupplier);

        // 线程池配置
        if (null != executorSupplier)
            builder.setExecutorSupplier(executorSupplier);

        // 统计缓存的命中率
        if (null != imageCacheStatsTracker)
            builder.setImageCacheStatsTracker(imageCacheStatsTracker);

        // 图片解码器配置
        if (null != imageDecoder)
            builder.setImageDecoder(imageDecoder);

        // 图片预览（缩略图，预加载图等）预加载到文件缓存
        if (null != isPrefetchEnabledSupplier)
            builder.setIsPrefetchEnabledSupplier(isPrefetchEnabledSupplier);

        // 磁盘缓存配置（总，三级缓存）
        if (null != mainDiskCacheConfig)
            builder.setMainDiskCacheConfig(mainDiskCacheConfig);

        // 内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了
        if (null != memoryTrimmableRegistry)
            builder.setMemoryTrimmableRegistry(memoryTrimmableRegistry);

        // 自定的网络层配置：如OkHttp，Volley
        if (null != networkFetcher)
            builder.setNetworkFetcher(networkFetcher);

        // 线程池工厂配置
        if (null != poolFactory)
            builder.setPoolFactory(poolFactory);

        // 渐进式JPEG图
        if (null != progressiveJpegConfig)
            builder.setProgressiveJpegConfig(progressiveJpegConfig);

        // 图片请求监听
        if (null != requestListeners)
            builder.setRequestListeners(requestListeners);

        // 调整和旋转是否支持网络图片
        builder.setResizeAndRotateEnabledForNetwork(resizeAndRotateEnabledForNetwork);

        // 磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
        if (null != smallImageDiskCacheConfig)
            builder.setSmallImageDiskCacheConfig(smallImageDiskCacheConfig);

        return builder;
    }

    /**
     * 修改内存图片缓存数量，空间策略（这个方式有点恶心）
     *
     * @return
     */
    private static Supplier<MemoryCacheParams> getSupplierMemoryCacheParams(FrescoConfig config) {
        final MemoryCacheParams memoryCacheParams = getMemoryCacheParams(config);
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return memoryCacheParams;
            }
        };
        return mSupplierMemoryCacheParams;
    }

    /**
     * @return 内存配置
     */
    private static MemoryCacheParams getMemoryCacheParams(FrescoConfig config) {
        MemoryCacheParams params = new MemoryCacheParams(config.getMaxMemoryCacheSize(),
                config.getMaxCacheEntries(),
                config.getMaxMemoryCacheSize(),
                config.getMaxEvictionQueueEntries(),
                config.getMaxCacheEntrySize());
        return params;
    }

    /**
     * 渐进式JPEG图
     *
     * @return default progressive jpeg config
     */
    public static ProgressiveJpegConfig getDefaultProgressiveJpegConfig() {
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
        return pjpegConfig;
    }
}
