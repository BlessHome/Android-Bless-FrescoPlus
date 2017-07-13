package com.bless.fresco.core;


import java.util.Locale;

public enum Scheme {

    HTTP("http"),
    HTTPS("https"),
    FILE("file"),
    CONTENT("content"),
    ASSET("asset"),
    RES("res"),
    DATA("data"),
    UNKNOWN("");

    private String scheme;
    private String uriPrefix;

    Scheme(String scheme) {
        this.scheme = scheme;
        this.uriPrefix = (scheme + "://");
    }

    public static Scheme ofUri(String uri) {
        if (uri != null) {
            for (Scheme s : values()) {
                if (s.belongsTo(uri)) {
                    return s;
                }
            }
        }
        return UNKNOWN;
    }

    private boolean belongsTo(String uri) {
        return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
    }

    public String getScheme() {
        return scheme;
    }

    public String getPrefix() {
        return uriPrefix;
    }

    public String wrap(String path) {
        return this.uriPrefix + path;
    }

    public String crop(String uri) {
        if (!belongsTo(uri)) {
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", new Object[]{uri, this.scheme}));
        }
        return uri.substring(this.uriPrefix.length());
    }

    public boolean equals(String schemeStr) {
        return scheme.equalsIgnoreCase(schemeStr);
    }

    public static Scheme parseScheme(String schemeStr) {
        for (Scheme scheme : Scheme.values()) {
            if (scheme.scheme.equalsIgnoreCase(schemeStr)) {
                return scheme;
            }
        }

        return UNKNOWN;
    }

}
