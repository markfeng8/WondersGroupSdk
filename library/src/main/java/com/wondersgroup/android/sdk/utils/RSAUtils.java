/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:RSA 加密工具类
 */
public final class RSAUtils {

    private static final String TAG = "RSAUtils";
    private static final String RSA = "RSA";
    /**
     * 加密方式，标准jdk的
     */
    private static final String TRANSFORMATION = "RSA/None/PKCS1Padding";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    /**
     * 秘钥默认长度
     */
    private static final int DEFAULT_KEY_SIZE = 1024;
    /**
     * 加密的数据最大的字节数，即117个字节
     */
    private static final int DEFAULT_BUFFER_SIZE = (DEFAULT_KEY_SIZE / 8) - 11;
    /**
     * 当加密的数据超过DEFAULT_BUFFER_SIZE，则使用分段加密
     */
    private static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();

    /**
     * 随机生成 RSA 密钥对(默认密钥长度为1024)
     *
     * @return KeyPair
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    /**
     * 随机生成 RSA 密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048 一般1024
     * @return KeyPair
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥加密
     * 每次加密的字节数，不能超过密钥的长度值减去 11
     *
     * @param data      需加密数据的 byte 数据
     * @param publicKey 公钥
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptData(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data data
     * @param privateKey privateKey
     * @return result
     */
    public static String encryptByPrivateKey(String data, PrivateKey privateKey) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 模长
            int keyLength = rsaPrivateKey.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(data, keyLength - 11);
            stringBuilder = new StringBuilder();
            // 如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                stringBuilder.append(bcd2Str(cipher.doFinal(s.getBytes())));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * 使用公钥分段加密
     */
    public static byte[] encryptByPrivateKeyForSpilt(byte[] data, byte[] privateKey) throws Exception {
        int dataLen = data.length;
        if (dataLen <= DEFAULT_BUFFER_SIZE) {
            return encryptByPrivateKey(data, privateKey);
        }
        List<Byte> allBytes = new ArrayList<>(2048);
        int bufIndex = 0;
        int subDataLoop = 0;
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];

        for (int i = 0; i < dataLen; i++) {
            buf[bufIndex] = data[i];
            if (++bufIndex == DEFAULT_BUFFER_SIZE || i == dataLen - 1) {
                subDataLoop++;
                if (subDataLoop != 1) {
                    for (byte b : DEFAULT_SPLIT) {
                        allBytes.add(b);
                    }
                }
                byte[] encryptBytes = encryptByPrivateKey(buf, privateKey);
                for (byte b : encryptBytes) {
                    allBytes.add(b);
                }
                bufIndex = 0;
                if (i == dataLen - 1) {
                    buf = null;
                } else {
                    buf = new byte[Math.min(DEFAULT_BUFFER_SIZE, dataLen - i - 1)];
                }
            }
        }

        byte[] bytes = new byte[allBytes.size()];
        int i = 0;

        for (Byte b : allBytes) {
            bytes[i++] = b.byteValue();
        }

        return bytes;
    }

    /**
     * 使用私钥加密
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKeyByte) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cp = Cipher.getInstance("RSA");
        cp.init(Cipher.ENCRYPT_MODE, privateKey);
        return cp.doFinal(data);
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return Base64Utils.encode(signed);

        } catch (Exception e) {
            LogUtil.e(TAG, "[出现错误]：" + e);
        }

        return null;
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];
        char val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过 encryptedData() 加密返回的 byte 数据
     * @param privateKey    私钥
     * @return byte[]
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过公钥 byte[](publicKey.getEncoded()) 将公钥还原，适用于 RSA 算法
     *
     * @param keyBytes keyBytes
     * @return result
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes byte[]
     * @return result
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用N、e值还原公钥
     *
     * @param modulus modulus
     * @param publicExponent publicExponent
     * @return result
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus modulus
     * @param privateExponent privateExponent
     * @return result
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     */
    public static PublicKey loadPublicKey(String publicKeyStr) {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "公钥非法");
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "公钥数据为空");
        }

        return null;
    }

    /**
     * 从字符串中加载私钥
     * 加载时使用的是 PKCS8EncodedKeySpec（PKCS#8编码的Key指令）
     *
     * @param privateKeyStr privateKeyStr
     * @return result
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) {
        try {
            byte[] buffer = Base64Utils.decode(privateKeyStr);
            //X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "私钥非法");
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogUtil.e(TAG, "私钥数据为空");
        }

        return null;
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @return 是否成功
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        LogUtil.i(TAG, "----------RSAPublicKey----------");
        LogUtil.i(TAG, "Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        LogUtil.i(TAG, "Modulus=" + rsaPublicKey.getModulus().toString());
        LogUtil.i(TAG, "PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        LogUtil.i(TAG, "PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        LogUtil.i(TAG, "----------RSAPrivateKey ----------");
        LogUtil.i(TAG, "Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        LogUtil.i(TAG, "Modulus=" + rsaPrivateKey.getModulus().toString());
        LogUtil.i(TAG, "PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        LogUtil.i(TAG, "PrivateExponent=" + rsaPrivateKey.getPrivateExponent().toString());
    }

}
