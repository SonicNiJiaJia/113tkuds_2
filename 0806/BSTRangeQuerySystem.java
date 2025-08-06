// BSTRangeQuerySystem.java

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BSTRangeQuerySystem {

    // --- 節點定義 ---
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode root; // BST 的根節點

    public BSTRangeQuerySystem() {
        this.root = null;
    }

    /**
     * 向 BST 中插入一個節點。
     *
     * @param val 要插入的值
     */
    public void insert(int val) {
        root = insertRecursive(root, val);
    }

    private TreeNode insertRecursive(TreeNode current, int val) {
        if (current == null) {
            return new TreeNode(val);
        }
        if (val < current.val) {
            current.left = insertRecursive(current.left, val);
        } else if (val > current.val) {
            current.right = insertRecursive(current.right, val);
        }
        // 如果 val 等於 current.val，則不插入重複值，或者您可以選擇處理重複值的方式
        return current;
    }

    // --- 實作範圍查詢：找出在 [min, max] 範圍內的所有節點 ---

    /**
     * 找出在 [min, max] 範圍內的所有節點值。
     * 使用遞迴的中序遍歷，並在範圍內收集節點。
     *
     * @param min 範圍的最小值（包含）
     * @param max 範圍的最大值（包含）
     * @return 包含所有在範圍內節點值的列表
     */
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeQueryRecursive(root, min, max, result);
        return result;
    }

    private void rangeQueryRecursive(TreeNode node, int min, int max, List<Integer> result) {
        if (node == null) {
            return;
        }

        // 如果當前節點值大於 min，則可能在左子樹中有符合條件的節點
        if (node.val > min) {
            rangeQueryRecursive(node.left, min, max, result);
        }

        // 如果當前節點值在範圍內，則添加到結果中
        if (node.val >= min && node.val <= max) {
            result.add(node.val);
        }

        // 如果當前節點值小於 max，則可能在右子樹中有符合條件的節點
        if (node.val < max) {
            rangeQueryRecursive(node.right, min, max, result);
        }
    }

    // --- 實作範圍計數：計算在指定範圍內的節點數量 ---

    /**
     * 計算在指定範圍 [min, max] 內的節點數量。
     *
     * @param min 範圍的最小值（包含）
     * @param max 範圍的最大值（包含）
     * @return 在範圍內的節點數量
     */
    public int rangeCount(int min, int max) {
        return rangeCountRecursive(root, min, max);
    }

    private int rangeCountRecursive(TreeNode node, int min, int max) {
        if (node == null) {
            return 0;
        }

        // 如果當前節點值小於 min，則所有符合條件的節點都在右子樹中
        if (node.val < min) {
            return rangeCountRecursive(node.right, min, max);
        }
        // 如果當前節點值大於 max，則所有符合條件的節點都在左子樹中
        if (node.val > max) {
            return rangeCountRecursive(node.left, min, max);
        }

        // 如果當前節點值在範圍內，則計數為 1，並遞迴計數左右子樹
        return 1 + rangeCountRecursive(node.left, min, max) + rangeCountRecursive(node.right, min, max);
    }

    // --- 實作範圍總和：計算在指定範圍內所有節點值的總和 ---

    /**
     * 計算在指定範圍 [min, max] 內所有節點值的總和。
     *
     * @param min 範圍的最小值（包含）
     * @param max 範圍的最大值（包含）
     * @return 在範圍內所有節點值的總和
     */
    public long rangeSum(int min, int max) {
        return rangeSumRecursive(root, min, max);
    }

    private long rangeSumRecursive(TreeNode node, int min, int max) {
        if (node == null) {
            return 0;
        }

        // 如果當前節點值小於 min，則所有符合條件的節點都在右子樹中
        if (node.val < min) {
            return rangeSumRecursive(node.right, min, max);
        }
        // 如果當前節點值大於 max，則所有符合條件的節點都在左子樹中
        if (node.val > max) {
            return rangeSumRecursive(node.left, min, max);
        }

        // 如果當前節點值在範圍內，則將其值加入總和，並遞迴計算左右子樹的和
        return node.val + rangeSumRecursive(node.left, min, max) + rangeSumRecursive(node.right, min, max);
    }

    // --- 實作最接近查詢：找出最接近給定值的節點 ---

    /**
     * 找出樹中最接近給定值的節點。
     *
     * @param target 給定值
     * @return 最接近 target 的節點值
     * @throws NoSuchElementException 如果樹為空
     */
    public int findClosest(int target) {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty, cannot find closest element.");
        }
        return findClosestRecursive(root, target, root.val);
    }

    private int findClosestRecursive(TreeNode node, int target, int closestVal) {
        if (node == null) {
            return closestVal;
        }

        // 檢查當前節點是否更接近目標值
        if (Math.abs(target - node.val) < Math.abs(target - closestVal)) {
            closestVal = node.val;
        } else if (Math.abs(target - node.val) == Math.abs(target - closestVal)) {
            // 如果距離相等，優先選擇較小的值（根據常見定義，或可自定義）
            closestVal = Math.min(closestVal, node.val);
        }

        // 根據目標值和當前節點值決定向左還是向右遞迴
        if (target < node.val) {
            closestVal = findClosestRecursive(node.left, target, closestVal);
        } else if (target > node.val) {
            closestVal = findClosestRecursive(node.right, target, closestVal);
        } else {
            // 如果 target 等於 node.val，則 node.val 就是最接近的
            return node.val;
        }
        return closestVal;
    }


    // --- 輔助方法：中序遍歷以可視化 BST ---
    public void inorderTraversal(TreeNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.val + " ");
            inorderTraversal(node.right);
        }
    }


    public static void main(String[] args) {
        BSTRangeQuerySystem bst = new BSTRangeQuerySystem();

        // 插入節點以建立 BST
        // 樹的結構大致會是：
        //         50
        //        /  \
        //       30   70
        //      / \   / \
        //     20 40 60  80
        //             \
        //              75
        int[] values = {50, 30, 70, 20, 40, 60, 80, 75};
        for (int val : values) {
            bst.insert(val);
        }

        System.out.println("--- BST 範圍查詢系統 ---");
        System.out.print("BST 中序遍歷 (排序後): ");
        bst.inorderTraversal(bst.root);
        System.out.println("\n");

        // 測試範圍查詢
        int min1 = 30, max1 = 70;
        List<Integer> queryResult1 = bst.rangeQuery(min1, max1);
        System.out.println("查詢範圍 [" + min1 + ", " + max1 + "] 中的所有節點: " + queryResult1); // 預期: [30, 40, 50, 60, 70] (順序可能不同，但元素相同)
        System.out.println("---");

        int min2 = 10, max2 = 25;
        List<Integer> queryResult2 = bst.rangeQuery(min2, max2);
        System.out.println("查詢範圍 [" + min2 + ", " + max2 + "] 中的所有節點: " + queryResult2); // 預期: [20]
        System.out.println("---");

        int min3 = 85, max3 = 90;
        List<Integer> queryResult3 = bst.rangeQuery(min3, max3);
        System.out.println("查詢範圍 [" + min3 + ", " + max3 + "] 中的所有節點: " + queryResult3); // 預期: []
        System.out.println("---");


        // 測試範圍計數
        int count1 = bst.rangeCount(30, 70);
        System.out.println("範圍 [" + 30 + ", " + 70 + "] 中的節點數量: " + count1); // 預期: 5
        System.out.println("---");

        int count2 = bst.rangeCount(1, 100);
        System.out.println("範圍 [" + 1 + ", " + 100 + "] 中的節點數量: " + count2); // 預期: 8 (所有節點)
        System.out.println("---");

        int count3 = bst.rangeCount(71, 74);
        System.out.println("範圍 [" + 71 + ", " + 74 + "] 中的節點數量: " + count3); // 預期: 0
        System.out.println("---");


        // 測試範圍總和
        long sum1 = bst.rangeSum(30, 70);
        System.out.println("範圍 [" + 30 + ", " + 70 + "] 中所有節點值的總和: " + sum1); // 預期: 30+40+50+60+70 = 250
        System.out.println("---");

        long sum2 = bst.rangeSum(1, 100);
        long expectedTotalSum = 50 + 30 + 70 + 20 + 40 + 60 + 80 + 75; // 425
        System.out.println("範圍 [" + 1 + ", " + 100 + "] 中所有節點值的總和: " + sum2); // 預期: 425
        System.out.println("---");

        long sum3 = bst.rangeSum(15, 25);
        System.out.println("範圍 [" + 15 + ", " + 25 + "] 中所有節點值的總和: " + sum3); // 預期: 20
        System.out.println("---");


        // 測試最接近查詢
        System.out.println("最接近 45 的節點: " + bst.findClosest(45));   // 預期: 40 或 50 (如果取小的，則是40)
        System.out.println("---");

        System.out.println("最接近 30 的節點: " + bst.findClosest(30));   // 預期: 30
        System.out.println("---");

        System.out.println("最接近 10 的節點: " + bst.findClosest(10));   // 預期: 20
        System.out.println("---");

        System.out.println("最接近 90 的節點: " + bst.findClosest(90));   // 預期: 80
        System.out.println("---");

        System.out.println("最接近 72 的節點: " + bst.findClosest(72));   // 預期: 70 或 75 (如果取小的，則是70)
        System.out.println("---");

        // 測試空樹的最接近查詢
        BSTRangeQuerySystem emptyBst = new BSTRangeQuerySystem();
        try {
            emptyBst.findClosest(100);
        } catch (NoSuchElementException e) {
            System.out.println("空樹的最接近查詢錯誤: " + e.getMessage());
        }
        System.out.println("---");
    }
}