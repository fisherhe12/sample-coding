package com.github.fisherhe12.algorithm;


public class MergeSort {

    public static void sort(int[] a, int n) {

        if (n <=1 ) {
            return;
        }
        int mid = n / 2;

        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }

        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];

        }

        sort(l, mid);
        sort(r, n - mid);
        merge(a, l, r);

    }

    public static void merge(int[] a, int[] l, int[] r) {

        int i = 0, j = 0, k = 0;
        while (i < l.length && j < r.length) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < l.length) {
            a[k++] = l[i++];
        }
        while (j < r.length) {
            a[k++] = r[j++];
        }
    }

    public static void main(String[] args) {
        int[] actual = { 5, 1, 6, 2, 3, 4 };
        
        sort(actual, actual.length);

        for (int i = 0; i < actual.length; i++) {
            System.out.println(actual[i]);
        }
        
    }
}
