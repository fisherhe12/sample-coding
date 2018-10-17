package com.github.fisherhe12.jdk.util.stream;

import com.github.fisherhe12.common.domain.PersonDTO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

/**
 *  集合框架comparator比较器示例
 * @author fisher
 */
class ComparatorTest {

	/**
	 *1. 传统方式的创建
	 *2.lambda方式创建
	 *3.方法引用方式创建
	 *
	 */
	@Test
	void createComparator() {
		Comparator<PersonDTO> byName = new Comparator<PersonDTO>() {
			@Override
			public int compare(PersonDTO o1, PersonDTO o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		Comparator<PersonDTO> byNameLambda = (PersonDTO o1, PersonDTO o2) -> o1.getName().compareTo(o2.getName());
		Comparator<PersonDTO> byNameReference = Comparator.comparing(PersonDTO::getName);

		Assertions.assertNotNull(byName);
		Assertions.assertNotNull(byNameLambda);
		Assertions.assertNotNull(byNameReference);
	}

	@Test
	void sort() {
		List<PersonDTO> personDTOList = getPersonDTOS();

		personDTOList.sort((PersonDTO o1, PersonDTO o2) -> o2.getAge() - o1.getAge());
		personDTOList.forEach(System.out::println);
	}

	private List<PersonDTO> getPersonDTOS() {
		List<PersonDTO> personDTOList = Lists.newArrayList();
		personDTOList.add(new PersonDTO("alvin", 32));
		personDTOList.add(new PersonDTO("jason", 21));
		personDTOList.add(new PersonDTO("iris", 45));
		personDTOList.add(new PersonDTO("fisher", 30));
		return personDTOList;
	}

	@Test
	void sortByComparator() {
		Comparator<PersonDTO> byNameReference = Comparator.comparing(PersonDTO::getName);
		List<PersonDTO> personDTOS = getPersonDTOS();
		personDTOS.sort(byNameReference.reversed());
		personDTOS.forEach(System.out::println);

	}
}
