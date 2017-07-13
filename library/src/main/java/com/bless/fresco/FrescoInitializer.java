package com.bless.fresco;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bless.fresco.core.FrescoConfig;
import com.bless.fresco.core.FrescoCore;
import com.bless.fresco.exception.FNullPointerException;
import com.bless.fresco.widget.FrescoImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco 初始化
 *
 * </pre>
 */
public class FrescoInitializer {

    private static volatile FrescoInitializer mInstance = null;

    private Context mContext;
    private boolean isDebug;
    private String logTag;

    private FrescoInitializer() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FrescoInitializer getInstance() {
        if (mInstance == null) {
            synchronized (FrescoInitializer.class) {
                if (mInstance == null) {
                    mInstance = new FrescoInitializer();
                }
            }
        }
        return mInstance;
    }

    /**
     * Init.
     *
     * @param context the context
     */
    public void init(Context context) {
        init(context, null);
    }

    /**
     * Init.
     *
     * @param context      the context
     * @param frescoConfig the fresco config
     */
    public void init(Context context, FrescoConfig frescoConfig) {
        if (context == null) {
            throw new FNullPointerException("Fresco initialize error,cause:context is null");
        }
        mContext = !(context instanceof Application) ? context.getApplicationContext() : context;
        initialize(frescoConfig);
    }

    private void initialize(FrescoConfig config) {
        final FrescoConfig frescoConfig;
        if (config == null)
            config = FrescoConfig.newBuilder(mContext).build();
        frescoConfig = config;

        isDebug = frescoConfig.isDebug();
        logTag = frescoConfig.getLogTag();

        printFrescoConfigLog(frescoConfig);

        ImagePipelineConfig pipelineConfig = FrescoFactory.getImagePipelineConfigBuilder(frescoConfig).build();

        FrescoCore.init(mContext, pipelineConfig);
    }

    /**
     * Shuts FrescoInitializer down.
     */
    public void shutDown() {
        FrescoCore.shutDownDraweeControllerBuilderSupplier();
        FrescoImageView.shutDown();
        ImagePipelineFactory.shutDown();
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
     * 打印 Fresco 配置日志
     *
     * @param frescoConfig config
     */
    private void printFrescoConfigLog(FrescoConfig frescoConfig) {
        if (isDebug) {
            Log.d(FrescoInitializer.getInstance().getLogTag(), "Fresco init...Config:"
                    + "DiskCacheDir->" + frescoConfig.getBaseDirectoryPath()
                    + ",MaxDiskCacheSize->" + frescoConfig.getMaxDiskCacheSize()
                    + ",BitmapConfig->" + frescoConfig.getBitmapConfig()
                    + ",IsDebug->" + frescoConfig.isDebug()
                    + ",Tag->" + frescoConfig.getLogTag());
        }
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearDiskCaches();
    }

    /**
     * 清除所有的缓存（内存和磁盘）
     */
    public void clearCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }
}
