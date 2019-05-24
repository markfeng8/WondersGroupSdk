/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by x-sir on 2019/2/27 :)
 * Function:
 */
public class HashMapUnitTest {

    @Test
    public void calculateExeTime() {

        int aHundredMillion = 10000000;

        // 1.未初始化容量
        Map<Integer, Integer> map = new HashMap<>();

        long s1 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map.put(i, i);
        }
        long s2 = System.currentTimeMillis();

        System.out.println("未初始化容量，耗时 ： " + (s2 - s1));

        // 2.初始化容量 5000000
        Map<Integer, Integer> map1 = new HashMap<>(aHundredMillion / 2);

        long s5 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map1.put(i, i);
        }
        long s6 = System.currentTimeMillis();

        System.out.println("初始化容量5000000，耗时 ： " + (s6 - s5));

        // 3.初始化容量为 10000000
        Map<Integer, Integer> map2 = new HashMap<>(aHundredMillion);

        long s3 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map2.put(i, i);
        }
        long s4 = System.currentTimeMillis();

        System.out.println("初始化容量为10000000，耗时 ： " + (s4 - s3));
    }
}
