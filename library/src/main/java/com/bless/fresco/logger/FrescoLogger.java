package com.bless.fresco.logger;


import com.bless.fresco.FrescoInitializer;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      17-7-12
 * 版本:      V1.0
 * 描述:      Fresco Logger
 *
 * </pre>
 */
public class FrescoLogger {
    private static volatile FrescoLogger instance;

    private FrescoLogger() {
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public static FrescoLogger getLogger() {
        if (instance == null) {
            synchronized (FrescoLogger.class) {
                if (instance == null) {
                    instance = new FrescoLogger();
                }
            }
        }
        return instance;
    }

    /**
     * Log.
     *
     * @param t the t
     */
    public void log(Object t) {
        boolean isDebug = FrescoInitializer.getInstance().isDebug();
        if (isDebug) {
            FrescoLogTracker.d(String.valueOf(t));
        }
    }

    /**
     * Log.
     *
     * @param tag the tag
     * @param t   the t
     */
    public void log(String tag, Object t) {
        boolean isDebug = FrescoInitializer.getInstance().isDebug();
        if (isDebug) {
            FrescoLogTracker.d(FrescoLogTracker.wrapTagIfNull(tag), String.valueOf(t));
        }
    }
}
