package com.bless.fresco.core;

import android.graphics.Bitmap;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.request.ImageRequest;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      默认配置中心
 *
 * </pre>
 */
public class DefaultConfigCentre {

    private DefaultConfigCentre() {
    }

    /**
     * The constant DEFAULT_IS_DEBUG.
     */
    public static final boolean DEFAULT_IS_DEBUG = false;

    /**
     * The constant DEFAULT_TAG.
     */
    public static final String DEFAULT_TAG = "fresco_image";

    /**
     * The constant KB.
     */
    public static final int KB = 1024;

    /**
     * The constant MB.
     */
    public static final int MB = KB << 10;

    /**
     * The constant DEFAULT_BITMAP_CONFIG.
     */
    public static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    /**
     * The constant DEFAULT_MAX_DISK_CACHE_SIZE.
     */
    public static final int DEFAULT_MAX_DISK_CACHE_SIZE = 60 * MB;

    /**
     * The constant DEFAULT_LOW_SPACE_DISK_CACHE_SIZE.
     */
    public static final int DEFAULT_LOW_SPACE_DISK_CACHE_SIZE = 20 * MB;

    /**
     * The constant DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE.
     */
    public static final int DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE = 8 * MB;

    /**
     * The constant DEFAULT_DISK_CACHE_DIR_NAME.
     */
    public static final String DEFAULT_DISK_CACHE_DIR_NAME = "FrescoCache";

    /**
     * The constant DEFAULT_FADE_DURATION.
     */
    public static final int DEFAULT_FADE_DURATION = 300;

    /**
     * The constant DEFAULT_SCALE_TYPE.
     */
    public static final ScalingUtils.ScaleType DEFAULT_SCALE_TYPE = ScalingUtils.ScaleType.CENTER_CROP;

    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final float DEFAULT_RADIUS = 0;

    /**
     * The constant DEFAULT_PRIORITY.
     */
    public static final FrescoPriority DEFAULT_PRIORITY = FrescoPriority.HIGH;

    /**
     * The constant DEFAULT_AUTO_ROTATE.
     */
    public static final boolean DEFAULT_AUTO_ROTATE = false;

    /**
     * The constant DEFAULT_REQUEST_LEVEL.
     */
    public static final ImageRequest.RequestLevel DEFAULT_REQUEST_LEVEL = ImageRequest.RequestLevel.FULL_FETCH;

    /**
     * The constant MAX_HEAP_SIZE.
     */
    public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();// 分配的可用内存

    /**
     * The constant MAX_MEMORY_CACHE_SIZE.
     */
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;// 使用的缓存数量
}
