package com.github.fisherhe12.concurrent;

/**
 * 锁消除的例子
 * JVM 设置锁消除 -server -XX:+DoEscapeAnalysis -XX:+EliminateLocks
 * @author fisher
 * @date 2018-08-07
 */
public class LockEliminationTest {

	public static void main(String args[]) throws InterruptedException {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 20000000; i++) {
			createStringBuffer("JVM", "Diagnosis");
		}
		long bufferCost = System.currentTimeMillis() - start;
		System.out.println("craeteStringBuffer: " + bufferCost + " ms");
	}

	public static String createStringBuffer(String s1, String s2) {
		StringBuffer sb = new StringBuffer();
		sb.append(s1);
		sb.append(s2);
		return sb.toString();
	}

}
