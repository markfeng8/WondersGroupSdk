package com.wondersgroup.android.sdk;

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
    public void switchCaseTest() {
        String actionType = "001";
        switch (actionType) {
            // 电子社保卡申领完成（一级签发）
            case "001":
                System.out.println("this is 001");
                // 其他申领成功的情况，和 001 一样需要上传 signNo
            case "002":
                System.out.println("this is 002");
                break;
            // 解除绑定完成
            case "003":
                System.out.println("this is 003");
                break;
            default:
                System.out.println("this is default case");
                break;
        }
    }
}