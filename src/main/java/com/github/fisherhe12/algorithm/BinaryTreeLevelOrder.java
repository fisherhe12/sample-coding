package com.github.fisherhe12.algorithm;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树层次遍历(LeeCode-102)
 *
 * @author Yu.He
 */
public class BinaryTreeLevelOrder {


    /**
     * Using BFS to Traversing
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>(0);
        }

        List<List<Integer>> resultList = new ArrayList<>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelList = new ArrayList<>(levelSize);
            for (int i = 0; i < levelSize; i++) {
                TreeNode treeNode = queue.pop();
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }

                levelList.add(treeNode.val);
            }
            resultList.add(levelList);
        }
        return resultList;
    }


}
