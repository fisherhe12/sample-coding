package com.github.fisherhe12.jdk.util.concurrent;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.github.fisherhe12.jdk.util.bit.concurrent
 *
 * @author fisher
 */
public class CopyOnWriteArrayListTest {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("hello world");
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		int nowThreads = topGroup.activeCount();
		Thread[] lstThreads = new Thread[nowThreads];
		topGroup.enumerate(lstThreads);
		for (int i = 0; i < nowThreads; i++) {
			System.out.println("线程number：" + i + " = " + lstThreads[i].getName());
		}

	}

	@Test
	public void create() {
		CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
		Iterator<Integer> iterator = numbers.iterator();
		numbers.add(10);
		List<Integer> resultList = new LinkedList<>();

		iterator.forEachRemaining(resultList::add);
		System.out.println(resultList);
		System.out.println(numbers);
	}
}
