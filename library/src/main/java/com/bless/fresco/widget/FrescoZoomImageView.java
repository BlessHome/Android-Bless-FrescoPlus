//package com.bless.fresco.widget;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.support.annotation.DrawableRes;
//import android.util.AttributeSet;
//
//import com.bless.fresco.core.DefaultConfigCentre;
//import com.bless.fresco.core.Priority;
//import com.bless.fresco.request.callback.FetchCallback;
//import com.bless.fresco.request.impl.FrescoFetcher;
//import com.bless.fresco.util.UriUtil;
//import com.bless.fresco.widget.photoview.PhotoView;
//import com.facebook.drawee.drawable.ScalingUtils;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.RoundingParams;
//import com.facebook.imagepipeline.image.ImageInfo;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.Postprocessor;
//
///**
// * <pre>
// *
// * 作者:      ASLai(gdcpljh@126.com).
// * 日期:      17-7-12
// * 版本:      V1.0
// * 描述:      继承缩放控件 PhotoView 的 FrescoZoomImageView; 使用方法参照{@link FrescoImageView}
// *
// * </pre>
// */
//public class FrescoZoomImageView extends PhotoView {
//
//    private int fadeDuration;
//    private Drawable failureDrawable;
//    private Drawable defaultDrawable;
//    private Drawable overlayDrawable;
//    private Drawable progressDrawable;
//    private Drawable retryDrawable;
//    private Drawable pressedDrawable;
//    private int resizeWidth;
//    private int resizeHeight;
//    private ScalingUtils.ScaleType scaleType;
//    private float radius;
//    private Priority requestPriority;
//    private boolean autoRotateEnabled = DefaultConfigCentre.DEFAULT_AUTO_ROTATE;
//    private ImageRequest.RequestLevel requestLevel;
//    private Postprocessor postprocessor;
//    private boolean isAutoPlayAnimations = true;
//    private boolean isRetainImageOnFailure = true;
//    private boolean isTapToRetryEnabled = true;
//
//    public FrescoZoomImageView(Context context) {
//        super(context);
//    }
//
//    public FrescoZoomImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public FrescoZoomImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    public FrescoZoomImageView(Context context, GenericDraweeHierarchy hierarchy) {
//        super(context, hierarchy);
//    }
//
//    public void setAutoRotateEnabled(boolean autoRotateEnabled) {
//        this.autoRotateEnabled = autoRotateEnabled;
//    }
//
//    public void setPlaceholderImage(Drawable defaultDrawable) {
//        this.defaultDrawable = defaultDrawable;
//        this.getHierarchy().setPlaceholderImage(defaultDrawable);
//    }
//
//    public void setFadeDuration(int fadeDuration) {
//        this.fadeDuration = fadeDuration;
//    }
//
//    public void setFailureDrawable(Drawable failureDrawable) {
//        this.failureDrawable = failureDrawable;
//        this.getHierarchy().setFailureImage(failureDrawable);
//    }
//
//    public void setAutoPlayAnimations(boolean autoPlayAnimations) {
//        isAutoPlayAnimations = autoPlayAnimations;
//    }
//
//    public void setRetainImageOnFailure(boolean retainImageOnFailure) {
//        isRetainImageOnFailure = retainImageOnFailure;
//    }
//
//    public void setTapToRetryEnabled(boolean tapToRetryEnabled) {
//        isTapToRetryEnabled = tapToRetryEnabled;
//    }
//
//    public void setOverlayDrawable(Drawable overlayDrawable) {
//        this.overlayDrawable = overlayDrawable;
//    }
//
//    public void setPostprocessor(Postprocessor postprocessor) {
//        this.postprocessor = postprocessor;
//    }
//
//    public void setPressedDrawable(Drawable pressedDrawable) {
//        this.pressedDrawable = pressedDrawable;
//    }
//
//    public void setProgressDrawable(Drawable progressDrawable) {
//        this.progressDrawable = progressDrawable;
//    }
//
//    public void setRadius(float radius) {
//        this.radius = radius;
//    }
//
//    public void setRequestLevel(ImageRequest.RequestLevel requestLevel) {
//        this.requestLevel = requestLevel;
//    }
//
//    public void setRequestPriority(Priority requestPriority) {
//        this.requestPriority = requestPriority;
//    }
//
//    public void setResizeHeight(int resizeHeight) {
//        this.resizeHeight = resizeHeight;
//    }
//
//    public void setResizeWidth(int resizeWidth) {
//        this.resizeWidth = resizeWidth;
//    }
//
//    public void setRetryDrawable(Drawable retryDrawable) {
//        this.retryDrawable = retryDrawable;
//    }
//
//    public void setScaleType(ScalingUtils.ScaleType scaleType) {
//        this.scaleType = scaleType;
//    }
//
//    public void asCircle() {
//        setRoundingParmas(getRoundingParams().setRoundAsCircle(true));
//    }
//
//    public void setBorder(int color, float width) {
//        setRoundingParmas(getRoundingParams().setBorder(color, width));
//    }
//
//    public void clearRoundingParams() {
//        setRoundingParmas(null);
//    }
//
//    private RoundingParams getRoundingParams() {
//        RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
//        if(roundingParams == null){
//            roundingParams = new RoundingParams();
//        }
//        return roundingParams;
//    }
//
//    public void setRoundingParmas(RoundingParams roundingParmas) {
//        this.getHierarchy().setRoundingParams(roundingParmas);
//    }
//
//    public void setCircle(int overlay_color) {
//        setRoundingParmas(getRoundingParams().setRoundAsCircle(true).
//                setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR).
//                setOverlayColor(overlay_color));
//    }
//
//    public void setCornerRadius(float radius) {
//        setRoundingParmas(getRoundingParams().setCornersRadius(radius));
//    }
//
//    public void setCornerRadius(float radius, int overlay_color) {
//        setRoundingParmas(getRoundingParams().setCornersRadius(radius).
//                setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR).
//                setOverlayColor(overlay_color));
//    }
//
//    public final void showImage(@DrawableRes int resId) {
//        showImage(UriUtil.parseUriFromResId(resId));
//    }
//
//    public final void showImage(String path) {
//        showImage(UriUtil.parseUri(UriUtil.wrap(path)));
//    }
//
//    public final void showImage(Uri uri) {
//        showImage(uri, null);
//    }
//
//    public final void showImage(@DrawableRes int resId, FetchCallback<ImageInfo> callback) {
//        showImage(UriUtil.parseUriFromResId(resId), callback);
//    }
//
//    public final void showImage(String path, FetchCallback<ImageInfo> callback) {
//        showImage(UriUtil.parseUri(UriUtil.wrap(path)), callback);
//    }
//
//    public final void showImage(Uri uri, FetchCallback<ImageInfo> callback) {
//        getFrescoFetcher().fetch(this, uri, callback);
//    }
//
//
//    private FrescoFetcher getFrescoFetcher() {
//        FrescoFetcher frescoFetcher = FrescoFetcher.newFetcher()
//                .withAutoRotateEnabled(autoRotateEnabled)
//                .withFadeDuration(fadeDuration)
//                .withFailureDrawable(failureDrawable)
//                .withDefaultDrawable(defaultDrawable)
//                .withOverlayDrawable(overlayDrawable)
//                .withProgressBarImage(progressDrawable)
//                .withRetryDrawable(retryDrawable)
//                .withPressedDrawable(pressedDrawable)
//                .withResizeWidth(resizeWidth)
//                .withResizeHeight(resizeHeight)
//                .withScaleType(scaleType)
//                .withRadius(radius)
//                .withRequestPriority(requestPriority)
//                .withRequestLevel(requestLevel)
//                .withPostprocessor(postprocessor)
//                .setAutoPlayAnimations(isAutoPlayAnimations)
//                .setRetainImageOnFailure(isRetainImageOnFailure)
//                .setTapToRetryEnabled(isTapToRetryEnabled)
//                .build();
//        return frescoFetcher;
//    }
//}
