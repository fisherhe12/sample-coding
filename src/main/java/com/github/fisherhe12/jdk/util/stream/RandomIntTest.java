package com.github.fisherhe12.jdk.util.stream;

import java.util.Random;

/**
 * 基于Stream的random
 * @author fisher
 */
public class RandomIntTest {

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			System.out.println(getRandomNumberInRange(100000, 1000000));
		}

	}

	private static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

	}
}
