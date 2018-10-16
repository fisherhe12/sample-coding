package com.github.fisherhe12.apache.commons.lang3;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Commons String utils Test case
 *
 * @author fisher
 * @date 2018-01-25
 */
class StringUtilsTest {

	@Test
	void abbreviate() {
		//缩短到某长度,用...结尾.其实就是(substring(str, 0, max-3) + "...")
		//public static String abbreviate(String str,int maxWidth)
		String abbStr = StringUtils.abbreviate("abcdefg", 6);
		assertThat(abbStr).isEqualTo("abc...");
	}

	@Test
	void appendIfMissing() {
		//字符串结尾的后缀是否与你要结尾的后缀匹配，若不匹配则添加后缀
		assertThat(StringUtils.appendIfMissing("abc", "xyz")).isEqualTo("abcxyz");
		assertThat(StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz")).isEqualTo("abcXYZ");
	}

	@Test
	void capitalize() {
		assertThat(StringUtils.capitalize("cat")).isEqualTo("CAT");
		assertThat(StringUtils.uncapitalize("Cat")).isEqualTo("cat");
		//全部字母大小写转换
		assertThat(StringUtils.upperCase("aBc")).isEqualTo("ABC");
		assertThat(StringUtils.lowerCase("aBc")).isEqualTo("abc");
		assertThat(StringUtils.swapCase("The dog has a BONE")).isEqualTo("tHE DOG HAS A bone");
	}

	@Test
	void center() {
		//字符串扩充至指定大小且居中（若扩充大小少于原字符大小则返回原字符，若扩充大小为 负数则为0计算 ）
		assertThat(StringUtils.center("abcd", 2).length()).isEqualTo(4);
		assertThat(StringUtils.center("ab", -1).length()).isEqualTo(2);
		assertThat(StringUtils.center("ab", 4).length()).isEqualTo(4);
		assertThat(StringUtils.center("a", 4, "yz").length()).isEqualTo(4);
		assertThat(StringUtils.center("abc", 7, "").length()).isEqualTo(7);
	}

	@Test
	void chomp() {
		//去除字符串中的"\n", "\r", or "\r\n"
		assertThat(StringUtils.chomp("abc\r\n")).isEqualTo("abc");
	}

	@Test
	void contains() {
		//判断一字符串是否包含另一字符串
		assertThat("abc").contains("z");
		assertThat("abc").containsIgnoringCase("A");
	}

	@Test
	void countMatches() {
		//统计一字符串在另一字符串中出现次数
		assertThat(StringUtils.countMatches("abba", "a")).isEqualTo(2);
	}

	@Test
	void deleteWhiteSpace() {
		//删除字符串中所有的空格,但是不会保留原有的字符串值
		assertThat(StringUtils.deleteWhitespace("   ab  c  ")).isEqualTo("abc");
		//删除字符串中两边的空格,但是不会保留原有的字符串值
		assertThat(StringUtils.trim("   ab  c  ")).isEqualTo("ab  c");
	}

	@Test
	void startWith() {
		//检查起始字符串是否匹配
		assertThat("abcdef").startsWith("abc");
		assertThat(StringUtils.startsWithIgnoreCase("ABCDEF", "abc")).isEqualTo(true);
		assertThat(StringUtils.startsWithAny("abcxyz", new String[]{null, "xyz", "abc"})).isEqualTo(true);
	}

	@Test
	void endWith() {
		//检查字符串结尾后缀是否匹配
		assertThat(StringUtils.endsWith("abc.doc", "doc")).isEqualTo(true);
		assertThat(StringUtils.endsWithIgnoreCase("ABC.DOC", "doc")).isEqualTo(true);
		assertThat(StringUtils.endsWithAny("abcxyz", new String[]{null, "xyz", "abc"})).isEqualTo(true);
	}

	@Test
	void diff() {
		//比较两字符串，返回不同之处。
		assertThat(StringUtils.difference("abcde", "abxyz")).isEqualTo("xyz");
	}

	@Test
	void equals() {
		//判断两字符串是否相同
		assertThat(StringUtils.equals("abc", "abc")).isEqualTo(true);
		assertThat(StringUtils.equalsIgnoreCase("abc", "ABC")).isEqualTo(true);
	}

	@Test
	void indexOf() {
		//正向查找字符在字符串中第一次出现的位置
		assertThat(StringUtils.indexOf("aabaabaa", "b")).isEqualTo(2);
		assertThat(StringUtils.indexOf("aabaabaa", "b", 3)).isEqualTo(5);
		assertThat(StringUtils.ordinalIndexOf("aabaabaa", "a", 3)).isEqualTo(3);
		;

		//反向查找字符串第一次出现的位置
		assertThat(StringUtils.lastIndexOf("aabaabaa", 'b')).isEqualTo(5);
		assertThat(StringUtils.lastIndexOf("aabaabaa", 'b', 4)).isEqualTo(2);
		assertThat(StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2)).isEqualTo(1);
	}

	@Test
	void isNumeric() {
		//判断字符串数字
		assertThat(StringUtils.isNumeric("123")).isEqualTo(true);
		assertThat(StringUtils.isNumeric("12 3")).isEqualTo(false);
	}

	@Test
	void subString() {

		//截取字符串
		assertThat(StringUtils.substring("abcd", 2)).isEqualTo("cd");
		assertThat(StringUtils.substring("abcdef", 2, 4)).isEqualTo("cd");

		//left、right从左(右)开始截取n位字符
		assertThat(StringUtils.left("abc", 2)).isEqualTo("ab");
		assertThat(StringUtils.right("abc", 2)).isEqualTo("bc");
		//从第n位开始截取m位字符   n  m
		assertThat(StringUtils.mid("abcdefg", 2, 4)).isEqualTo("cdef");

		assertThat(StringUtils.substringBefore("abcba", "b")).isEqualTo("a");
		assertThat(StringUtils.substringBeforeLast("abcba", "b")).isEqualTo("abc");
		assertThat(StringUtils.substringAfter("abcba", "b")).isEqualTo("cba");
		assertThat(StringUtils.substringAfterLast("abcba", "b")).isEqualTo("a");
		assertThat(StringUtils.substringBetween("tagabctag", "tag")).isEqualTo("abc");
		assertThat(StringUtils.substringBetween("yabczyabcz", "y", "z")).isEqualTo("abc");
	}

	@Test
	void other() {

		//重复字符
		assertThat(StringUtils.repeat('e', 3)).isEqualTo("eee");

		//反转字符串
		assertThat(StringUtils.reverse("bat")).isEqualTo("tab");

		//删除某字符
		assertThat(StringUtils.remove("queued", 'u')).isEqualTo("qeed");

	}
}
