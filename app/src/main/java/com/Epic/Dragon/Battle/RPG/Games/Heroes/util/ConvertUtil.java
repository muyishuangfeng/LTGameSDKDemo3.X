package com.Epic.Dragon.Battle.RPG.Games.Heroes.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 转换工具类
 */
public class ConvertUtil {

    /**
     * 输入内容
     *
     * @param input   输入的内容
     * @param isCheck 是否取消选择OX
     * @return 返回取消OX后的值
     */
    private static String input(String input, boolean isCheck) {
        input = input.toUpperCase();
        if (isCheck) {
            Pattern pattern = Pattern.compile("0X");
            Matcher matcher = pattern.matcher(input);
            input = matcher.replaceAll("");
        }
        Pattern pattern = Pattern.compile("[^A-Fa-f0-9]");
        Matcher matcher = pattern.matcher(input);
        input = matcher.replaceAll("");
        return input;
    }

    /**
     * 转换
     *
     * @param input   输入的内容
     * @param isCheck 是否取消选择OX
     * @return 返回散列密钥
     */
    public static String convert(String input, boolean isCheck) {
        String array;
        String result = input(input, isCheck);
        if (result.length() % 2 != 0) {
            return "123";
        }
        int[] mArray = new int[result.length() / 2];
        for (int index = 0; index < result.length() / 2; index++) {
            String h = result.substring(index * 2, index * 2 + 2);
            Log.e("TA", h + "");
            mArray[index] = Integer.parseInt(h, 16);
        }
        array = convertTo64(mArray);
        return array;
    }

    /**
     * 转换为64为的String字符串
     *
     * @param input int 数组
     * @return 转换后的散列密钥
     */
    private static String convertTo64(int[] input) {
        String base64_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        StringBuilder ret = new StringBuilder();
        int i = 0;
        int j = 0;
        int[] char_array_3 = new int[3];
        int[] char_array_4 = new int[4];
        int in_len = input.length;
        int pos = 0;

        while (in_len-- != 0) {
            char_array_3[i++] = input[pos++];
            if (i == 3) {
                char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
                char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
                char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
                char_array_4[3] = char_array_3[2] & 0x3f;

                for (i = 0; (i < 4); i++)
                    ret.append(base64_chars.charAt(char_array_4[i]));
                i = 0;
            }
        }

        if (i != 0) {
            for (j = i; j < 3; j++)
                char_array_3[j] = 0;

            char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
            char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
            char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
            char_array_4[3] = char_array_3[2] & 0x3f;

            for (j = 0; (j < i + 1); j++)
                ret.append(base64_chars.charAt(char_array_4[j]));

            while ((i++ < 3))
                ret.append('=');

        }
        return ret.toString();
    }


}
