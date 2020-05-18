package com.leetcode.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* Two's complement(补码)
* 以4-bit(16进制)为例
*
* ## 时钟模型
* 由于1111+0001会溢出所有位清0，所以不断地+1相当于有16个刻度的时钟在顺时针转圈
* 所以-7时钟倒着拨动7格相当于 正着拨动16-7格
*
* ## 0的状态重复了
* 用时钟模型的话，0有两种表示方法，正转360°或者负转360°
* 但是在数字电路中，一个值两种状态都能表示不仅是一个UB，而且还浪费了宝贵的状态
* 所以出现了 负数补码=反码+1 也就是让负数的从-0开始全部往后挪一位
* -0变成了-1，-7变成了-8，所以4bit寄存器有符号数的范围是[-8, 7]
* */
public class HashMapTwoSum {

    // 比HashMap的解法快一半
    // 0ms解法
    // 以「补码」的形式存储负数索引
    public static int[] twoSumBitwise(int[] nums, int target) {
        // 2047是input case的最大值，确保：
        // 1. a & bitMode = a;
        // 2. -a & bitMode = bitMode-a+1()
        int volume = 8;        // 100000000000
        int bitMode = volume - 1; // 011111111111
        int[] result = new int[volume];
        for (int i = 0; i < nums.length; i++) {
            // & bitMode防止相减后出现负数索引(case twoSumBitWise1)
            // -4 & 2047 = 4
            int c = (target - nums[i]) & bitMode;
            if (result[c] != 0) {
                return new int[]{result[c] - 1, i};
            }
            // 加1防止index=0时保存的记录被`result[c] != 0`拦截
            // & bitMode防止相减后出现负数索引(case twoSumBitWise2)
            result[nums[i] & bitMode] = i + 1;
        }
        return null;
    }

    @Test
    void twoSumBitWise2() {
        int[] result = twoSumBitwise(new int[]{-3, 4, 3, 90}, 0);
        Assertions.assertArrayEquals(result, new int[]{0, 2});
    }

    @Test
    void twoSumBitWise1() {
        int[] result = twoSumBitwise(new int[]{0, 4, 3, 0}, 0);
        Assertions.assertArrayEquals(result, new int[]{0, 1});
    }

    // leetcode:1ms
    // C++用的是unordered_map
    // 值得一提的是，同样是暴力遍历，C语言耗时140ms，而C++耗时500+ms
    public static int[] twoSumHashMap(int[] nums, int target) {
//        long start = System.nanoTime();
        Map<Integer, Integer> sumTracker = new HashMap<>();
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
    void twoSumHashMapTest() {
        int[] result = twoSumHashMap(new int[]{2, 7, 11, 15}, 9);
        Assertions.assertArrayEquals(result, new int[]{0, 1});
    }

    // leetcode:8ms
    public static int[] twoSumTreeMap(int[] nums, int target) {
        long start = System.nanoTime();
        Map<Integer, Integer> sumTracker = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (sumTracker.containsKey(nums[i])) {
                System.out.println((System.nanoTime() - start) + "ns");
                return new int[]{sumTracker.get(nums[i]), i};
            }
            sumTracker.put(target - nums[i], i);
        }
        return null;
    }

    @Test
    void twoSumTreeMapTest() {
        int[] result = twoSumTreeMap(new int[]{2, 7, 11, 15}, 9);
        Assertions.assertArrayEquals(result, new int[]{0, 1});
    }
}
