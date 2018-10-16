package com.github.fisherhe12.apache.commons.lang3;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

/**
 * RandomString utils Test case
 *
 * @author fisher
 * @date 2018-01-25
 * @see org.apache.commons.lang3.RandomStringUtils
 */
class RandomStringUtilsTest {
    @Test
    void random() {
        //在指定字符串中生成长度为n的随机字符串,只包含数字
        System.out.println(RandomStringUtils.randomNumeric(5));
        //生成指定长度的随机字符串,只包含字母
        System.out.println(RandomStringUtils.randomAlphabetic(5));
        //指定从字符或数字中生成随机字符串F
        System.out.println(RandomStringUtils.random(5, "abcdefghijk"));
        //--长度 字母写选项 数字选项
        System.out.println(RandomStringUtils.random(5, true, false));
        System.out.println(RandomStringUtils.random(5, false, true));
    }
}
