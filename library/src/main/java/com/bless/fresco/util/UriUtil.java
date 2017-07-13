package com.bless.fresco.util;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.bless.fresco.core.Scheme;
import com.bless.fresco.exception.FRuntimeException;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      URI 工具
 *
 * </pre>
 */
public class UriUtil {

    /**
     * 检查 uri 是否为网络资源
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "http" or "https"
     */
    public static boolean isNetworkUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return Scheme.HTTPS.equals(scheme) || Scheme.HTTP.equals(scheme);
    }

    /**
     * 检查 uri 是否为本地资源
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "file"
     */
    public static boolean isLocalFileUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return Scheme.FILE.equals(scheme);
    }

    /**
     * 检查 uri 是否为本地“Content”资源
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "content"
     */
    public static boolean isLocalContentUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return Scheme.CONTENT.equals(scheme);
    }

    /**
     * 检查 uri 是否为本地“Asset”资源
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "asset"
     */
    public static boolean isLocalAssetUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return Scheme.ASSET.equals(scheme);
    }

    /**
     * 检查 uri 是否为本地“Resource”资源
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "res"
     */
    public static boolean isLocalResourceUri(Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return Scheme.RES.equals(scheme);
    }

    /**
     * 检查 uri 是否为“data”资源
     *
     * @param uri the uri
     * @return the boolean
     */
    public static boolean isDataUri(Uri uri) {
        return Scheme.DATA.equals(getSchemeOrNull(uri));
    }

    /**
     * Parse uri from res id uri.
     *
     * @param resId the res id
     * @return the uri
     */
    public static Uri parseUriFromResId(@DrawableRes int resId){
        return new Uri.Builder()
                .scheme(Scheme.RES.getScheme())
                .path(String.valueOf(resId))
                .build();
    }

    /**
     * Gets scheme or null.
     *
     * @param uri uri to extract scheme from, possibly null
     * @return null if uri is null, result of uri.getScheme() otherwise
     */
    public static String getSchemeOrNull(Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    /**
     * Parse uri uri.
     *
     * @param uriAsString the uri as a string
     * @return the parsed Uri or null if the input was null
     */
    public static Uri parseUri(String uriAsString) {
        Uri uri;
        try {
            uri = Uri.parse(uriAsString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FRuntimeException("uriPath is null",e.getCause());
        }
        return uri;
    }

    /**
     * 使用绝对路径
     *
     * @param path the path
     * @return 绝对路径 ，并且带上该URI的{@link Scheme}
     */
    public static String wrap(String path) {

        Scheme scheme = Scheme.UNKNOWN;
        try {
            String schemeStr = UriUtil.getSchemeOrNull(Uri.parse(path));
            scheme = Scheme.parseScheme(schemeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (scheme == Scheme.UNKNOWN || scheme == Scheme.DATA) {
            path = Scheme.FILE.wrap(path);
        }
        return path;
    }

    /**
     * 使用绝对路径
     *
     * @param assets the assets
     * @return 绝对路径 ，并且带上该URI的{@link Scheme}
     */
    public static String wrapAssets(String assets) {
        if (!assets.startsWith(Scheme.ASSET.getPrefix()))
        {
            assets = Scheme.ASSET.wrap(assets);
        }
        return assets;
    }

    /**
     * 使用绝对路径
     * <p>
     * resId : "/R.drawable.resId"
     *
     * @param resId the res id
     * @return 绝对路径 ，并且带上该URI的{@link Scheme}
     */
    public static String wrapRes(int resId) {
        return Scheme.RES.wrap("/" + resId);
    }
}
