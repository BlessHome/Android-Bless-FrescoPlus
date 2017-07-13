package com.bless.fresco.widget.html.getter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Created by ASLai on 2016/4/23.
 */
public class FrescoImageGetter implements Html.ImageGetter {


    private Type mType = Type.AUTO;
    private TextView mTextView;
    private Context mContext;
    private URI mBaseUri;
    private Drawable mPlaceHolder; // 占位图
    private Drawable mErrorImage; // 错误图
    private int mImageWidth = 200;
    private int mImageHeight = 200;
    private URLDrawable mURLDrawable;

    private FrescoImageGetter(Builder builder) {
        mType = builder.mType;
        mTextView = builder.mTextView;
        mContext = builder.mContext;
        mBaseUri = builder.mBaseUri;
        mPlaceHolder = builder.mPlaceHolder;
        mErrorImage = builder.mErrorImage;
        mImageWidth = builder.mImageWidth;
        mImageHeight = builder.mImageHeight;

        init();
    }

    private void init() {
        if (mPlaceHolder == null) {
            mPlaceHolder = new ColorDrawable(Color.GRAY);
        }
        mPlaceHolder.setBounds(0, 0, mImageWidth, mImageHeight);
        if (mErrorImage == null) {
            mErrorImage = new ColorDrawable(Color.GRAY);
        }
        mErrorImage.setBounds(0, 0, mImageWidth, mImageHeight);
    }

    @Override
    public Drawable getDrawable(String source) {
        mURLDrawable = new URLDrawable(mContext.getResources(), source);

        if (source != null) {
            if (!Uri.parse(source).isAbsolute())
            {
                if (mBaseUri != null && !TextUtils.isEmpty(mBaseUri.toString()))
                {
                    try {
                        source = mBaseUri.resolve(source).toURL().getPath();
                    } catch (MalformedURLException e) {
                        source = mBaseUri + source;
                    }
                } else {
                    mURLDrawable.setBounds(mErrorImage.getBounds());
                    mURLDrawable.setDrawable(mErrorImage);
                    return mURLDrawable;
                }
            }
        }

        setDataSubscriber(mContext, Uri.parse(source));
        return mURLDrawable;
    }

    public void setDataSubscriber(Context context, Uri uri) {
        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableBitmap>>() {

            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableBitmap>> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }

                CloseableReference<CloseableBitmap> imageReference = dataSource.getResult();
                if (imageReference != null) {
                    final CloseableReference<CloseableBitmap> closeableReference = imageReference.clone();
                    try {
                        CloseableBitmap closeableBitmap = closeableReference.get();
                        Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            //you can use bitmap here
//                           mURLDrawable.setDrawable(new BitmapDrawable(mContext.getResources(), bitmap));

                            Drawable result = getResultDrawable(bitmap);

                            mURLDrawable.setBounds(result.getBounds());
                            mURLDrawable.setDrawable(result);

                            setText();
//                          container.invalidate();
                        }
                    } finally {
                        imageReference.close();
                        closeableReference.close();
                    }
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableBitmap>> dataSource) {
//                Throwable throwable = dataSource.getFailureCause();
                // handle failure
                mURLDrawable.setBounds(mErrorImage.getBounds());
                mURLDrawable.setDrawable(mErrorImage);
                setText();
            }
        };

        mURLDrawable.setBounds(mPlaceHolder.getBounds());
        mURLDrawable.setDrawable(mPlaceHolder);
        setText();

        getBitmap(context, uri, dataSubscriber);
    }

    /**
     * @param context
     * @param uri
     * @param dataSubscriber
     */
    public void getBitmap(Context context, Uri uri, DataSubscriber dataSubscriber) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        // 使用ImageRequest并设置ResizeOptions是为了避免本地图片加载时变小
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(1024, 768))
                .setLocalThumbnailPreviewsEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setProgressiveRenderingEnabled(true)
                .build();

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, context);
//      dataSource.subscribe(dataSubscriber, UiThreadExecutorService.getInstance());
        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }

    private Drawable getResultDrawable(Bitmap bitmap) {

        int paddingLeft = mTextView.getPaddingLeft();
        int paddingRight = mTextView.getPaddingRight();

        int bitmapWidth = bitmap.getWidth();
        int bitmapleHeight = bitmap.getHeight();

        int showWidth = mTextView.getWidth() - (paddingLeft + paddingRight);

        Drawable result = new BitmapDrawable(mContext.getResources(), bitmap);
        if (mType == Type.AUTO) {
//            DisplayMetrics dm = mTextView.getResources().getDisplayMetrics();

            int showHeidth = (int) (((float) showWidth / (float) bitmapWidth) * bitmapleHeight);


            if (bitmapWidth > showWidth / 2) {
                result.setBounds(0, 0, showWidth, showHeidth);
            } else {
                // 原始大小
                result.setBounds(0, 0, bitmapWidth, bitmapleHeight);
            }

        } else {

            int newWidth = bitmapWidth;
            int newHeight = bitmapleHeight;

            if (bitmapWidth > showWidth) {
                newWidth = showWidth;
                newHeight = (newWidth * bitmapleHeight) / bitmapWidth;
            }
            result.setBounds(0, 0, newWidth, newHeight);
        }

        return result;

    }

    private void setText() {
        if (mTextView != null && mTextView instanceof TextView) {
            CharSequence text = mTextView.getText();
            mTextView.setText(text);
        }
    }

    public static Builder newBuilder(TextView view) {
        return new Builder(view);
    }

    public static final class Builder {
        private Type mType = Type.AUTO;
        private TextView mTextView;
        private Context mContext;
        private URI mBaseUri;
        private Drawable mPlaceHolder; // 占位图
        private Drawable mErrorImage; // 错误图
        private int mImageWidth = 200;
        private int mImageHeight = 200;

        private Builder(TextView view) {
            mTextView = view;
            mContext = view.getContext();
        }

        public void setBaseUri(String baseUrl) {
            if (!TextUtils.isEmpty(baseUrl))
            {
                this.mBaseUri = URI.create(baseUrl);
            }
        }

        public void setErrorImage(Drawable errorImage) {
            this.mErrorImage = errorImage;
        }

        public void setImageHeight(int imageHeight) {
            this.mImageHeight = imageHeight;
        }

        public void setImageWidth(int imageWidth) {
            this.mImageWidth = imageWidth;
        }

        public void setPlaceHolder(Drawable placeHolder) {
            this.mPlaceHolder = placeHolder;
        }

        public void setType(Type type) {
            this.mType = type;
        }

        public FrescoImageGetter build() {
            return new FrescoImageGetter(this);
        }
    }

    private static final class URLDrawable extends BitmapDrawable {
        private Drawable drawable;

        @SuppressWarnings("deprecation")
        public URLDrawable() {
        }

        /**
         * Create a drawable by decoding a bitmap from the given input stream.
         *
         * @param res
         * @param is
         */
        public URLDrawable(Resources res, InputStream is) {
            super(res, is);
        }

        /**
         * Create a drawable by opening a given file path and decoding the bitmap.
         *
         * @param res
         * @param filepath
         */
        public URLDrawable(Resources res, String filepath) {
            super(res, filepath);
            drawable = new BitmapDrawable(res, filepath);
        }

        /**
         * Create drawable from a bitmap, setting initial target density based on
         * the display metrics of the resources.
         *
         * @param res
         * @param bitmap
         */
        public URLDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        @Override
        public void draw(Canvas canvas) {
            try {
                if (drawable != null)
                    drawable.draw(canvas);
            } catch (Exception e) {

            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        public Drawable getDrawable() {
            return drawable;
        }
    }

    public enum Type {
        AUTO,
        FIT;
    }

}
