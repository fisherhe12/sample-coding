package com.github.fisherhe12.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和（LeeCode-1)
 *
 * @author fisher
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int leftNum = target - nums[i];
            if (map.containsKey(leftNum)) {
                return new int[]{map.get(leftNum), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

}
