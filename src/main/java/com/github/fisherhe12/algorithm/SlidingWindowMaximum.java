package com.github.fisherhe12.algorithm;

import java.util.PriorityQueue;

/**
 * 找出滑动窗口的最大值
 * <p>Given an array nums, there is a sliding window of size k which is moving from the very
 * left of the array to the very right. You can only see the k numbers in the window. Each time the
 * sliding window moves right by one position. Return the max sliding window. Input: nums
 * =[1,3,-1,-3,5,3,6,7], and k = 3 Output: [3,3,5,5,6,7]
 * </p>
 *
 * @author Yu.He
 */
public class SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {

        if (k <= 0 || nums == null || nums.length == 0) {
            return new int[0];
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int startIndex = 0;
        int endIndex = k - 1;
        int[] array = new int[nums.length - k + 1];
        while (endIndex < nums.length) {
            if (!queue.isEmpty()) {
                queue.clear();
            }
            for (int i = startIndex; i <= endIndex; i++) {
                queue.add(nums[i]);
            }
            array[startIndex] = queue.peek();
            startIndex++;
            endIndex++;
        }
        return array;
    }

}
