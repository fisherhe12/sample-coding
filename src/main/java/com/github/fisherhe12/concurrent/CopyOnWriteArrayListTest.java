package com.github.fisherhe12.concurrent;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.github.fisherhe12.concurrent
 *
 * @author fisher
 */
public class CopyOnWriteArrayListTest {

    @Test
    public void create() {
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[] {1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);
        List<Integer> resultList = new LinkedList<>();

        iterator.forEachRemaining(resultList::add);
        System.out.println(resultList);
        System.out.println(numbers);
    }
}
