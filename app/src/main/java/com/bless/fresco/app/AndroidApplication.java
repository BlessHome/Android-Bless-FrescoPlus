package com.bless.fresco.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.bless.fresco.FrescoInitializer;
import com.bless.fresco.core.FrescoConfig;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      description
 *
 * </pre>
 */

public class AndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FrescoInitializer.getInstance().init(this);

        FrescoConfig frescoConfig = FrescoConfig.newBuilder(this)
                .setDebug(true)
                .setTag("FrescoPlus")
                .setBitmapConfig(Bitmap.Config.RGB_565)
                .setBaseDirectoryPath(this.getCacheDir())
                .setBaseDirectoryName("FrescoCache")
                .setMaxDiskCacheSize(80)
                .build();

        FrescoInitializer.getInstance().init(this, frescoConfig);
    }
}
