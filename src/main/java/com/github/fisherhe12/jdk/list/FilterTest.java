package com.github.fisherhe12.jdk.list;

import com.github.fisherhe12.common.domain.PersonDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 集合框架Stream filter示例
 * @author fisher
 */
class FilterTest {


	/**
	 * filter过滤和collect合并结果集示例
	 * 匹配集合元素
	 */
	@Test
	void filterList() {
		List<String> lines = Arrays.asList("spring", "node", "java");
		List<String> resultList = lines.stream().filter(line -> !StringUtils.equals("node", line))
				.collect(Collectors.toList());
		resultList.forEach(System.out::println);
	}

	/**
	 * filter过滤的进阶用法
	 * 1.findAny()查询匹配的一个元素
	 * 2.如果没有找到匹配的元素,orElse()指定返回一个指定元素
	 */
	@Test
	void filterOne() {
		List<PersonDTO> persons = getPersonDTOS();
		PersonDTO result1 = persons.stream().filter(personDTO -> StringUtils.equals("jack", personDTO.getName()))
				.findAny().orElse(null);
		Assertions.assertNotNull(result1);
		Assertions.assertEquals(result1.getName(), "jack");
	}

	/**
	 * filter->map
	 * 过滤了元素之后并封装自定义返回
	 */
	@Test
	void filterToMap() {
		List<PersonDTO> personDTOS = getPersonDTOS();
		List<String> resultList = personDTOS.stream().map(personDTO -> personDTO.getName())
				.collect(Collectors.toList());
		resultList.forEach(System.out::println);

		String personName = personDTOS.stream().filter(personDTO -> "jack".equals(personDTO.getName()))
				.map(PersonDTO::getName).findAny().orElse(null);
		Assertions.assertEquals(personName, "jack");

	}

	private List<PersonDTO> getPersonDTOS() {
		return Arrays.asList(new PersonDTO("fisher", 30), new PersonDTO("jack", 20), new PersonDTO("lawrence", 40));
	}
}
