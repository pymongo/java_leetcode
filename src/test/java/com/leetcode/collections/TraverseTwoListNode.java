package com.leetcode.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TraverseTwoListNode {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // 仅用于生成测试用例的Helper方法
    public static ListNode arrayToListNode(int []numbers) {
        /* Java没有指针的概念，不能用Rust的另一种避免尾部额外生成一个节点(0, null)的写法
        ListNode headNode = null; // 可能会被GC掉，然后报空指针异常...
        ListNode currentNode = headNode; // 可能会被GC掉，然后报空指针异常...
        for (int number: numbers) {
            currentNode = new ListNode(number);
            currentNode = currentNode.next;
        }
        return headNode;
        */
        ListNode dummyHead = new ListNode();
        ListNode current_node = dummyHead;
        for (int number: numbers) {
            current_node.next = new ListNode(number);
            current_node = current_node.next;
        }
        return dummyHead.next;
    }

    // 仅用于单元测试中验证返回值的Helper方法
    public static int[] listNodeToArray(ListNode listNode) {
        List<Integer> numbers = new ArrayList<>();
        while (listNode != null) {
            numbers.add(listNode.val);
            listNode = listNode.next;
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }

    @Test
    void testHelperMethods() {
        int[] array = new int[]{1, 2, 3};
        ListNode listNode = arrayToListNode(array);
        Assertions.assertArrayEquals(listNodeToArray(listNode), array);
    }

    // 2ms
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
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
    void testAddTwoNumbers() {
        ListNode listNode1 = arrayToListNode(new int[]{5});
        ListNode listNode2 = arrayToListNode(new int[]{5});
        ListNode result = addTwoNumbers(listNode1, listNode2);
        Assertions.assertArrayEquals(listNodeToArray(result), new int[]{0, 1});
    }

}
