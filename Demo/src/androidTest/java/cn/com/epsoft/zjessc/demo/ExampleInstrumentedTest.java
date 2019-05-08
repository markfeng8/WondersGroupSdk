package cn.com.epsoft.zjessc.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();
    assertEquals("cn.com.epsoft.zjessc.demo", appContext.getPackageName());
    String result = "{\"sign\":\"903DB0E730066C1E920CBAF5944EAD26\",\"idcard\":\"330622197407215513\",\"name\":\"鲁伟兴\"}";
    String key = "1545373843191059";
//    String encrypt = new String(EncryptUtils
//        .encryptAES2Base64(result.getBytes(), key.getBytes(),
//            "AES/CBC/PKCS5Padding", "d22b0a851e014f7b".getBytes()));
//    System.out.println(encrypt);
  }
}
