package com.github.fisherhe12.guava;

import com.github.fisherhe12.common.domain.PersonDTO;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ImmutableArrayList
 *
 * @author fisher
 */
public class ImmutableArrayListTest {
    private List<PersonDTO> peopleList;

    @Before
    public void init() {
        peopleList = Lists.newArrayListWithCapacity(4);
        peopleList.add(new PersonDTO("bowen", 27));
        peopleList.add(new PersonDTO("bob", 20));
        peopleList.add(new PersonDTO("Katy", 18));
        peopleList.add(new PersonDTO("Logon", 24));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createByJdk() {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.add("four");
    }

    @Test
    public void create() {
        //--通过copyOf将原来的ArrayList改造成不可变集合--//
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        ImmutableList<String> copyOfImmutableList = ImmutableList.copyOf(list);
        System.out.println(copyOfImmutableList);
        //--通过of模式进行构造--//
        ImmutableList<String> immutableList = ImmutableList.of("one", "two", "three");
        System.out.println(immutableList);
        //--通过build模式进行构造--//
        ImmutableList<Object> immutableList1 = ImmutableList.builder().add("one").add("two").add("three").build();
        System.out.println(immutableList1);
    }

    @Test
    public void partition() {
        //--倒序--//
        peopleList = Lists.reverse(peopleList);
        //--分片--//
        List<List<PersonDTO>> partitionList = Lists.partition(peopleList, 2);

        for (List<PersonDTO> personDTOS : partitionList) {
            System.out.println(personDTOS);
        }

    }

    @Test
    public void transform() {
        List<String> transformedList = Lists.transform(peopleList, personDTO -> personDTO != null ? personDTO.getName() : "");
        System.out.println(transformedList);
    }
}
