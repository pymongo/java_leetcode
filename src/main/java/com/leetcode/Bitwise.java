package com.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Bitwise {
}

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
class TwoSum {
    // 比HashMap的解法快一半
    // 0ms解法
    // 以「补码」的形式存储负数索引
    static int[] twoSumBitwise(final int[] nums, final int target) {
        // 2047是input case的最大值，确保：
        // 1. a & bitMode = a;
        // 2. -a & bitMode = bitMode-a+1()
        final int volume = 8;        // 100000000000
        final int bitMode = volume - 1; // 011111111111
        var ret = new int[volume];
        for (int i = 0; i < nums.length; i++) {
            // & bitMode防止相减后出现负数索引(case twoSumBitWise1)
            // -4 & 2047 = 4
            final int c = (target - nums[i]) & bitMode;
            if (ret[c] != 0) {
                return new int[]{ret[c] - 1, i};
            }
            // 加1防止index=0时保存的记录被`result[c] != 0`拦截
            // & bitMode防止相减后出现负数索引(case twoSumBitWise2)
            ret[nums[i] & bitMode] = i + 1;
        }
        return null;
    }

    @Test
    void testTwoSumBitWise() {
        Assertions.assertArrayEquals(twoSumBitwise(new int[]{-3, 4, 3, 90}, 0), new int[]{0, 2});
        Assertions.assertArrayEquals(twoSumBitwise(new int[]{0, 4, 3, 0}, 0), new int[]{0, 3});
    }
}
