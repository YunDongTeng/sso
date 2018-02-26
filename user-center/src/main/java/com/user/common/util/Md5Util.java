package com.user.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by admin on 2018/2/26.
 */
public class Md5Util {

    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static int SALT_LENGTH = 5;

    //普通md5加密
    public static String md5(String password) {


        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(password.getBytes());

            byte[] md = digest.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加盐的md5密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String md5WithSalt(String password, String salt) {

        String md5 = md5(password);

        StringBuffer buffer = new StringBuffer();
        buffer.append(md5.substring(0, 32 - SALT_LENGTH ));
        buffer.append(salt);

        return buffer.toString();
    }

    /**
     * 验证密码是否正确
     *
     * @param inputPass
     * @param password
     * @param salt
     * @return
     */
    public static boolean vertifyPassword(String inputPass, String password, String salt) {
        return md5WithSalt(inputPass, salt).equals(password);
    }

    /**
     * 获取指定长度的盐
     *
     * @return
     */
    public static String getSalt() {

        String source = "ABCDEFHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < SALT_LENGTH; i++) {
            int randomInt = random.nextInt(source.length());
            buffer.append(source.charAt(randomInt));
        }
        return buffer.toString();
    }


    public static void main(String[] args) {
        System.out.println(Md5Util.md5("123456"));
        String salt = getSalt();
        System.out.println(salt);
        System.out.println(md5WithSalt("123456",salt));
        System.out.println(vertifyPassword("123456","E10ADC3949BA59ABBE56E057F206VI0Q","6VI0Q"));
    }
}
