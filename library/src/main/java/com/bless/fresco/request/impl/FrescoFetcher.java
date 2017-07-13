package com.bless.fresco.request.impl;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.bless.fresco.core.DefaultConfigCentre;
import com.bless.fresco.core.FrescoCore;
import com.bless.fresco.logger.FrescoLogger;
import com.bless.fresco.request.callback.FetchCallback;
import com.bless.fresco.request.callback.LoadCallback;
import com.bless.fresco.request.supplier.FetchImageControllerListenerSupplier;
import com.bless.fresco.core.FrescoPriority;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * The type Fresco fetcher.
 *
 * @param <VIEW> the type parameter
 */
public class FrescoFetcher<VIEW extends SimpleDraweeView> extends AbsBaseFetcher<VIEW> {
    private boolean autoRotateEnabled;
    private int fadeDuration;
    private Drawable failureDrawable;
    private Drawable defaultDrawable;
    private Drawable overlayDrawable;
    private Drawable progressBarDrawable;
    private Drawable retryDrawable;
    private Drawable pressedDrawable;
    private int resizeWidth;
    private int resizeHeight;
    private ScalingUtils.ScaleType scaleType;
    private float radius;
    private FrescoPriority requestPriority;
    private ImageRequest.RequestLevel requestLevel;
    private Postprocessor postprocessor;
    private boolean isAutoPlayAnimations = true;
    private boolean isRetainImageOnFailure = true;
    private boolean isTapToRetryEnabled = true;
    private ExecutorService executeBackgroundTask = Executors.newSingleThreadExecutor();
    private FrescoFetcher(Builder builder) {
        autoRotateEnabled = builder.autoRotateEnabled;
        fadeDuration = builder.fadeDuration <= 0 ? DefaultConfigCentre.DEFAULT_FADE_DURATION : builder.fadeDuration;
        failureDrawable = builder.failureDrawable;
        defaultDrawable = builder.defaultDrawable;
        overlayDrawable = builder.overlayDrawable;
        progressBarDrawable = builder.progressBarDrawable;
        retryDrawable = builder.retryDrawable;
        pressedDrawable = builder.pressedDrawable;
        resizeWidth = builder.resizeWidth <= 0 ? 0 : builder.resizeWidth;
        resizeHeight = builder.resizeHeight <= 0 ? 0 : builder.resizeHeight;
        scaleType = builder.scaleType == null ? DefaultConfigCentre.DEFAULT_SCALE_TYPE : builder.scaleType;
        radius = builder.radius <= 0 ? DefaultConfigCentre.DEFAULT_RADIUS : builder.radius;
        requestPriority = builder.requestPriority == null ? DefaultConfigCentre.DEFAULT_PRIORITY : builder.requestPriority;
        requestLevel = builder.requestLevel == null ? DefaultConfigCentre.DEFAULT_REQUEST_LEVEL : builder.requestLevel;
        postprocessor = builder.postprocessor;
        isAutoPlayAnimations = builder.isAutoPlayAnimations;
        isRetainImageOnFailure = builder.isRetainImageOnFailure;
        isTapToRetryEnabled = builder.isTapToRetryEnabled;
    }

    /**
     * New fetcher builder.
     *
     * @return the builder
     */
    public static Builder newFetcher() {
        return new Builder();
    }


    @Override
    public void fetch(VIEW view, Uri uri) {
        super.fetch(view, uri);
        FrescoLogger.getLogger().log(uri);
        fetchImage(view, uri, null);
    }

    @Override
    public void fetch(VIEW view, Uri uri, FetchCallback<ImageInfo> callback) {
        super.fetch(view, uri, callback);
        FrescoLogger.getLogger().log(uri);
        fetchImage(view, uri, callback);
    }

    @Override
    public void fetch(Uri uri, LoadCallback<Bitmap> callback) {
        super.fetch(uri, callback);
        FrescoLogger.getLogger().log(uri);
        fetchImage(uri, callback);
    }


    @Override
    protected Object beforeExecute(Uri uri) {
        return null;
    }

    /**
     * @param view The draweeView is to display the bitmap
     * @param uri         The source uri
     * @param callback    Listening to the success or failure
     */
    private void fetchImage(VIEW view, Uri uri, FetchCallback<ImageInfo> callback) {
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(null);
        hierarchyBuilder.setFadeDuration(fadeDuration);
        RoundingParams roundingParams = view.getHierarchy().getRoundingParams(); // 解决FrescoImageView不能圆形问题
        roundingParams.fromCornersRadius(radius);
        hierarchyBuilder.setRoundingParams(roundingParams);
//        hierarchyBuilder.setRoundingParams(RoundingParams.fromCornersRadius(radius));
        hierarchyBuilder.setActualImageScaleType(scaleType);
        if (defaultDrawable != null)
            hierarchyBuilder.setPlaceholderImage(defaultDrawable, scaleType);
        if (pressedDrawable != null)
            hierarchyBuilder.setPressedStateOverlay(pressedDrawable);
        if (retryDrawable != null)
            hierarchyBuilder.setRetryImage(retryDrawable);
        if (overlayDrawable != null)
            hierarchyBuilder.setOverlay(overlayDrawable);
        if (failureDrawable != null)
            hierarchyBuilder.setFailureImage(failureDrawable, scaleType);
        if (progressBarDrawable != null)
            hierarchyBuilder.setProgressBarImage(progressBarDrawable);
        GenericDraweeHierarchy hierarchy = hierarchyBuilder.build();

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        requestBuilder.setLowestPermittedRequestLevel(requestLevel);
        requestBuilder.setAutoRotateEnabled(autoRotateEnabled);
        if (postprocessor != null)
            requestBuilder.setPostprocessor(postprocessor);
        com.facebook.imagepipeline.common.Priority priority = requestPriority == FrescoPriority.HIGH ? com.facebook.imagepipeline.common.Priority.HIGH : com.facebook.imagepipeline.common.Priority.MEDIUM;
        requestBuilder.setRequestPriority(priority);
        if (resizeWidth > 0 && resizeHeight > 0)
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        ImageRequest imageRequest = requestBuilder.build();

        DraweeController draweeController = FrescoCore.newDraweeControllerBuilder()
                .setOldController(view.getController())
                .setAutoPlayAnimations(isAutoPlayAnimations)
                .setRetainImageOnFailure(isRetainImageOnFailure)
                .setTapToRetryEnabled(isTapToRetryEnabled)
                .setImageRequest(imageRequest)
                .setControllerListener(FetchImageControllerListenerSupplier.newInstance(callback))
                .build();
        view.setHierarchy(hierarchy);
        view.setController(draweeController);
    }

    private void fetchImage(final Uri uri, final LoadCallback<Bitmap> callback) {
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        requestBuilder.setLowestPermittedRequestLevel(requestLevel);
        requestBuilder.setAutoRotateEnabled(autoRotateEnabled);
        if (postprocessor != null)
            requestBuilder.setPostprocessor(postprocessor);
        com.facebook.imagepipeline.common.Priority priority = requestPriority == FrescoPriority.HIGH ? com.facebook.imagepipeline.common.Priority.HIGH : com.facebook.imagepipeline.common.Priority.MEDIUM;
        requestBuilder.setRequestPriority(priority);
        if (resizeWidth > 0 && resizeHeight > 0)
            requestBuilder.setResizeOptions(new ResizeOptions(resizeWidth, resizeHeight));
        ImageRequest imageRequest = requestBuilder.build();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = FrescoCore.getImagePipeline().fetchDecodedImage(imageRequest, null);
        //callback event processing thread pool.If you pass the UI thread pool you can not handle time-consuming operation.
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                 @Override
                                 public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                                     if (callback == null)
                                         return;
                                     if (bitmap != null && !bitmap.isRecycled()) {
                                         FrescoLogger.getLogger().log("Bitmap:[height="+bitmap.getHeight()+",width="+bitmap.getWidth()+"]");
                                         handlerBackgroundTask(new Callable<Bitmap>() {
                                             @Override
                                             public Bitmap call() throws Exception {
                                                 final Bitmap resultBitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
                                                 if (resultBitmap != null && !resultBitmap.isRecycled())
                                                     postResult(resultBitmap,uri,callback);
                                                 return resultBitmap;
                                             }
                                         });
                                     }
                                 }

                                 @Override
                                 public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
                                     super.onCancellation(dataSource);
                                     if (callback == null)
                                         return;
                                     FrescoLogger.getLogger().log("onCancel");
                                     callback.onCancel(uri);
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     if (callback == null)
                                         return;
                                     Throwable throwable = null;
                                     if (dataSource != null) {
                                         throwable = dataSource.getFailureCause();
                                     }
                                     FrescoLogger.getLogger().log(throwable);
                                     callback.onFailure(uri, throwable);
                                 }
                             },
                UiThreadImmediateExecutorService.getInstance());
    }

    private <T> Future<T> handlerBackgroundTask(Callable<T> callable){
        return executeBackgroundTask.submit(callable);
    }

    /**
     * 回调UI线程中去
     * @param result
     * @param uri
     * @param callback
     * @param <T>
     */
    private <T> void postResult(final T result, final Uri uri, final LoadCallback<T> callback){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(uri,result);
            }
        });
    }

    /**
     * The type Builder.
     */
    public static final class Builder {
        private int fadeDuration;
        private Drawable failureDrawable;
        private Drawable defaultDrawable;
        private Drawable overlayDrawable;
        private Drawable progressBarDrawable;
        private Drawable retryDrawable;
        private Drawable pressedDrawable;
        private int resizeWidth;
        private int resizeHeight;
        private ScalingUtils.ScaleType scaleType;
        private float radius;
        private FrescoPriority requestPriority;
        private boolean autoRotateEnabled = DefaultConfigCentre.DEFAULT_AUTO_ROTATE;
        private ImageRequest.RequestLevel requestLevel;
        private Postprocessor postprocessor;
        private boolean isAutoPlayAnimations = true;
        private boolean isRetainImageOnFailure = true;
        private boolean isTapToRetryEnabled = true;

        private Builder() {
        }

        /**
         * With auto rotate enabled builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withAutoRotateEnabled(boolean val) {
            autoRotateEnabled = val;
            return this;
        }

        /**
         * With fade duration builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withFadeDuration(int val) {
            fadeDuration = val;
            return this;
        }

        /**
         * With failure drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withFailureDrawable(Drawable val) {
            failureDrawable = val;
            return this;
        }

        /**
         * With default drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withDefaultDrawable(Drawable val) {
            defaultDrawable = val;
            return this;
        }

        /**
         * With overlay drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withOverlayDrawable(Drawable val) {
            overlayDrawable = val;
            return this;
        }

        /**
         * With progress drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withProgressBarImage(Drawable val) {
            progressBarDrawable = val;
            return this;
        }

        /**
         * With retry drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withRetryDrawable(Drawable val) {
            retryDrawable = val;
            return this;
        }

        /**
         * With pressed drawable builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withPressedDrawable(Drawable val) {
            pressedDrawable = val;
            return this;
        }

        /**
         * With resize width builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withResizeWidth(int val) {
            resizeWidth = val;
            return this;
        }

        /**
         * With resize height builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withResizeHeight(int val) {
            resizeHeight = val;
            return this;
        }

        /**
         * With scale type builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withScaleType(ScalingUtils.ScaleType val) {
            scaleType = val;
            return this;
        }

        /**
         * With radius builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withRadius(float val) {
            radius = val;
            return this;
        }

        /**
         * With request priority builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withRequestPriority(FrescoPriority val) {
            requestPriority = val;
            return this;
        }

        /**
         * With request level builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withRequestLevel(ImageRequest.RequestLevel val) {
            requestLevel = val;
            return this;
        }

        /**
         * With postprocessor builder.
         *
         * @param val the val
         * @return the builder
         */
        public Builder withPostprocessor(Postprocessor val) {
            postprocessor = val;
            return this;
        }

        /**
         * Sets auto play animations.
         *
         * @param val the val
         * @return the auto play animations
         */
        public Builder setAutoPlayAnimations(boolean val) {
            isAutoPlayAnimations = val;
            return this;
        }

        /**
         * Sets retain image on failure.
         *
         * @param val the val
         * @return the retain image on failure
         */
        public Builder setRetainImageOnFailure(boolean val) {
            this.isRetainImageOnFailure = val;
            return this;
        }

        /**
         * Sets tap to retry enabled.
         *
         * @param val the val
         * @return the tap to retry enabled
         */
        public Builder setTapToRetryEnabled(boolean val) {
            isTapToRetryEnabled = val;
            return this;
        }

        /**
         * Build fresco fetcher.
         *
         * @return the fresco fetcher
         */
        public FrescoFetcher build() {
            return new FrescoFetcher(this);
        }
    }
}
