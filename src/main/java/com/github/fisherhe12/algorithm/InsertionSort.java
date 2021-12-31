package com.github.fisherhe12.algorithm;

import org.checkerframework.checker.units.qual.A;

/**
 * com.github.fisherhe12.algorithm
 *
 * @author Fisher.He
 */
public class InsertionSort {

	public static void sort(int[] a) {
		if (a.length < 1) {
			return;
		}

		for (int i = 1; i < a.length; ++i) {
			int value = a[i];
			int j = i - 1;
			for (; j >= 0; --j) {
				if (a[j] > value) {
					a[j + 1] = a[j];

				} else {
					break;
				}

			}
			a[j + 1] = value;
		}

	}

	public static void main(String[] args) {
		int[] a = { 4, 1, 5, 2, 3, 4, 6 };
		sort(a);
		for (int i : a) {
			System.out.println(i);
		}

	}

}
