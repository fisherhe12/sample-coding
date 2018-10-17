package com.github.fisherhe12.jdk.util.stream;

import com.github.fisherhe12.common.domain.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * list与map之间转换的示例
 * @author fisher
 */
class ListMapConvertTest {


	/**
	 * list转Map
	 */
	@Test
	void listConvertToMap() {
		List<Item> items = Arrays
				.asList(new Item("apple", 10, new BigDecimal("9.99")), new Item("banana", 20, new BigDecimal("19.99")),
						new Item("orange", 10, new BigDecimal("29.99")));

		Map<String, BigDecimal> resultMap = items.stream().collect(Collectors.toMap(Item::getName, Item::getPrice));
		System.out.println(resultMap);
	}


	/**
	 * list转map处理重复元素
	 */
	@Test
	void listConvertToMapDuplicate() {
		List<Item> items = Arrays
				.asList(new Item("apple", 10, new BigDecimal("9.99")), new Item("banana", 20, new BigDecimal("19.99")),
						new Item("orange", 10, new BigDecimal("29.99")),
						new Item("watermelon", 10, new BigDecimal("29.99")),
						new Item("papaya", 20, new BigDecimal("9.99")), new Item("apple", 10, new BigDecimal("9.99")),
						new Item("banana", 10, new BigDecimal("19.99")), new Item("apple", 20, new BigDecimal("9.99")));
		Map<String, BigDecimal> resultMap = items.stream().sorted(Comparator.comparing(Item::getName).reversed())
				.collect(Collectors.toMap(Item::getName, Item::getPrice, (bigDecimal, bigDecimal2) -> bigDecimal,
						LinkedHashMap::new));
		System.out.println(resultMap);


	}

	/**
	 * map的Key或者value集合转换成list
	 */
	@Test
	void mapToList() {

		Map<Integer, String> map = new HashMap<>(5);
		map.put(10, "apple");
		map.put(20, "orange");
		map.put(30, "banana");
		map.put(40, "watermelon");
		map.put(50, "dragonFruit");

		ArrayList<Integer> keyList = new ArrayList<>(map.keySet());
		ArrayList<String> valueList = new ArrayList<>(map.values());


		Assertions.assertEquals(5, keyList.size());
		Assertions.assertEquals(5, valueList.size());

		List<String> resultKeyList = map.values().stream().filter(x -> !"banana".equalsIgnoreCase(x))
				.collect(Collectors.toList());
		Assertions.assertEquals(4, resultKeyList.size());

		resultKeyList.forEach(System.out::println);

	}

	/**
	 * 同时将map的key和value分别转换成list
	 */
	@Test
	void mapTo2List() {
		Map<Integer, String> map = new HashMap<>(5);
		map.put(10, "apple");
		map.put(20, "orange");
		map.put(30, "banana");
		map.put(40, "watermelon");
		map.put(50, "dragonFruit");

		List<Integer> resultSortedKey = new ArrayList<>();
		List<String> resultValueList = map.entrySet().stream()
				.sorted(Map.Entry.<Integer, String>comparingByKey().reversed())
				.peek(integerStringEntry -> resultSortedKey.add(integerStringEntry.getKey())).map(x -> x.getValue())
				.filter(x -> !"banana".equalsIgnoreCase(x)).collect(Collectors.toList());

		Assertions.assertEquals(5, resultSortedKey.size());
		Assertions.assertEquals(4, resultValueList.size());
	}
}
