package com.wondersgroup.android.healthcity_sdk;

import com.wondersgroup.android.sdk.utils.DateUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void datetest() {
        String a = DateUtils.getCurMonthBeforeM(3);
        String aas = "";
    }
}