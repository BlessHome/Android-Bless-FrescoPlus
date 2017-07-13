# Android-Bless-FrescoPlus

## 概述
- 基于Fresco图片库的二次封装，只需2步就可以接入Fresco！

## 使用
### 1、在Application初始化FrescoPlus

```java

@Override
public void onCreate() {
    super.onCreate();
	...
    FrescoInitializer.getInstance().init(this);
    ...
}
```

或使用自定义的配置

```java

@Override
public void onCreate() {
    super.onCreate();
	...
    FrescoConfig frescoConfig = FrescoConfig.newBuilder(this)
            .setDebug(true)
            .setTag("FrescoPlus")
            .setBitmapConfig(Bitmap.Config.RGB_565)
            .setBaseDirectoryPath(this.getCacheDir())
            .setBaseDirectoryName("FrescoCache")
            .setMaxDiskCacheSize(80)
            .build();
    FrescoInitializer.getInstance().init(this, frescoConfig);
    ...
}
```

## 2、在xml布局中使用`FrescoImageView`作为显示图片的View

```xml
<com.bless.fresco.widget.FrescoImageView
    android:id="@+id/fresco_image_view"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"/>
```

## 3、在代码上加载图片

```java

FrescoImageView imageView = (FrescoImageView)findViewById(R.id.fresco_image_view);

// 加载drawable里的图片
imageView.showImage(R.drawable.imageView);

// 通过路径加载图片；支持：http、https、file、content、asset、res、data
imageView.showImage("scheme://imagePath");

// 加载图片并回调
imageView.showImage("scheme://imagePath", new FetchCallback<ImageInfo>() {
    @Override
    public void onSuccess(ImageInfo result) {
        // do something
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
});

// 下载图片
Uri uri = Uri.parse("http://pic130.nipic.com/file/20170524/4516668_143048292032_2.jpg");
// imageView.showImage(uri);
FrescoFetcher.newFetcher()
    .withFadeDuration(500)
    .withRequestPriority(FrescoPriority.HIGH)
    .withAutoRotateEnabled(true)
    .build()
    .fetch(uri, new LoadCallback<Bitmap>() {
        @Override
        public void onSuccess(Uri uri, Bitmap result) {
            // do something
        }

        @Override
        public void onFailure(Uri uri, Throwable throwable) {

        }

        @Override
        public void onCancel(Uri uri) {

	}
});
      
```

## 4、关于Fresco更多内容，请看[官方文档](https://www.fresco-cn.org/docs/ "官方文档")

- 官方文档: [https://www.fresco-cn.org/docs/](https://www.fresco-cn.org/docs/)