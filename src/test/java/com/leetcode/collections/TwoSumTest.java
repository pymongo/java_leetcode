package com.leetcode.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TwoSumTest {

    // 21968ns 20565ns 19601ns
    public static int[] twoSum(int[] nums, int target) {
//        long start = System.nanoTime();
        Map<Integer, Integer> sumTracker = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (sumTracker.containsKey(nums[i])) {
//                System.out.println((System.nanoTime() - start) + "ns");
                return new int[]{sumTracker.get(nums[i]), i};
            }
            sumTracker.put(target - nums[i], i);
        }
        throw new IllegalArgumentException("No solution");
    }

    @Test
    void twoSumTest() {
        int[] result = twoSum(new int[]{2, 7, 11, 15}, 9);
        Assertions.assertArrayEquals(result, new int[]{0, 1});
    }
}
