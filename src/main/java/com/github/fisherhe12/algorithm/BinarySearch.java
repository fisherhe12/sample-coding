package com.github.fisherhe12.algorithm;

/**
 * com.github.fisherhe12.algorithm
 *
 * @author Fisher.He
 */
public class BinarySearch {

	public int findFirstValueOfArray(int[] a, int value) {
		int n = a.length;
		int low = 0;
		int high = n - 1;

		int mid = low + (high - low) >> 1;


		while (low <= high) {
			if (a[mid] == value) {
				return mid;
			} else if (a[mid] < value) {
				low = mid + 1;
			} else {
				if (mid == 0 || (a[mid - 1] != value)) {
					return mid;
				} else {
					high = mid - 1;
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		BinarySearch binarySearch = new BinarySearch();
		int[] a = {1, 3, 4, 5, 6, 8, 8, 8, 8, 10, 19};

		System.out.println(binarySearch.findFirstValueOfArray(a, 8));

	}
}
