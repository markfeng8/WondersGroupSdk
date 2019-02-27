/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.HashMap;

/**
 * Created by x-sir on 2019/2/27 :)
 * Function:HashMap initialize with expected size of capacity.
 */
public class Maps {

    /**
     * recommend default size.
     */
    private static final int DEFAULT_SIZE = 16;

    /**
     * only adapter to JDK 1.8
     */
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize() {
        return newHashMapWithExpectedSize(DEFAULT_SIZE);
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<K, V>(capacity(expectedSize));
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as long as it grows no
     * larger than expectedSize and the load factor is ≥ its default (0.75).
     */
    private static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            checkNonNegative(expectedSize);
            return expectedSize + 1;
        }

        if (expectedSize < 1073741824) {
            // This is the calculation used in JDK8 to resize when a putAll
            // happens; it seems to be the most conservative calculation we
            // can make.  0.75 is the default load factor.
            return (int) ((float) expectedSize / 0.75F + 1.0F);
        }

        return Integer.MAX_VALUE; // any large value
    }

    private static void checkNonNegative(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
        }
    }
}
