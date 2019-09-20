package com.github.fisherhe12.algorithm;

/**
 * 找出二叉树的最大深度和最小深度(LeeCode-104,111)
 *
 * @author Yu.He
 */
public class MaxOrMinDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = minDepth(root.left);
        int right = minDepth(root.right);

        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
    }
}

