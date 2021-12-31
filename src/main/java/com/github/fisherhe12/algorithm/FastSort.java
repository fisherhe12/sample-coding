package com.github.fisherhe12.algorithm;

public class FastSort {

    public static void quickSort(int[] a, int begin, int end) {
        if (begin >= end) {
            return;
        }

        int pivotIndex = partition(a, begin, end);
        quickSort(a, begin, pivotIndex - 1);
        quickSort(a, pivotIndex + 1, end);

    }

    public static int partition(int[] a, int begin, int end) {
        int pivot = a[end];
        int i = begin;

        for (int j = begin; j <= end; j++) {
            if (a[j] <= pivot) {
                swap(a, i++, j);
            }
        }
        swap(a,i,end);
        return i-1;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = { 0, 8, 7, 1, 6, 5, 3, 2, 4, };
        quickSort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
