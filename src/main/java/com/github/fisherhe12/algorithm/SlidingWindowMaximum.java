package com.github.fisherhe12.algorithm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
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

        if (k < 1 || nums == null || nums.length == 0) {
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

    public int[] maxSlidingWindow2(int[] nums, int k) {

        if (k < 1 || nums == null || nums.length == 0) {
            return new int[0];
        }
        List<Integer> list = new ArrayList<>();

        //双端队列记录最大值下标
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            //队列不为空，当往后一个数，就会把它的下标放进队列里，从后往前比较，如果队列里的下标对应的数不如它
            //大，就弹出，
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            //判断队首元素是否过期，==是因为它每一次移动都会判定，所以==或者<=都是一样的
            if (deque.peekFirst() == i - k) {
                deque.pollFirst();
            }
            //==是正好形成了第一个滑动窗口，以后每右移一次就添加一个最大数
            if (i >= k - 1) {
                list.add(nums[deque.peekFirst()]);
            }
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{8, 3, -1, 3, 5, 2, 6};
        int k = 3;
        SlidingWindowMaximum slidingWindowMaximum = new SlidingWindowMaximum();
        int[] ints = slidingWindowMaximum.maxSlidingWindow2(nums, k);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

}
