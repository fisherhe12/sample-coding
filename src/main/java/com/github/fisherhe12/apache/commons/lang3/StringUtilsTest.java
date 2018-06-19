package com.github.fisherhe12.apache.commons.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Commons String utils Test case
 *
 * @author fisher
 * @date 2018-01-25
 */
public class StringUtilsTest {

    @Test
    public void abbreviate() {
        //缩短到某长度,用...结尾.其实就是(substring(str, 0, max-3) + "...")
        //public static String abbreviate(String str,int maxWidth)
        String abbStr = StringUtils.abbreviate("abcdefg", 6);
        assertEquals("abc...", abbStr);
    }

    @Test
    public void appendIfMissing() {
        //字符串结尾的后缀是否与你要结尾的后缀匹配，若不匹配则添加后缀
        assertEquals("abcxyz", StringUtils.appendIfMissing("abc", "xyz"));
        assertEquals("abcXYZ", StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz"));
    }

    @Test
    public void capitalize() {
        //首字母大小写转换
        assertEquals("CAT", StringUtils.capitalize("cat"));
        assertEquals("cat", StringUtils.uncapitalize("Cat"));
        //全部字母大小写转换
        assertEquals("ABC", StringUtils.upperCase("aBc"));
        assertEquals("abc", StringUtils.lowerCase("aBc"));
        assertEquals("tHE DOG HAS A bone", StringUtils.swapCase("The dog has a BONE"));
    }

    @Test
    public void center() {
        //字符串扩充至指定大小且居中（若扩充大小少于原字符大小则返回原字符，若扩充大小为 负数则为0计算 ）
        assertEquals(4, StringUtils.center("abcd", 2).length());
        assertEquals(2, StringUtils.center("ab", -1).length());
        assertEquals(4, StringUtils.center("ab", 4).length());
        assertEquals(4, StringUtils.center("a", 4, "yz").length());
        assertEquals(7, StringUtils.center("abc", 7, "").length());
    }

    @Test
    public void chomp() {
        //去除字符串中的"\n", "\r", or "\r\n"
        assertEquals("abc", StringUtils.chomp("abc\r\n"));
    }

    @Test
    public void contains() {
        //判断一字符串是否包含另一字符串
        assertEquals(false, StringUtils.contains("abc", "z"));
        assertEquals(true, StringUtils.containsIgnoreCase("abc", "A"));
    }

    @Test
    public void countMatches() {
        //统计一字符串在另一字符串中出现次数
        assertEquals(2, StringUtils.countMatches("abba", "a"));
    }

    @Test
    public void deleteWhiteSpace() {
        //删除字符串中所有的空格,但是不会保留原有的字符串值
        assertEquals("abc", StringUtils.deleteWhitespace("   ab  c  "));
        //删除字符串中两边的空格,但是不会保留原有的字符串值
        assertEquals("ab  c", StringUtils.trim("   ab  c  "));
    }

    @Test
    public void startWith() {
        //检查起始字符串是否匹配
        assertEquals(true, StringUtils.startsWith("abcdef", "abc"));
        assertEquals(true, StringUtils.startsWithIgnoreCase("ABCDEF", "abc"));
        assertEquals(true, StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}));
    }

    @Test
    public void endWith() {
        //检查字符串结尾后缀是否匹配
        assertEquals(true, StringUtils.endsWith("abc.doc", "doc"));
        assertEquals(true, StringUtils.endsWithIgnoreCase("ABC.DOC", "doc"));
        assertEquals(true, StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}));
    }

    @Test
    public void diff() {
        //比较两字符串，返回不同之处。
        assertEquals("xyz", StringUtils.difference("abcde", "abxyz"));
    }

    @Test
    public void equals() {
        //判断两字符串是否相同
        assertEquals(true, StringUtils.equals("abc", "abc"));
        assertEquals(true, StringUtils.equalsIgnoreCase("abc", "ABC"));
    }

    @Test
    public void indexOf() {
        //正向查找字符在字符串中第一次出现的位置
        assertEquals(2, StringUtils.indexOf("aabaabaa", "b"));
        assertEquals(5, StringUtils.indexOf("aabaabaa", "b", 3));
        assertEquals(3, StringUtils.ordinalIndexOf("aabaabaa", "a", 3));

        //反向查找字符串第一次出现的位置
        assertEquals(5, StringUtils.lastIndexOf("aabaabaa", 'b'));
        assertEquals(2, StringUtils.lastIndexOf("aabaabaa", 'b', 4));
        assertEquals(1, StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2));
    }

    @Test
    public void isNumeric() {
        //判断字符串数字
        assertEquals(true, StringUtils.isNumeric("123"));
        assertEquals(false, StringUtils.isNumeric("12 3"));
    }

    @Test
    public void subString() {

        //截取字符串
        assertEquals("cd", StringUtils.substring("abcd", 2));
        assertEquals("cd", StringUtils.substring("abcdef", 2, 4));

        //left、right从左(右)开始截取n位字符
        assertEquals("ab", StringUtils.left("abc", 2));
        assertEquals("bc", StringUtils.right("abc", 2));
        //从第n位开始截取m位字符   n  m
        assertEquals("cdef", StringUtils.mid("abcdefg", 2, 4));

        assertEquals("a", StringUtils.substringBefore("abcba", "b"));
        assertEquals("abc", StringUtils.substringBeforeLast("abcba", "b"));
        assertEquals("cba", StringUtils.substringAfter("abcba", "b"));
        assertEquals("a", StringUtils.substringAfterLast("abcba", "b"));
        assertEquals("abc", StringUtils.substringBetween("tagabctag", "tag"));
        assertEquals("abc", StringUtils.substringBetween("yabczyabcz", "y", "z"));
    }

    @Test
    public void other() {

        //重复字符
        assertEquals("eee", StringUtils.repeat('e', 3));

        //反转字符串
        assertEquals("tab", StringUtils.reverse("bat"));

        //删除某字符
        assertEquals("qeed", StringUtils.remove("queued", 'u'));

    }
}
