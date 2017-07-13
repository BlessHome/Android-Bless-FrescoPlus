package com.bless.fresco.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bless.fresco.core.Scheme;
import com.bless.fresco.exception.FIllegalArgumentException;
import com.bless.fresco.exception.FNullPointerException;
import com.bless.fresco.exception.FRuntimeException;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      参数检查工具
 *
 * </pre>
 */
public final class ParamCheckUtil {

    private ParamCheckUtil() {
    }


    /**
     * 检查参数是否为空
     *
     * @param <T>       the type parameter
     * @param reference the reference
     * @return the t
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null)
            throw new FNullPointerException("The reference is null!");
        return reference;
    }

    /**
     * 检查参数是否为空，并提供错误信息
     *
     * @param <T>          the type parameter
     * @param reference    the reference
     * @param errorMessage the error message
     * @return the t
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new FNullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 检查URI 是否合法
     *
     * @param uri the uri
     */
    public static void checkUriIsLegal(Uri uri) {
        ParamCheckUtil.checkNotNull(uri, "uri is null");
        String schemeStr = UriUtil.getSchemeOrNull(uri);
        if (TextUtils.isEmpty(schemeStr))
            throw new FIllegalArgumentException("uri is illegal,cause:This scheme is null or empty");
        Scheme scheme = Scheme.parseScheme(schemeStr);
        switch (scheme) {
            case HTTP:
            case HTTPS:
            case FILE:
            case ASSET:
            case CONTENT:
            case RES:
            case DATA:
                break;
            default:
                throw new FIllegalArgumentException("uri is illegal,cause:This scheme not supported");
        }
        validate(uri);
    }

    private static void validate(Uri uri) {
        if (UriUtil.isLocalResourceUri(uri)) {
            if (!uri.isAbsolute()) {
                throw new FRuntimeException("Resource URI path must be absolute.");
            }
            if (uri.getPath().isEmpty()) {
                throw new FRuntimeException("Resource URI must not be empty");
            }
            try {
                Integer.parseInt(uri.getPath().substring(1));
            } catch (NumberFormatException ignored) {
                throw new FRuntimeException("Resource URI path must be a resource id.",ignored.getCause());
            }
        }
        if (UriUtil.isLocalAssetUri(uri) && !uri.isAbsolute()) {
            throw new FRuntimeException("Asset URI path must be absolute.");
        }
    }
}
