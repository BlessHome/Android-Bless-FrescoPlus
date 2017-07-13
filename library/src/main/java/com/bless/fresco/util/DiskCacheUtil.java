package com.bless.fresco.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.bless.fresco.exception.FNullPointerException;

import java.io.File;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      磁盘缓存工具
 *
 * </pre>
 */
public class DiskCacheUtil {

    /**
     * 获取磁盘中本地缓存目录
     *
     * @param context the context
     * @return the disk lru cache dir
     */
    public static File getDiskLruCacheDir(Context context) {
        if (context == null)
            throw new FNullPointerException("context can not be null");
        if (!(context instanceof Application))
            context = context.getApplicationContext();
        File cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = getSDFreeSize() > 100 ? context.getExternalCacheDir() : context.getCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    /**
     * 获取SD 卡的大小
     *
     * @return the sd free size
     */

    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        if (path != null && path.exists() && path.isDirectory()) {
            StatFs sf = new StatFs(path.getPath());
            long blockSize = 0;
            long freeBlocks = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = sf.getBlockSizeLong();
                freeBlocks = sf.getAvailableBlocksLong();
            } else {
                blockSize = sf.getBlockSize();
                freeBlocks = sf.getAvailableBlocks();
            }

            return (freeBlocks * blockSize) / 1024 / 1024;
        }
        return -1;
    }
}
