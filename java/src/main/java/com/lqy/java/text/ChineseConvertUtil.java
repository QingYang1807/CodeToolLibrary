package com.lqy.java.text;

import com.hankcs.hanlp.HanLP;
public class ChineseConvertUtil {
    /**
     * 将繁体文本转换为简体文本
     *
     * @param traditionalText 繁体文本
     * @return 简体文本
     */
    public static String convertToSimplified(String traditionalText) {
        return HanLP.convertToSimplifiedChinese(traditionalText);
    }

    /**
     * 将简体文本转换为繁体文本
     *
     * @param simplifiedText 简体文本
     * @return 繁体文本
     */
    public static String convertToTraditional(String simplifiedText) {
        return HanLP.convertToTraditionalChinese(simplifiedText);
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        String traditionalText = "a繁體中文";
        String simplifiedText = "B简体中文";

        System.out.println("繁体转简体: " + ChineseConvertUtil.convertToSimplified(traditionalText));
        System.out.println("简体转繁体: " + ChineseConvertUtil.convertToTraditional(simplifiedText));
    }
}
