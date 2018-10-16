package com.github.fisherhe12.apache.commons.lang3;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

/**
 * NumberUtils test case
 *
 * @author fisher
 * @date 2018-01-25
 */
class NumberUtilsTest {

    @Test
    void testNumberUtils() {
        //从数组中选出最大值
        System.out.println(NumberUtils.max(5, 2, 3, 4));
        //判断字符串是否全是整数
        System.out.println(NumberUtils.isDigits("153.4"));
        //判断字符串是否是有效数字
        System.out.println(NumberUtils.isCreatable("0321.1"));
    }
}
