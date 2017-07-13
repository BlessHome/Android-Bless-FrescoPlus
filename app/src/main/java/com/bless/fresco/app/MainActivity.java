package com.bless.fresco.app;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bless.fresco.core.FrescoPriority;
import com.bless.fresco.request.callback.LoadCallback;
import com.bless.fresco.request.impl.FrescoFetcher;
import com.bless.fresco.widget.photoview.PhotoView;

public class MainActivity extends AppCompatActivity {

    PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (PhotoView) findViewById(R.id.fresco_image_view);

        imageView.showImage(R.mipmap.ic_launcher);

//        imageView.showImage("http://pic130.nipic.com/file/20170524/4516668_143048292032_2.jpg");
        Uri uri = Uri.parse("http://pic130.nipic.com/file/20170524/4516668_143048292032_2.jpg");
//        imageView.showImage(uri);
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
    }
}
