package com.github.fisherhe12.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树深度遍历
 *
 * @author Yu.He
 */
public class BinaryTreeOrder {

    private static final List<Integer> TMP_LIST = new ArrayList<>();

    /**
     * 先序遍历(递归) 中序后序的方式一样,无非调换一下顺序
     */
    private static List<Integer> preorderTraversalByRecusive(TreeNode root) {
        if (root != null) {
            TMP_LIST.add(root.val);
            preorderTraversalByRecusive(root.left);
            preorderTraversalByRecusive(root.right);
        }
        return TMP_LIST;
    }

    private static List<Integer> preorderTraversalByStack(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> treeNodeStack = new Stack<>();
        while (root != null || !treeNodeStack.isEmpty()) {
            while (root != null) {
                resultList.add(root.val);
                treeNodeStack.push(root);
                root = root.left;
            }
            root = treeNodeStack.pop();
            root = root.right;
        }

        return resultList;
    }

    private static List<Integer> inorderTraversalByStack(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> treeNodeStack = new Stack<>();
        while (root != null || !treeNodeStack.isEmpty()) {
            while (root != null) {
                treeNodeStack.push(root);
                root = root.left;
            }
            root = treeNodeStack.pop();
            resultList.add(root.val);
            root = root.right;
        }
        return resultList;
    }

    private static List<Integer> postorderTraversalByStack(TreeNode root) {
        List<Integer> resultList = new ArrayList<>();
        if (root == null) {
            return resultList;
        }
        Stack<TreeNode> treeNodeStack = new Stack<>();

        TreeNode curNode;
        TreeNode lastVisitNode;
        curNode = root;
        lastVisitNode = null;

        while (curNode != null) {
            treeNodeStack.push(curNode);
            curNode = curNode.left;
        }

        while (!treeNodeStack.isEmpty()) {
            curNode = treeNodeStack.pop();
            if (curNode.right != null && curNode.right != lastVisitNode) {
                treeNodeStack.push(curNode);
                curNode = curNode.right;

                while (curNode != null) {
                    treeNodeStack.push(curNode);
                    curNode = curNode.left;
                }
            } else {
                resultList.add(curNode.val);
                lastVisitNode = curNode;
            }
        }
        return resultList;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);

        TreeNode rightTreeNode = new TreeNode(2);
        treeNode.left = null;
        treeNode.right = rightTreeNode;

        List<Integer> integers = preorderTraversalByStack(treeNode);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }

}
