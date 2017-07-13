package com.bless.fresco.widget.html.span;

import android.text.style.ClickableSpan;
import android.view.View;

import java.util.List;

/**
 * Created by ASLai on 2016/4/27.
 */
public abstract class ClickableImageSpan extends ClickableSpan{

    private List<String> imageUrls;

    private int position;

    public abstract ClickableImageSpan newInstance();

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View widget) {
        onImageClicked(imageUrls, position);
    }

    public abstract void onImageClicked(List<String> imageUrls, int position);
}
