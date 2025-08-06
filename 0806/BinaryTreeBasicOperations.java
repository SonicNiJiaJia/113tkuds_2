// BinaryTreeBasicOperations.java

import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

public class BinaryTreeBasicOperations {

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

    // --- 計算樹中所有節點值的總和與平均值 ---

    private long sumOfNodes = 0;
    private int countOfNodes = 0;

    /**
     * 遞迴計算樹中所有節點值的總和與節點數量。
     * 這是私有輔助方法，會更新 sumOfNodes 和 countOfNodes。
     *
     * @param node 當前節點
     */
    private void calculateSumAndCountRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        sumOfNodes += node.val;
        countOfNodes++;
        calculateSumAndCountRecursive(node.left);
        calculateSumAndCountRecursive(node.right);
    }

    /**
     * 計算樹中所有節點值的總和。
     *
     * @param root 樹的根節點
     * @return 所有節點值的總和
     */
    public long getSum(TreeNode root) {
        sumOfNodes = 0; // 重置
        countOfNodes = 0; // 重置
        calculateSumAndCountRecursive(root);
        return sumOfNodes;
    }

    /**
     * 計算樹中所有節點值的平均值。
     *
     * @param root 樹的根節點
     * @return 所有節點值的平均值，如果樹為空則返回 0.0
     */
    public double getAverage(TreeNode root) {
        sumOfNodes = 0; // 重置
        countOfNodes = 0; // 重置
        calculateSumAndCountRecursive(root);
        if (countOfNodes == 0) {
            return 0.0;
        }
        return (double) sumOfNodes / countOfNodes;
    }

    // --- 找出樹中的最大值和最小值節點 ---

    /**
     * 找出樹中的最大值節點。
     *
     * @param root 樹的根節點
     * @return 最大值節點的值，如果樹為空則拋出 NoSuchElementException
     */
    public int findMax(TreeNode root) {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty.");
        }
        return findMaxRecursive(root, root.val);
    }

    private int findMaxRecursive(TreeNode node, int currentMax) {
        if (node == null) {
            return currentMax;
        }
        currentMax = Math.max(currentMax, node.val);
        currentMax = Math.max(currentMax, findMaxRecursive(node.left, currentMax));
        currentMax = Math.max(currentMax, findMaxRecursive(node.right, currentMax));
        return currentMax;
    }

    /**
     * 找出樹中的最小值節點。
     *
     * @param root 樹的根節點
     * @return 最小值節點的值，如果樹為空則拋出 NoSuchElementException
     */
    public int findMin(TreeNode root) {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty.");
        }
        return findMinRecursive(root, root.val);
    }

    private int findMinRecursive(TreeNode node, int currentMin) {
        if (node == null) {
            return currentMin;
        }
        currentMin = Math.min(currentMin, node.val);
        currentMin = Math.min(currentMin, findMinRecursive(node.left, currentMin));
        currentMin = Math.min(currentMin, findMinRecursive(node.right, currentMin));
        return currentMin;
    }

    // --- 計算樹的寬度（每一層節點數的最大值） ---

    /**
     * 計算樹的寬度（每一層節點數的最大值）。
     * 使用層次遍歷 (Level Order Traversal / BFS)。
     *
     * @param root 樹的根節點
     * @return 樹的最大寬度，如果樹為空則返回 0
     */
    public int getMaxWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxWidth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // 將根節點加入佇列

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 當前層的節點數就是佇列的大小
            maxWidth = Math.max(maxWidth, levelSize);

            // 處理當前層的所有節點，並將它們的子節點加入佇列
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
        }
        return maxWidth;
    }

    // --- 判斷一棵樹是否為完全二元樹 ---

    /**
     * 判斷一棵樹是否為完全二元樹。
     * 完全二元樹的定義：除了最後一層外，每一層都被完全填滿，
     * 並且最後一層的所有節點都盡可能地靠左。
     * 使用層次遍歷 (BFS) 來檢查。
     *
     * @param root 樹的根節點
     * @return 如果是完全二元樹則返回 true，否則返回 false
     */
    public boolean isCompleteBinaryTree(TreeNode root) {
        if (root == null) {
            return true; // 空樹是完全二元樹
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean sawNull = false; // 用於標記是否已經遇到過空節點

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();

            if (currentNode == null) {
                sawNull = true; // 第一次遇到空節點，標記為 true
            } else {
                if (sawNull) {
                    // 如果在遇到空節點之後又遇到了非空節點，則不是完全二元樹
                    return false;
                }
                // 將子節點（即使是 null）也加入佇列
                queue.offer(currentNode.left);
                queue.offer(currentNode.right);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BinaryTreeBasicOperations treeOps = new BinaryTreeBasicOperations();

        // --- 測試案例 1: 標準二元樹 ---
        //        1
        //       / \
        //      2   3
        //     / \ /
        //    4  5 6
        System.out.println("--- 測試案例 1: 標準二元樹 ---");
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        root1.right.left = new TreeNode(6);

        System.out.println("節點總和: " + treeOps.getSum(root1));      // 預期: 21
        System.out.println("節點平均值: " + treeOps.getAverage(root1));   // 預期: 3.5
        System.out.println("最大值: " + treeOps.findMax(root1));        // 預期: 6
        System.out.println("最小值: " + treeOps.findMin(root1));        // 預期: 1
        System.out.println("樹的寬度: " + treeOps.getMaxWidth(root1)); // 預期: 3 (level 2: 4,5,6)
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root1)); // 預期: true (因為6是3的左子節點，完美符合)
        System.out.println("---");

        // --- 測試案例 2: 不完全二元樹 (右側有空缺) ---
        //        1
        //       / \
        //      2   3
        //     /
        //    4
        System.out.println("\n--- 測試案例 2: 不完全二元樹 (右側有空缺) ---");
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);

        System.out.println("節點總和: " + treeOps.getSum(root2));      // 預期: 10
        System.out.println("節點平均值: " + treeOps.getAverage(root2));   // 預期: 2.5
        System.out.println("最大值: " + treeOps.findMax(root2));        // 預期: 4
        System.out.println("最小值: " + treeOps.findMin(root2));        // 預期: 1
        System.out.println("樹的寬度: " + treeOps.getMaxWidth(root2)); // 預期: 2 (level 1: 2,3 或 level 2: 4) 這裡 Max(2,1)=2
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root2)); // 預期: false (因為3沒有右子節點，但4是2的左子節點)
        System.out.println("---");


        // --- 測試案例 3: 空樹 ---
        System.out.println("\n--- 測試案例 3: 空樹 ---");
        TreeNode root3 = null;
        System.out.println("節點總和: " + treeOps.getSum(root3));      // 預期: 0
        System.out.println("節點平均值: " + treeOps.getAverage(root3));   // 預期: 0.0
        try {
            System.out.println("最大值: " + treeOps.findMax(root3));
        } catch (NoSuchElementException e) {
            System.out.println("最大值: " + e.getMessage()); // 預期: Tree is empty.
        }
        try {
            System.out.println("最小值: " + treeOps.findMin(root3));
        } catch (NoSuchElementException e) {
            System.out.println("最小值: " + e.getMessage()); // 預期: Tree is empty.
        }
        System.out.println("樹的寬度: " + treeOps.getMaxWidth(root3)); // 預期: 0
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root3)); // 預期: true
        System.out.println("---");

        // --- 測試案例 4: 只有一個節點的樹 ---
        System.out.println("\n--- 測試案例 4: 只有一個節點的樹 ---");
        TreeNode root4 = new TreeNode(10);
        System.out.println("節點總和: " + treeOps.getSum(root4));      // 預期: 10
        System.out.println("節點平均值: " + treeOps.getAverage(root4));   // 預期: 10.0
        System.out.println("最大值: " + treeOps.findMax(root4));        // 預期: 10
        System.out.println("最小值: " + treeOps.findMin(root4));        // 預期: 10
        System.out.println("樹的寬度: " + treeOps.getMaxWidth(root4)); // 預期: 1
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root4)); // 預期: true
        System.out.println("---");

        // --- 測試案例 5: 另一個完全二元樹 ---
        //        1
        //       / \
        //      2   3
        //     / \
        //    4  5
        System.out.println("\n--- 測試案例 5: 另一個完全二元樹 ---");
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(3);
        root5.left.left = new TreeNode(4);
        root5.left.right = new TreeNode(5);
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root5)); // 預期: true
        System.out.println("---");

        // --- 測試案例 6: 另一個不完全二元樹 (左側有空缺，但右邊有節點) ---
        //        1
        //       / \
        //      2   3
        //         /
        //        6
        System.out.println("\n--- 測試案例 6: 另一個不完全二元樹 (左側有空缺，但右邊有節點) ---");
        TreeNode root6 = new TreeNode(1);
        root6.left = new TreeNode(2);
        root6.right = new TreeNode(3);
        root6.right.left = new TreeNode(6);
        System.out.println("是否為完全二元樹: " + treeOps.isCompleteBinaryTree(root6)); // 預期: false
        System.out.println("---");
    }
}