package com.bless.fresco.logger;

import android.text.TextUtils;
import android.util.Log;

import com.bless.fresco.FrescoInitializer;


/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco 日志跟踪器
 *
 * </pre>
 */
public class FrescoLogTracker {

    /**
     * D.
     *
     * @param message the message
     */
    public static void d(String message) {
        Log.d(FrescoInitializer.getInstance().getLogTag(), packMessage(message));
    }

    /**
     * D.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void d(String tag, String message) {
        Log.d(wrapTagIfNull(tag), packMessage(message));
    }

    /**
     * V.
     *
     * @param message the message
     */
    public static void v(String message) {
        Log.v(FrescoInitializer.getInstance().getLogTag(), packMessage(message));
    }

    /**
     * V.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void v(String tag, String message) {
        Log.v(wrapTagIfNull(tag), packMessage(message));
    }

    /**
     * .
     *
     * @param message the message
     */
    public static void i(String message) {
        Log.i(FrescoInitializer.getInstance().getLogTag(), packMessage(message));
    }

    /**
     * .
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void i(String tag, String message) {
        Log.i(wrapTagIfNull(tag), packMessage(message));
    }

    /**
     * E.
     *
     * @param message the message
     */
    public static void e(String message) {
        Log.e(FrescoInitializer.getInstance().getLogTag(), packMessage(message));
    }

    /**
     * E.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void e(String tag, String message) {
        Log.e(wrapTagIfNull(tag), packMessage(message));
    }

    /**
     * W.
     *
     * @param message the message
     */
    public static void w(String message) {
        Log.w(FrescoInitializer.getInstance().getLogTag(), packMessage(message));
    }

    /**
     * W.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void w(String tag, String message) {
        Log.w(wrapTagIfNull(tag), packMessage(message));
    }

    /**
     * Wrap tag if null string.
     *
     * @param tag the tag
     * @return the string
     */
    public static String wrapTagIfNull(String tag) {
        return TextUtils.isEmpty(tag) ? FrescoInitializer.getInstance().getLogTag() : tag;
    }
    private static StackTraceElement generateSTE(Thread thread,int index){
        return thread.getStackTrace()[index];
    }
    private static String packMessage(String message) {
        StringBuilder builder = new StringBuilder();
        Thread thread = Thread.currentThread();
        StackTraceElement element = generateSTE(thread,6);
        if (element == null)
            return ""+message;
        String threadName = thread.getName();
        builder.append("ThreadName:")
                .append(threadName)
                .append("->")
                .append(element.getClassName())
                .append("->")
                .append(element.getMethodName())
                .append("[line:")
                .append(element.getLineNumber())
                .append("],message:")
                .append(message);
        return builder.toString();
    }
}

