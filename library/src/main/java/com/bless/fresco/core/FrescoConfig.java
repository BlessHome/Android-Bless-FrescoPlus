package com.bless.fresco.core;

import android.content.Context;
import android.graphics.Bitmap;

import com.bless.fresco.exception.FIllegalArgumentException;
import com.bless.fresco.util.DiskCacheUtil;

import java.io.File;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco 配置
 *
 * </pre>
 */
public class FrescoConfig {

    private boolean isDebug;

    private String logTag;

    private Context mContext;

    private Bitmap.Config mBitmapConfig;

    private int mMaxDiskCacheSize;

    private int mMaxCacheSizeOnLowDiskSpace;

    private int mMaxCacheSizeOnVeryLowDiskSpace;

    private File mBaseDirectoryPath;

    private String mBaseDirectoryName;

    private int mMaxCacheEntries;
    private int mMaxMemoryCacheSize;
    private int mMaxEvictionQueueEntries;
    private int mMaxCacheEntrySize;


    private FrescoConfig(Builder builder) {
        isDebug = builder.isDebug;
        logTag = builder.logTag == null ? DefaultConfigCentre.DEFAULT_TAG : builder.logTag;
        mContext = builder.mContext;
        mBitmapConfig = builder.mBitmapConfig == null ? DefaultConfigCentre.DEFAULT_BITMAP_CONFIG : builder.mBitmapConfig;
        mMaxDiskCacheSize = builder.mMaxDiskCacheSize <= 0 ? DefaultConfigCentre.DEFAULT_MAX_DISK_CACHE_SIZE : builder.mMaxDiskCacheSize;
        mMaxCacheSizeOnLowDiskSpace = builder.mMaxCacheSizeOnLowDiskSpace <= 0 ? DefaultConfigCentre.DEFAULT_LOW_SPACE_DISK_CACHE_SIZE : builder.mMaxCacheSizeOnLowDiskSpace;
        mMaxCacheSizeOnVeryLowDiskSpace = builder.mMaxCacheSizeOnVeryLowDiskSpace <= 0 ? DefaultConfigCentre.DEFAULT_VERY_LOW_SPACE_DISK_CACHE_SIZE : builder.mMaxCacheSizeOnVeryLowDiskSpace;
        mBaseDirectoryPath = builder.mBaseDirectoryPath == null ? getDefaultDiskCacheDir() : builder.mBaseDirectoryPath;
        mBaseDirectoryName = builder.mBaseDirectoryName == null ? DefaultConfigCentre.DEFAULT_DISK_CACHE_DIR_NAME : builder.mBaseDirectoryName;
        mMaxCacheEntries = builder.mMaxCacheEntries <= 0 ? Integer.MAX_VALUE : builder.mMaxCacheEntries;
        mMaxCacheEntrySize = builder.mMaxCacheEntrySize <= 0 ? Integer.MAX_VALUE : builder.mMaxCacheEntrySize;
        mMaxMemoryCacheSize = builder.mMaxMemoryCacheSize <= 0 ? DefaultConfigCentre.MAX_MEMORY_CACHE_SIZE : builder.mMaxMemoryCacheSize;
        mMaxEvictionQueueEntries = builder.mMaxEvictionQueueEntries <= 0 ? Integer.MAX_VALUE : builder.mMaxEvictionQueueEntries;
    }

    /**
     * Is debug boolean.
     *
     * @return the boolean
     */
    public boolean isDebug() {
        return isDebug;
    }

    /**
     * Gets log tag.
     *
     * @return the log tag
     */
    public String getLogTag() {
        return logTag;
    }

    /**
     * Gets context.
     *
     * @return the context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Gets bitmap config.
     *
     * @return the bitmap config
     */
    public Bitmap.Config getBitmapConfig() {
        return mBitmapConfig;
    }

    /**
     * Gets max disk cache size.
     *
     * @return the max disk cache size
     */
    public int getMaxDiskCacheSize() {
        return mMaxDiskCacheSize;
    }

    /**
     * Gets max cache size on low disk space.
     *
     * @return the max cache size on low disk space
     */
    public int getMaxCacheSizeOnLowDiskSpace() {
        return mMaxCacheSizeOnLowDiskSpace;
    }

    /**
     * Gets max cache size on very low disk space.
     *
     * @return the max cache size on very low disk space
     */
    public int getMaxCacheSizeOnVeryLowDiskSpace() {
        return mMaxCacheSizeOnVeryLowDiskSpace;
    }

    /**
     * Gets base directory path.
     *
     * @return the base directory path
     */
    public File getBaseDirectoryPath() {
        return mBaseDirectoryPath;
    }

    /**
     * Gets base directory name.
     *
     * @return the base directory name
     */
    public String getBaseDirectoryName() {
        return mBaseDirectoryName;
    }

    /**
     * 内存缓存中图片的最大数量
     *
     * @return max cache entries
     */
    public int getMaxCacheEntries() {
        return mMaxCacheEntries;
    }

    /**
     * 使用的缓存数量
     *
     * @return max memory cache size
     */
    public int getMaxMemoryCacheSize() {
        return mMaxMemoryCacheSize;
    }

    /**
     * 内存缓存中准备清除的总图片的最大数量
     *
     * @return max eviction queue entries
     */
    public int getMaxEvictionQueueEntries() {
        return mMaxEvictionQueueEntries;
    }

    /**
     * 内存缓存中单个图片的最大大小
     *
     * @return max cache entry size
     */
    public int getMaxCacheEntrySize() {
        return mMaxCacheEntrySize;
    }


    /**
     * New builder builder.
     *
     * @param context the context
     * @return the builder
     */
    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    /**
     * 配置生成器
     */
    public static final class Builder {
        private boolean isDebug = DefaultConfigCentre.DEFAULT_IS_DEBUG;
        private String logTag = DefaultConfigCentre.DEFAULT_TAG;
        private Context mContext;
        private Bitmap.Config mBitmapConfig;
        private int mMaxDiskCacheSize;
        private int mMaxCacheSizeOnLowDiskSpace;
        private int mMaxCacheSizeOnVeryLowDiskSpace;
        private File mBaseDirectoryPath;
        private String mBaseDirectoryName;

        private int mMaxCacheEntries;
        private int mMaxCacheEntrySize;
        private int mMaxMemoryCacheSize;
        private int mMaxEvictionQueueEntries;


        private Builder(Context context) {
            mContext = context;
        }

        /**
         * Sets debug.
         *
         * @param debug the debug
         * @return the debug
         */
        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        /**
         * Sets tag.
         *
         * @param tag the tag
         * @return the tag
         */
        public Builder setTag(String tag) {
            logTag = tag;
            return this;
        }

        /**
         * Sets bitmap config.
         *
         * @param val The Bitmap Config,such as:RGB_565,ARGB_8888,ARGB_4444.
         * @return this bitmap config
         */
        public Builder setBitmapConfig(Bitmap.Config val) {
            mBitmapConfig = val;
            return this;
        }

        /**
         * Sets max disk cache size.
         *
         * @param val The disk cache max size,unit is MB
         * @return this max disk cache size
         */
        public Builder setMaxDiskCacheSize(int val) {
            mMaxDiskCacheSize = val;
            return this;
        }

        /**
         * Sets max cache size on low disk space.
         *
         * @param val The maximum size of the cache, the use of the device when the low disk space
         * @return this max cache size on low disk space
         */
        public Builder setMaxCacheSizeOnLowDiskSpace(int val) {
            this.mMaxCacheSizeOnLowDiskSpace = val;
            return this;
        }

        /**
         * Sets max cache size on very low disk space.
         *
         * @param val The maximum size of the cache, when the device is extremely low disk space
         * @return this max cache size on very low disk space
         */
        public Builder setMaxCacheSizeOnVeryLowDiskSpace(int val) {
            this.mMaxCacheSizeOnVeryLowDiskSpace = val;
            return this;
        }

        /**
         * Sets base directory path.
         *
         * @param val The disk cache dir,example:context.getApplicationContext().getCacheDir().
         * @return this base directory path
         */
        public Builder setBaseDirectoryPath(File val) {
            mBaseDirectoryPath = val;
            return this;
        }

        /**
         * Sets base directory name.
         *
         * @param val The disk cache file name,example:FrescoCache
         * @return base directory name
         */
        public Builder setBaseDirectoryName(String val) {
            mBaseDirectoryName = val;
            return this;
        }

        /**
         * Sets max cache entries.
         *
         * @param val the val
         */
        public void setMaxCacheEntries(int val) {
            this.mMaxCacheEntries = val;
        }

        /**
         * Sets max cache entry size.
         *
         * @param val the val
         */
        public void setMaxCacheEntrySize(int val) {
            this.mMaxCacheEntrySize = val;
        }

        /**
         * Sets max memory cache size.
         *
         * @param val the val
         */
        public void setMaxMemoryCacheSize(int val) {
            this.mMaxMemoryCacheSize = val;
        }

        /**
         * Sets max eviction queue entries.
         *
         * @param val the val
         */
        public void setMaxEvictionQueueEntries(int val) {
            this.mMaxEvictionQueueEntries = val;
        }

        /**
         * Build fresco config.
         *
         * @return the fresco config
         */
        public FrescoConfig build() {
            return new FrescoConfig(this);
        }
    }

    /**
     * Gets default disk cache dir.
     *
     * @return the default disk cache dir
     */
    public File getDefaultDiskCacheDir() {
        if (mContext == null)
            throw new FIllegalArgumentException("Context can not be null");
        return DiskCacheUtil.getDiskLruCacheDir(mContext);
    }
}
