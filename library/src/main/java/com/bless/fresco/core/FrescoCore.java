package com.bless.fresco.core;

import android.content.Context;

import com.bless.fresco.util.ParamCheckUtil;
import com.bless.fresco.widget.FrescoImageView;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      核心功能
 *
 * </pre>
 */
public class FrescoCore {

    private static PipelineDraweeControllerBuilderSupplier mDraweeControllerBuilderSupplier;

    /**
     * 初始化
     *
     * @param context             the context
     * @param imagePipelineConfig the image pipeline config
     */
    public static void init(Context context,ImagePipelineConfig imagePipelineConfig){
        ImagePipelineFactory.initialize(imagePipelineConfig);
        mDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context);
        FrescoImageView.initialize(mDraweeControllerBuilderSupplier);
    }

    /**
     * New drawee controller builder pipeline drawee controller builder.
     *
     * @return create a new DraweeControllerBuilder instance.
     */
    public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {
        ParamCheckUtil.checkNotNull(mDraweeControllerBuilderSupplier,"FrescoCore not initialize");
        return mDraweeControllerBuilderSupplier.get();
    }

    /**
     * Gets image pipeline factory.
     *
     * @return the image pipeline factory
     */
    public static ImagePipelineFactory getImagePipelineFactory() {
        return ImagePipelineFactory.getInstance();
    }

    /**
     * Gets image pipeline.
     *
     * @return get ImagePipeline instance from ImagePipelineFactory.
     */
    public static ImagePipeline getImagePipeline() {
        return getImagePipelineFactory().getImagePipeline();
    }

    /**
     * shut down .
     */
    public static void shutDownDraweeControllerBuilderSupplier(){
        mDraweeControllerBuilderSupplier = null;
    }
}
