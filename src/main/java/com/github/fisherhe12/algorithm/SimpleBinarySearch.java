package com.github.fisherhe12.algorithm;

import javax.sound.midi.MidiChannel;

/**
 * com.github.fisherhe12.algorithm
 *
 * @author Fisher.He
 */
public class SimpleBinarySearch {

	public int search(int[] a, int value) {
		return binarySearch(a, 0, a.length - 1, value);
	}

	public int binarySearch(int[] a, int low, int high, int value) {
		if (low > high) {
			return -1;
		}
		int mid = low + (high - low) / 2;

		if (a[mid] == value) {
			return mid;
		} else if (a[mid] > value) {
			return binarySearch(a, low, mid - 1, value);
		} else {
			return binarySearch(a, mid + 1, high, value);
		}
	}

	public static void main(String[] args) {
		SimpleBinarySearch simpleBinarySearch = new SimpleBinarySearch();

		int[] a = {1, 3, 5, 6, 8, 9, 10, 13, 22};

		System.out.println(simpleBinarySearch.search(a, 10));
	}

}
