package com.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

class TestBinaryTree {
    @Test
    void test_tree_node_serialize() {
        final var nums = new Integer[]{5, 3, 6, 2, 4, null, 8, 1, null, null, null, 7, 9};
        final var node = TreeNode.deserialize(nums);
        assert node != null;
        node.print_tree();
        Assertions.assertArrayEquals(node.serialize(), nums);
    }
}

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(final int val) {
        this.val = val;
    }

    TreeNode(final Integer[] level_order) {
        var head =  deserialize(level_order);
        this.val = head.val;
        this.left = head.left;
        this.right = head.right;
    }

    void print_tree() {
        var sb = new StringBuilder();
        final var nums = this.serialize();
        for (final var num : nums) {
            if (num == null) {
                sb.append('#');
            } else {
                sb.append(num);
            }
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        final var arg = String.format("from drawtree.drawtree import deserialize, drawtree; drawtree(deserialize('%s'))", sb);
        // Runtime().run() would not sync wait python program cause, so Runtime.run() no stdout
        ProcessBuilder pb = new ProcessBuilder("python3", "-c", arg);
        pb.inheritIO();
        try {
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Integer[] serialize() {
        var ret = new ArrayList<Integer>();
        var queue = new ArrayDeque<Optional<TreeNode>>();
        queue.addLast(Optional.of(this));
        while (!queue.isEmpty()) {
            final var node_opt = queue.removeFirst();
            node_opt.ifPresentOrElse(
                    (node) -> {
                        ret.add(node.val);
                        // queue.addLast require @NotNull, need to map nullable to optional
                        if (node.left == null) {
                            queue.addLast(Optional.empty());
                        } else {
                            queue.addLast(Optional.of(node.left));
                        }
                        if (node.right == null) {
                            queue.addLast(Optional.empty());
                        } else {
                            queue.addLast(Optional.of(node.right));
                        }
                    },
                    () -> ret.add(null)
            );
        }
        // remove all null at end
        while (ret.get(ret.size() - 1) == null) {
            ret.remove(ret.size() - 1);
        }

        return ret.toArray(Integer[]::new);
    }

    static TreeNode deserialize(final Integer[] level_order) {
        if (level_order == null) {
            return null;
        }
        List<TreeNode> nodes = Arrays.stream(level_order).map(integer -> {
            if (integer == null) {
                return null;
            } else {
                return new TreeNode(integer);
            }
        }).collect(Collectors.toList());
        final int len = nodes.size();
        int child_node_ptr = 1;
        for (TreeNode node : nodes) {
            if (node == null) {
                continue;
            }
            if (child_node_ptr < len) {
                node.left = nodes.get(child_node_ptr);
                child_node_ptr++;
            }
            if (child_node_ptr < len) {
                node.right = nodes.get(child_node_ptr);
                child_node_ptr++;
            }
        }
        return nodes.get(0);
    }
}

// https://leetcode.com/problems/increasing-order-search-tree/
class increasingOrderSearchTree {
    private TreeNode pre_node;

    private void inorder(TreeNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);

        node.left = null;
        pre_node.right = node;
        pre_node = node;

        inorder(node.right);
    }

    public TreeNode increasingBST(TreeNode root) {
        TreeNode dummy = new TreeNode(-1);
        pre_node = dummy;
        inorder(root);
        return dummy.right;
    }
}
