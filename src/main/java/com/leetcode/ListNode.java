package com.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(final int val) {
        this.val = val;
    }

    ListNode(final int val, final ListNode next) {
        this.val = val;
        this.next = next;
    }

    ListNode(final int[] nums) {
        ListNode dummy_head = new ListNode();
        ListNode curr_node = dummy_head;
        for (final var num: nums) {
            curr_node.next = new ListNode(num);
            curr_node = curr_node.next;
        }
        this.val = dummy_head.next.val;
        this.next = dummy_head.next.next;
    }

    int [] to_array() {
        List<Integer> numbers = new ArrayList<>();
        var curr_node = this;
        while (curr_node != null) {
            numbers.add(curr_node.val);
            curr_node = curr_node.next;
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }
}

class TestListNode {
    @Test
    void test_to_array() {
        final var nums = new int[] { 1,2,3};
        final var node = new ListNode(nums);
        Assertions.assertArrayEquals(node.to_array(), nums);
    }

    // https://leetcode.com/problems/add-two-numbers
    public static ListNode add_two_numbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode();
        ListNode current_node = dummyHead;
        int sumOrCarry = 0;
        while (l1 != null || l2 != null || sumOrCarry != 0) {
            if (l1 != null) {
                sumOrCarry += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sumOrCarry += l2.val;
                l2 = l2.next;
            }
            current_node.next = new ListNode(sumOrCarry % 10);
            current_node = current_node.next;
            sumOrCarry /= 10;
        }
        return dummyHead.next;
    }

    @Test
    void test_add_two_numbers() {
        final ListNode ln1 = new ListNode(new int[]{5});
        final ListNode ln2 = new ListNode(new int[]{5});
        Assertions.assertArrayEquals(add_two_numbers(ln1, ln2).to_array(), new int[]{0, 1});
    }
}
