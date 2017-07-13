package com.bless.fresco.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;

import com.bless.fresco.widget.html.getter.FrescoImageGetter;
import com.bless.fresco.widget.html.HtmlTagHandler;
import com.bless.fresco.widget.html.HtmlTextView;
import com.bless.fresco.widget.html.LocalLinkMovementMethod;
import com.bless.fresco.widget.html.span.ClickableImageSpan;
import com.bless.fresco.widget.html.span.ClickableURLSpan;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      支持HTML格式的TextView
 *
 * </pre>
 */
public class FrescoHtmlView extends HtmlTextView {

    /**
     * The constant TAG.
     */
    public static final String TAG = FrescoHtmlView.class.getSimpleName();

    private FrescoImageGetter.Type mType = FrescoImageGetter.Type.AUTO;
    private String mBaseUrl;
    private Drawable mPlaceHolder; // 占位图
    private Drawable mErrorImage; // 错误图
    private int mImageWidth = 200;
    private int mImageHeight = 200;
    private ClickableImageSpan mClickableImageSpan; //图片点击回调
    private ClickableURLSpan mClickableURLSpan; // 超链接点击回调

    /**
     * Instantiates a new Fresco html view.
     *
     * @param context the context
     */
    public FrescoHtmlView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Fresco html view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public FrescoHtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Instantiates a new Fresco html view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public FrescoHtmlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets image type.
     *
     * @param type the type
     */
    public void setImageType(FrescoImageGetter.Type type) {
        this.mType = type;
    }

    /**
     * Sets base url.
     *
     * @param baseUrl the base url
     */
    public void setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    /**
     * Sets error image.
     *
     * @param errorImage the error image
     */
    public void setErrorImage(Drawable errorImage) {
        this.mErrorImage = errorImage;
    }

    /**
     * Sets image height.
     *
     * @param imageHeight the image height
     */
    public void setImageHeight(int imageHeight) {
        this.mImageHeight = imageHeight;
    }

    /**
     * Sets image width.
     *
     * @param imageWidth the image width
     */
    public void setImageWidth(int imageWidth) {
        this.mImageWidth = imageWidth;
    }

    /**
     * Sets place holder.
     *
     * @param placeHolder the place holder
     */
    public void setPlaceHolder(Drawable placeHolder) {
        this.mPlaceHolder = placeHolder;
    }

    /**
     * Sets clickable image span.
     *
     * @param clickableImageSpan the clickable image span
     */
    public void setClickableImageSpan(ClickableImageSpan clickableImageSpan) {
        this.mClickableImageSpan = clickableImageSpan;
    }

    /**
     * Sets clickable url span.
     *
     * @param clickableURLSpan the clickable url span
     */
    public void setClickableURLSpan(ClickableURLSpan clickableURLSpan) {
        this.mClickableURLSpan = clickableURLSpan;
    }


    /**
     * Parses String containing HTML to Android's Spannable format and displays it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html) {
        setHtmlFromString(html, this.mBaseUrl);
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays it in this TextView.
     *
     * @param html    String containing HTML, for example: "<b>Hello world!</b>"
     * @param baseUrl String url, for example: "http://www.google.com"
     */
    public void setHtmlFromString(final String html, String baseUrl) {
        setBaseUrl(baseUrl);

        final FrescoImageGetter.Builder builder = FrescoImageGetter.newBuilder(this);
        builder.setType(mType);
        builder.setBaseUri(mBaseUrl);
        builder.setImageWidth(mImageWidth);
        builder.setImageHeight(mImageHeight);
        builder.setPlaceHolder(mPlaceHolder);
        builder.setErrorImage(mErrorImage);

        post(new Runnable() {
            @Override
            public void run() {
                try {
                    FrescoImageGetter htmlImageGetter = builder.build();

                    // this uses Android's Html class for basic parsing, and HtmlTagHandler
                    final HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
                    htmlTagHandler.setClickableTableSpan(mClickableTableSpan);
                    htmlTagHandler.setDrawTableLinkSpan(mDrawTableLinkSpan);
                    htmlTagHandler.setClickableImageSpan(mClickableImageSpan);
                    htmlTagHandler.setClickableURLSpan(mClickableURLSpan);

                    if (removeFromHtmlSpace) {
                        setText(removeHtmlBottomPadding(Html.fromHtml(html, htmlImageGetter, htmlTagHandler)));
                    } else {
                        setText(Html.fromHtml(html, htmlImageGetter, htmlTagHandler));
                    }

                    // make links work
                    setMovementMethod(LocalLinkMovementMethod.getInstance());
                } catch (Exception e) {

                }
            }
        });
    }

}
