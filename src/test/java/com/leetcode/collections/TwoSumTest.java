package com.leetcode.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TwoSumTest {
    @Test
    void twoSumTest() {
        int[] result = TwoSum.twoSum(new int[]{2, 7, 11, 15}, 9);
        assertArrayEquals(result, new int[]{0, 1});
    }
}
