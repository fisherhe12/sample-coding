package com.github.fisherhe12.algorithm;

import java.util.Stack;

/**
 * 验证是否是二叉排序树
 *
 * @author Yu.He
 */
public class ValidateBinarySearchTree {


    /**
     * 中序遍历求解
     */
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        double inOrder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // If next element in inOrder traversal
            // is smaller than the previous one
            // that's not BST.
            if (root.val <= inOrder) {
                return false;
            }
            inOrder = root.val;
            root = root.right;
        }
        return true;
    }
}

