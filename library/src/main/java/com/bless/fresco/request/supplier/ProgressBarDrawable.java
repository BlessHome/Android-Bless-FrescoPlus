package com.bless.fresco.request.supplier;

import android.graphics.Color;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Drawable that displays a progress bar based on the level.
 *
 * </pre>
 */
public class ProgressBarDrawable extends com.facebook.drawee.drawable.ProgressBarDrawable {
    private int mProgressBarColor = Color.parseColor("#FDD11A");
    private int mProgressBarPadding = 0;
    private int mProgressBarWidth = 15;

    public ProgressBarDrawable() {
        this(-1);
    }

    public ProgressBarDrawable(int progressBarColor) {
        setColor(progressBarColor == -1 ? mProgressBarColor : progressBarColor);
        setPadding(mProgressBarPadding);
        setBarWidth(mProgressBarWidth);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public void setPadding(int padding) {
        super.setPadding(padding);
    }

    @Override
    public void setBarWidth(int barWidth) {
        super.setBarWidth(barWidth);
    }

}
