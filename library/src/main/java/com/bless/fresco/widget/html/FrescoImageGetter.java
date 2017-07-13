package com.bless.fresco.widget.html;//package com.duolia.fresco.widget.html;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.drawable.Animatable;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.text.Html;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.duolia.fresco.R;
//import com.facebook.common.executors.UiThreadImmediateExecutorService;
//import com.facebook.common.references.CloseableReference;
//import com.facebook.datasource.BaseDataSubscriber;
//import com.facebook.datasource.DataSource;
//import com.facebook.datasource.DataSubscriber;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.controller.BaseControllerListener;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.DraweeHolder;
//import com.facebook.drawee.view.MultiDraweeHolder;
//import com.facebook.imagepipeline.common.ResizeOptions;
//import com.facebook.imagepipeline.core.ImagePipeline;
//import com.facebook.imagepipeline.image.CloseableBitmap;
//import com.facebook.imagepipeline.image.CloseableImage;
//import com.facebook.imagepipeline.image.ImageInfo;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;
//
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URI;
//
///**
// * Created by ASLai on 2016/4/23.
// */
//public class FrescoImageGetter implements Html.ImageGetter {
//
//    TextView container;
//    private Context mContext;
//    URI baseUri;
//
//    private Drawable placeHolder, errorImage;//占位图，错误图
//    private int d_w = 200;
//    private int d_h = 200;
//
//    private URLDrawable urlDrawable;
//
//    public FrescoImageGetter(TextView view) {
//        this.container = view;
//    }
//
//    public void init(Context context, AttributeSet attrs) {
//        mContext = context;
//
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RichTextView);
//        placeHolder = typedArray.getDrawable(R.styleable.RichTextView_placeHolderImage);
//        errorImage = typedArray.getDrawable(R.styleable.RichTextView_errorImage);
//
//        d_w = typedArray.getDimensionPixelSize(R.styleable.RichTextView_img_default_width, d_w);
//        d_h = typedArray.getDimensionPixelSize(R.styleable.RichTextView_img_default_height, d_h);
//
//        if (placeHolder == null) {
//            placeHolder = new ColorDrawable(Color.GRAY);
//        }
//        placeHolder.setBounds(0, 0, d_w, d_h);
//        if (errorImage == null) {
//            errorImage = new ColorDrawable(Color.GRAY);
//        }
//        errorImage.setBounds(0, 0, d_w, d_h);
//        typedArray.recycle();
//    }
//
//    public void setBaseUrl(String baseUrl) {
//        if (!TextUtils.isEmpty(baseUrl)) {
//            this.baseUri = URI.create(baseUrl);
//        }
//    }
//
//    public void setDataSubscriber(Context context, Uri uri/*, int width, int height*/) {
//        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableBitmap>>() {
//
//
//            @Override
//            public void onNewResultImpl(
//                    DataSource<CloseableReference<CloseableBitmap>> dataSource) {
//                if (!dataSource.isFinished()) {
//                    return;
//                }
//                CloseableReference<CloseableBitmap> imageReference = dataSource.getResult();
//                if (imageReference != null) {
//                    final CloseableReference<CloseableBitmap> closeableReference = imageReference.clone();
//                    try {
//                        CloseableBitmap closeableBitmap = closeableReference.get();
//                        Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();
//                        if (bitmap != null && !bitmap.isRecycled()) {
//                            //you can use bitmap here
//
////                            urlDrawable.setDrawable(new BitmapDrawable(mContext.getResources(), bitmap));
//                            Log.i("fresco", "onNewResultImpl");
//
//                            Drawable result = new BitmapDrawable(mContext.getResources(), bitmap);
//
//
//                            int paddingLeft = container.getPaddingLeft();
//                            int paddingRight = container.getPaddingRight();
//                            int drawableWidth = bitmap.getWidth();
//                            int drawableHeight = bitmap.getHeight();
//
//                            DisplayMetrics dm = container.getResources().getDisplayMetrics();
////                            int showWidth = dm.widthPixels - (paddingLeft + paddingRight);
//                            int showWidth =  container.getWidth() - (paddingLeft + paddingRight);
//                            int showHeidth = (int) (((float) showWidth / (float) drawableWidth) * drawableHeight);
//
//                            if (drawableWidth > showWidth / 2) {
//                                result.setBounds(0, 0, showWidth, showHeidth);
////                                urlDrawable.setBounds(0, 0, showWidth, showHeidth);
//                            } else {
//                                // 原始大小
//                                result.setBounds(0, 0, drawableWidth, drawableHeight);
////                                urlDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
//                            }
//
//                            urlDrawable.setBounds(result.getBounds());
//                            urlDrawable.setDrawable(result);
//
//                            setText();
////                            container.invalidate();
//                        }
//                    } finally {
//                        imageReference.close();
//                        closeableReference.close();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailureImpl(DataSource dataSource) {
//                Throwable throwable = dataSource.getFailureCause();
//                // handle failure
//                urlDrawable.setBounds(errorImage.getBounds());
//                urlDrawable.setDrawable(errorImage);
//                setText();
//            }
//        };
//
//        urlDrawable.setBounds(placeHolder.getBounds());
//        urlDrawable.setDrawable(placeHolder);
//        setText();
//
//        getBitmap(context, uri, /*width, height, */dataSubscriber);
//    }
//
//    /**
//     * @param context
//     * @param uri            //     * @param width
//     *                       //     * @param height
//     * @param dataSubscriber
//     */
//    public void getBitmap(Context context, Uri uri/*, int width, int height*/, DataSubscriber dataSubscriber) {
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
////        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
////        if(width > 0 && height > 0){
////            builder.setResizeOptions(new ResizeOptions(width, height));
////        }
//        // 使用ImageRequest 是为了避免本地图片加载时变小
//        ImageRequest request = ImageRequestBuilder
//                .newBuilderWithSource(uri)
//                .setResizeOptions(new ResizeOptions(1024, 768))
//                .setLocalThumbnailPreviewsEnabled(true)
//                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
//                .setProgressiveRenderingEnabled(true)
//                .build();
//
//        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, context);
////        dataSource.subscribe(dataSubscriber, UiThreadExecutorService.getInstance());
//        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
//    }
//
//    private static final class URLDrawable extends BitmapDrawable {
//        private Drawable drawable;
//
//        /**
//         * Create a drawable by decoding a bitmap from the given input stream.
//         *
//         * @param res
//         * @param is
//         */
//        public URLDrawable(Resources res, InputStream is) {
//            super(res, is);
//        }
//
//        /**
//         * Create a drawable by opening a given file path and decoding the bitmap.
//         *
//         * @param res
//         * @param filepath
//         */
//        public URLDrawable(Resources res, String filepath) {
//            super(res, filepath);
//            drawable = new BitmapDrawable(res, filepath);
//        }
//
//        /**
//         * Create drawable from a bitmap, setting initial target density based on
//         * the display metrics of the resources.
//         *
//         * @param res
//         * @param bitmap
//         */
//        public URLDrawable(Resources res, Bitmap bitmap) {
//            super(res, bitmap);
//        }
//
//        @Override
//        public void draw(Canvas canvas) {
//            if (drawable != null)
//                drawable.draw(canvas);
//        }
//
//        public void setDrawable(Drawable drawable) {
//            this.drawable = drawable;
//        }
//
//        public Drawable getDrawable() {
//            return drawable;
//        }
//    }
//
//    public void setPlaceHolder(Drawable placeHolder) {
//        this.placeHolder = placeHolder;
//        this.placeHolder.setBounds(0, 0, d_w, d_h);
//    }
//
//    public void setErrorImage(Drawable errorImage) {
//        this.errorImage = errorImage;
//        this.errorImage.setBounds(0, 0, d_w, d_h);
//    }
//
//    @Override
//    public Drawable getDrawable(String source) {
//        urlDrawable = new URLDrawable(mContext.getResources(), source);
//
//        if (source != null) {
//            if (!Uri.parse(source).isAbsolute())
//            {
//                if (baseUri != null && !TextUtils.isEmpty(baseUri.toString()))
//                {
//                    try {
//                        source = baseUri.resolve(source).toURL().getPath();
//                    } catch (MalformedURLException e) {
//                        source = baseUri + source;
//                    }
//                } else {
//                    urlDrawable.setBounds(errorImage.getBounds());
//                    urlDrawable.setDrawable(errorImage);
//                    return urlDrawable;
//                }
//            }
//        }
//
//        setDataSubscriber(mContext, Uri.parse(source));
//        return urlDrawable;
//    }
//
////    @Override
////    public Drawable getDrawable(String source) {
////        if (source != null) {
////            if (!Uri.parse(source).isAbsolute() && baseUri != null && !TextUtils.isEmpty(baseUri.toString())) {
////                try {
////                    source = baseUri.resolve(source).toURL().getPath();
////                } catch (MalformedURLException e) {
////                    source = baseUri + source;
////                }
////
////            } else {
////                URLDrawable urlDrawable = new URLDrawable();
////                urlDrawable.setBounds(errorImage.getBounds());
////                urlDrawable.setDrawable(errorImage);
////                return urlDrawable;
////            }
////        }
////
////        final URLDrawable urlDrawable = new URLDrawable();
////        GenericDraweeHierarchy mHierarchy = new GenericDraweeHierarchyBuilder(container.getResources()).build();
////
////        final DraweeHolder draweeHolder = new DraweeHolder<GenericDraweeHierarchy>(mHierarchy);
////
////
////        // 使用ImageRequest 是为了避免本地图片加载时变小
////        ImageRequest request = ImageRequestBuilder
////                .newBuilderWithSource(Uri.parse(source))
////                .setResizeOptions(new ResizeOptions(1024, 768))
////                .setLocalThumbnailPreviewsEnabled(true)
////                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
////                .setProgressiveRenderingEnabled(true)
////                .build();
////
////        DraweeController controller = Fresco.newDraweeControllerBuilder()
//////                    .setUri(Uri.parse(source))
////                .setImageRequest(request)
////                .setTapToRetryEnabled(true)
////                .setOldController(draweeHolder.getController())
////                .setControllerListener(new BaseControllerListener<ImageInfo>() {
////
////                    @Override
////                    public void onSubmit(String id, Object callerContext) {
////                        urlDrawable.setBounds(placeHolder.getBounds());
////                        urlDrawable.setDrawable(placeHolder);
////                        setText();
////                    }
////
////                    @Override
////                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
////                        final Drawable drawable = draweeHolder.getHierarchy().getTopLevelDrawable();
////
////                        int paddingLeft = container.getPaddingLeft();
////                        int paddingRight = container.getPaddingRight();
////                        int drawableWidth = imageInfo.getWidth();
////                        int drawableHeight = imageInfo.getHeight();
////
////                        DisplayMetrics dm = container.getResources().getDisplayMetrics();
//////                            int showWidth = dm.widthPixels - (paddingLeft + paddingRight);
////                        int showWidth =  container.getWidth() - (paddingLeft + paddingRight);
////                        int showHeidth = (int) (((float) showWidth / (float) drawableWidth) * drawableHeight);
////
////                        if (drawableWidth > showWidth / 2) {
////                            drawable.setBounds(0, 0, showWidth, showHeidth);
////                            urlDrawable.setBounds(0, 0, showWidth, showHeidth);
////                        } else {
////                            // 原始大小
////                            drawable.setBounds(0, 0, drawableWidth, drawableHeight);
////                            urlDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
////                        }
////                        urlDrawable.setDrawable(drawable);
////                        setText();
////                        container.invalidate();
////                    }
////
////                    @Override
////                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
////                        super.onIntermediateImageSet(id, imageInfo);
////                    }
////
////                    @Override
////                    public void onFailure(String id, Throwable throwable) {
////                        urlDrawable.setBounds(errorImage.getBounds());
////                        urlDrawable.setDrawable(errorImage);
////                        setText();
////                    }
////                })
////                .build();
////        draweeHolder.setController(controller);
////
////        return urlDrawable;
////    }
//
//    private void setText() {
//        if (container != null && container instanceof TextView)
//        {
//            CharSequence text = ((TextView) container).getText();
//            ((TextView) container).setText(text);
//        }
//    }
//}
