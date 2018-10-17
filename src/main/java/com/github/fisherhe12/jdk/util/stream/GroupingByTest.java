package com.github.fisherhe12.jdk.util.stream;

import com.github.fisherhe12.common.domain.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合分组演示
 * @author fisher
 */
class GroupingByTest {

	/**
	 * 1.分组并统计出现的次数
	 * 2.排序
	 */
	@Test
	void groupingByPrimitiveType() {
		List<String> items = Arrays.asList("apple", "apple", "banana", "apple", "orange", "banana", "papaya");

		Map<String, Long> result = items.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		System.out.println(result);

		Map<String, Long> finalMap = new LinkedHashMap<>();
		result.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEachOrdered(stringLongEntry -> finalMap.put(stringLongEntry.getKey(), stringLongEntry.getValue()));

		System.out.println(finalMap);
	}

	@Test
	void groupingByObject() {
		List<Item> items = Arrays
				.asList(new Item("apple", 10, new BigDecimal("9.99")), new Item("banana", 20, new BigDecimal("19.99")),
						new Item("orange", 10, new BigDecimal("29.99")),
						new Item("watermelon", 10, new BigDecimal("29.99")),
						new Item("papaya", 20, new BigDecimal("9.99")), new Item("apple", 10, new BigDecimal("9.99")),
						new Item("banana", 10, new BigDecimal("19.99")), new Item("apple", 20, new BigDecimal("9.99")));

		Map<String, Integer> result = items.stream()
				.collect(Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));
		System.out.println(result);

		Map<BigDecimal, Set<String>> resultMap = items.stream()
				.collect(Collectors.groupingBy(Item::getPrice, Collectors.mapping(Item::getName, Collectors.toSet())));
		System.out.println(resultMap);
	}
}
