package com.bless.fresco.widget;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.bless.fresco.core.DefaultConfigCentre;
import com.bless.fresco.core.FrescoPriority;
import com.bless.fresco.request.callback.FetchCallback;
import com.bless.fresco.request.impl.FrescoFetcher;
import com.bless.fresco.util.UriUtil;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      用于替代系统自带ImageView的FrescoImageView
 *
 * </pre>
 */
public class FrescoImageView extends SimpleDraweeView {

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

    /**
     * Instantiates a new Fresco image view.
     *
     * @param context the context
     */
    public FrescoImageView(Context context) {
        super(context);
    }

    public FrescoImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }
    /**
     * Instantiates a new Fresco image view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public FrescoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Fresco image view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public FrescoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrescoImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置启用自动旋转
     *
     * @param autoRotateEnabled the auto rotate enabled
     */
    public void setAutoRotateEnabled(boolean autoRotateEnabled) {
        this.autoRotateEnabled = autoRotateEnabled;
    }

    /**
     * 设置加载占位图
     *
     * @param defaultDrawable the default drawable
     */
    public void setPlaceholderImage(Drawable defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
        this.getHierarchy().setPlaceholderImage(defaultDrawable);
    }

    /**
     * 设置消失时间
     *
     * @param fadeDuration the fade duration
     */
    public void setFadeDuration(int fadeDuration) {
        this.fadeDuration = fadeDuration;
    }

    /**
     * 设置加载失败占位图
     *
     * @param failureDrawable the failure drawable
     */
    public void setFailureImage(Drawable failureDrawable) {
        this.failureDrawable = failureDrawable;
        this.getHierarchy().setFailureImage(failureDrawable);
    }

    /**
     * 设置自动播放动画
     *
     * @param autoPlayAnimations the auto play animations
     */
    public void setAutoPlayAnimations(boolean autoPlayAnimations) {
        isAutoPlayAnimations = autoPlayAnimations;
    }

    /**
     * 设置失败时是否保留图像
     *
     * @param retainImageOnFailure the retain image on failure
     */
    public void setRetainImageOnFailure(boolean retainImageOnFailure) {
        isRetainImageOnFailure = retainImageOnFailure;
    }

    /**
     * 设置点击重新加载图
     *
     * @param tapToRetryEnabled the tap to retry enabled
     */
    public void setTapToRetryEnabled(boolean tapToRetryEnabled) {
        isTapToRetryEnabled = tapToRetryEnabled;
    }

    /**
     * 设置叠加图
     *
     * @param overlayDrawable the overlay drawable
     */
    public void setOverlayDrawable(Drawable overlayDrawable) {
        this.overlayDrawable = overlayDrawable;
    }

    /**
     * 设置后处理器
     *
     * @param postprocessor the postprocessor
     */
    public void setPostprocessor(Postprocessor postprocessor) {
        this.postprocessor = postprocessor;
    }

    /**
     * 设置按压状态下的叠加图
     *
     * @param pressedDrawable the pressed drawable
     */
    public void setPressedDrawable(Drawable pressedDrawable) {
        this.pressedDrawable = pressedDrawable;
    }

    /**
     * 设置一个进度条图片
     *
     * @param progressDrawable the progress drawable
     */
    public void setProgressBarImage(Drawable progressDrawable) {
        this.progressBarDrawable = progressDrawable;
    }

    /**
     * 设置圆角半径
     *
     * @param radius the radius
     */
    public void setRoundedCornerRadius(float radius) {
        this.radius = radius;
    }

    /**
     * 设置请求级别
     *
     * @param requestLevel the request level
     */
    public void setRequestLevel(ImageRequest.RequestLevel requestLevel) {
        this.requestLevel = requestLevel;
    }

    /**
     * 设置请求的优先级
     *
     * @param requestPriority the request priority
     */
    public void setRequestPriority(FrescoPriority requestPriority) {
        this.requestPriority = requestPriority;
    }

    /**
     * 设置调整高度
     *
     * @param resizeHeight the resize height
     */
    public void setResizeHeight(int resizeHeight) {
        this.resizeHeight = resizeHeight;
    }

    /**
     * 设置调整宽度
     *
     * @param resizeWidth the resize width
     */
    public void setResizeWidth(int resizeWidth) {
        this.resizeWidth = resizeWidth;
    }

    /**
     * 设置重试图片
     *
     * @param retryDrawable the retry drawable
     */
    public void setRetryImage(Drawable retryDrawable) {
        this.retryDrawable = retryDrawable;
    }

    /**
     * 设置实际图像缩放类型
     *
     * @param scaleType the scale type
     */
    public void setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    /**
     * 设置为圆圈，默认为false
     */
    public void setRoundAsCircle(boolean roundAsCircle) {
        setRoundingParams(getRoundingParams().setRoundAsCircle(roundAsCircle));
    }

    /**
     * 设置边框
     *
     * @param color the color
     * @param width the width
     */
    public void setBorder(int color, float width) {
        setRoundingParams(getRoundingParams().setBorder(color, width));
    }

    /**
     * 清除圆形的参数
     */
    public void clearRoundingParams() {
        setRoundingParams(null);
    }

    private RoundingParams getRoundingParams() {
        RoundingParams roundingParams = this.getHierarchy().getRoundingParams();
        if (roundingParams == null) {
            roundingParams = new RoundingParams();
        }
        return roundingParams;
    }

    /**
     * 设置圆形参数
     *
     * @param roundingParams the rounding params
     */
    public void setRoundingParams(RoundingParams roundingParams) {
        this.getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * 设置圆圈覆盖的颜色
     *
     * @param overlayColor the overlay color
     */
    public void setRoundAsCircleOverlayColor(int overlayColor) {
        setRoundingParams(getRoundingParams().setRoundAsCircle(true).
                setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR).
                setOverlayColor(overlayColor));
    }

    /**
     * 设置圆角半径
     *
     * @param radius the radius
     */
    public void setCornerRadius(float radius) {
        setRoundingParams(getRoundingParams().setCornersRadius(radius));
    }

    /**
     * 设置圆角半径
     *
     * @param radius        the radius
     * @param overlay_color the overlay color
     */
    public void setCornerRadius(float radius, int overlay_color) {
        setRoundingParams(getRoundingParams().setCornersRadius(radius).
                setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR).
                setOverlayColor(overlay_color));
    }

    /**
     * 显示图像
     *
     * @param resId the res id
     */
    public final void showImage(@DrawableRes int resId) {
        showImage(UriUtil.parseUriFromResId(resId));
    }

    /**
     * 显示图像
     *
     * @param path the path
     */
    public final void showImage(String path) {
        showImage(UriUtil.parseUri(UriUtil.wrap(path)));
    }

    /**
     * 显示图像
     *
     * @param uri the uri
     */
    public final void showImage(Uri uri) {
        showImage(uri, null);
    }

    /**
     * 显示图像
     *
     * @param resId    the res id
     * @param callback the callback
     */
    public final void showImage(@DrawableRes int resId, FetchCallback<ImageInfo> callback) {
        showImage(UriUtil.parseUriFromResId(resId), callback);
    }

    /**
     * 显示图像
     *
     * @param path     the path
     * @param callback the callback
     */
    public final void showImage(String path, FetchCallback<ImageInfo> callback) {
        showImage(UriUtil.parseUri(UriUtil.wrap(path)), callback);
    }

    /**
     * 显示图像
     *
     * @param uri      the uri
     * @param callback the callback
     */
    public final void showImage(Uri uri, FetchCallback<ImageInfo> callback) {
        getFrescoFetcher().fetch(this, uri, callback);
    }


    private FrescoFetcher getFrescoFetcher() {
        FrescoFetcher frescoFetcher = FrescoFetcher.newFetcher()
                .withAutoRotateEnabled(autoRotateEnabled)
                .withFadeDuration(fadeDuration)
                .withFailureDrawable(failureDrawable)
                .withDefaultDrawable(defaultDrawable)
                .withOverlayDrawable(overlayDrawable)
                .withProgressBarImage(progressBarDrawable)
                .withRetryDrawable(retryDrawable)
                .withPressedDrawable(pressedDrawable)
                .withResizeWidth(resizeWidth)
                .withResizeHeight(resizeHeight)
                .withScaleType(scaleType)
                .withRadius(radius)
                .withRequestPriority(requestPriority)
                .withRequestLevel(requestLevel)
                .withPostprocessor(postprocessor)
                .setAutoPlayAnimations(isAutoPlayAnimations)
                .setRetainImageOnFailure(isRetainImageOnFailure)
                .setTapToRetryEnabled(isTapToRetryEnabled)
                .build();
        return frescoFetcher;
    }

}