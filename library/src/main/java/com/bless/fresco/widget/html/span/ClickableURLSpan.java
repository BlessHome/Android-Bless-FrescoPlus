package com.bless.fresco.widget.html.span;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by ASLai on 2016/4/27.
 */
public abstract class ClickableURLSpan extends ClickableSpan {

    public abstract ClickableURLSpan newInstance();

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void onClick(View widget) {
        onLinkClicked(url);
    }

    public abstract void onLinkClicked(String url);
}
