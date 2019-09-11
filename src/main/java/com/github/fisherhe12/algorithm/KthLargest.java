package com.github.fisherhe12.algorithm;

import java.util.PriorityQueue;

/**
 * 实时判断数据流中第K大的元素(LeeCode-703)
 *
 * @author Yu.He
 */
public class KthLargest {

    private final PriorityQueue<Integer> q;
    private final int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        q = new PriorityQueue<>();
        for (int n : nums) {
            add(n);
        }
    }

    public int add(int val) {
        if (q.size() < k) {
            q.offer(val);
        } else if (q.peek() < val) {
            q.poll();
            q.offer(val);
        }
        return q.peek();
    }
}
