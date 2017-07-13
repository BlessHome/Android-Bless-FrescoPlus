package com.bless.fresco.core;


import android.support.annotation.Nullable;

/**
 * Priority levels recognized by the image pipeline.
 */
public enum FrescoPriority {
    /**
     * NOTE: DO NOT CHANGE ORDERING OF THOSE CONSTANTS UNDER ANY CIRCUMSTANCES.
     * Doing so will make ordering incorrect.
     */

    /**
     * Lowest priority level. Used for prefetches of non-visible images.
     */
    LOW,

    /**
     * Medium priority level. Used for warming of images that might soon get visible.
     */
    MEDIUM,

    /**
     * Highest priority level. Used for images that are currently visible on screen.
     */
    HIGH;

    /**
     * Gets the higher priority among the two.
     * @param priority1
     * @param priority2
     * @return higher priority
     */
    public static FrescoPriority getHigherPriority(
            @Nullable FrescoPriority priority1,
            @Nullable FrescoPriority priority2) {
        if (priority1 == null) {
            return priority2;
        }
        if (priority2 == null) {
            return priority1;
        }
        if (priority1.ordinal() > priority2.ordinal()) {
            return priority1;
        } else {
            return priority2;
        }
    }

}
