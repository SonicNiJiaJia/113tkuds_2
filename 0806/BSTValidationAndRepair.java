// BSTValidationAndRepair.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BSTValidationAndRepair {

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

        // 輔助方法：用於建立範例樹
        public TreeNode withLeft(TreeNode left) {
            this.left = left;
            return this;
        }

        public TreeNode withRight(TreeNode right) {
            this.right = right;
            return this;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    private long prevVal = Long.MIN_VALUE; // 用於驗證 BST，追蹤中序遍歷的前一個值

    // --- 1. 驗證一棵二元樹是否為有效的BST ---

    /**
     * 驗證一棵二元樹是否為有效的 BST。
     * 方法 1: 使用中序遍歷，檢查節點值是否嚴格遞增。
     *
     * @param root 樹的根節點
     * @return 如果是有效的 BST 則返回 true，否則返回 false
     */
    public boolean isValidBSTInorder(TreeNode root) {
        prevVal = Long.MIN_VALUE; // 重置狀態，避免多次呼叫影響
        return isValidBSTInorderRecursive(root);
    }

    private boolean isValidBSTInorderRecursive(TreeNode node) {
        if (node == null) {
            return true;
        }

        // 遞迴檢查左子樹
        if (!isValidBSTInorderRecursive(node.left)) {
            return false;
        }

        // 檢查當前節點：如果當前節點的值不嚴格大於前一個節點的值，則不是 BST
        if (node.val <= prevVal) {
            return false;
        }
        prevVal = node.val; // 更新前一個節點的值

        // 遞迴檢查右子樹
        return isValidBSTInorderRecursive(node.right);
    }

    /**
     * 驗證一棵二元樹是否為有效的 BST。
     * 方法 2: 使用遞迴，傳遞最小值和最大值範圍。
     *
     * @param root 樹的根節點
     * @return 如果是有效的 BST 則返回 true，否則返回 false
     */
    public boolean isValidBSTRange(TreeNode root) {
        return isValidBSTRangeRecursive(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTRangeRecursive(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        // 檢查當前節點的值是否在允許的範圍內
        if (node.val <= min || node.val >= max) {
            return false;
        }

        // 遞迴檢查左子樹：更新最大值為當前節點的值 (左子樹的所有節點都必須小於當前節點)
        // 遞迴檢查右子樹：更新最小值為當前節點的值 (右子樹的所有節點都必須大於當前節點)
        return isValidBSTRangeRecursive(node.left, min, node.val) &&
               isValidBSTRangeRecursive(node.right, node.val, max);
    }

    // --- 2. 找出BST中不符合規則的節點 (假設只有兩個節點被錯誤交換) ---

    private TreeNode firstMisplaced = null;
    private TreeNode secondMisplaced = null;
    private TreeNode prevNodeInOrder = null; // 用於中序遍歷時追蹤前一個節點

    /**
     * 找出 BST 中兩個位置錯誤的節點 (假設只有兩個節點被錯誤交換)。
     * 使用中序遍歷找到逆序的點。
     *
     * @param root 樹的根節點
     */
    public void findMisplacedNodes(TreeNode root) {
        firstMisplaced = null;
        secondMisplaced = null;
        prevNodeInOrder = null;
        findMisplacedNodesRecursive(root);
    }

    private void findMisplacedNodesRecursive(TreeNode node) {
        if (node == null) {
            return;
        }

        findMisplacedNodesRecursive(node.left);

        // 檢查當前節點與前一個節點的順序
        if (prevNodeInOrder != null && node.val < prevNodeInOrder.val) {
            // 第一次找到逆序 (pre > current)，則 pre 是第一個錯位節點
            if (firstMisplaced == null) {
                firstMisplaced = prevNodeInOrder;
            }
            // 每次找到逆序，current 都是第二個錯位節點 (因為只有兩個錯位，可能是連續的，也可能不連續)
            secondMisplaced = node;
        }
        prevNodeInOrder = node; // 更新前一個節點

        findMisplacedNodesRecursive(node.right);
    }

    // --- 3. 修復一棵有兩個節點位置錯誤的BST ---

    /**
     * 修復一棵有兩個節點位置錯誤的 BST (透過交換它們的值)。
     * 假設只有兩個節點被錯誤交換。
     *
     * @param root 樹的根節點
     */
    public void recoverBST(TreeNode root) {
        findMisplacedNodes(root); // 先找到這兩個節點
        if (firstMisplaced != null && secondMisplaced != null) {
            // 交換它們的值
            int temp = firstMisplaced.val;
            firstMisplaced.val = secondMisplaced.val;
            secondMisplaced.val = temp;
            System.out.println("已修復：交換了節點 " + firstMisplaced.val + " 和 " + secondMisplaced.val + " 的值。");
        } else {
            System.out.println("未找到需要修復的錯位節點，或者樹已經是有效的 BST。");
        }
    }

    // --- 4. 計算需要移除多少個節點才能讓樹變成有效的BST ---

    /**
     * 計算需要移除多少個節點才能讓樹變成有效的 BST。
     * 這可以透過找到最長遞增子序列 (LIS) 的長度來實現。
     * 將 BST 轉換為中序遍歷序列，然後計算 LIS。
     *
     * @param root 樹的根節點
     * @return 需要移除的節點數量
     */
    public int minNodesToRemoveToMakeBST(TreeNode root) {
        if (root == null) {
            return 0; // 空樹已經是 BST
        }

        List<Integer> inorderSequence = new ArrayList<>();
        collectInorder(root, inorderSequence);

        if (inorderSequence.isEmpty()) {
            return 0;
        }

        int longestIncreasingSubsequenceLength = lengthOfLIS(inorderSequence);
        return inorderSequence.size() - longestIncreasingSubsequenceLength;
    }

    // 輔助方法：中序遍歷收集節點值
    private void collectInorder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        collectInorder(node.left, list);
        list.add(node.val);
        collectInorder(node.right, list);
    }

    // 輔助方法：計算最長遞增子序列 (LIS) 的長度
    // 使用 Patience Sorting 或 Binary Search 優化 O(N log N)
    private int lengthOfLIS(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) {
            return 0;
        }

        // tails[i] is the smallest end of all increasing subsequences of length i+1.
        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            // Find the first element in tails that is >= num
            int i = Collections.binarySearch(tails, num);
            if (i < 0) { // If not found, binarySearch returns (-(insertion point) - 1)
                i = -(i + 1);
            }

            if (i == tails.size()) {
                // If num is greater than all elements in tails, append it
                tails.add(num);
            } else {
                // Otherwise, replace the element at index i
                tails.set(i, num);
            }
        }
        return tails.size();
    }


    // --- 輔助方法：層次遍歷列印樹 (用於驗證) ---
    public void printTreeLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        List<List<Integer>> levels = new ArrayList<>();
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    currentLevel.add(node.val);
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    currentLevel.add(null); // 用 null 表示空節點，以便觀察結構
                }
            }
            // 移除尾部連續的 nulls，使輸出更簡潔，但保留中間的 null
            int lastNonNull = currentLevel.size() - 1;
            while (lastNonNull >= 0 && currentLevel.get(lastNonNull) == null) {
                lastNonNull--;
            }
            if (lastNonNull >= 0) {
                levels.add(currentLevel.subList(0, lastNonNull + 1));
            }
        }
        for (List<Integer> level : levels) {
            System.out.println(level);
        }
    }


    public static void main(String[] args) {
        BSTValidationAndRepair validator = new BSTValidationAndRepair();

        // --- 1. 驗證一棵二元樹是否為有效的BST ---
        System.out.println("--- 驗證是否為有效的 BST ---");
        // 有效 BST 範例
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        TreeNode validBST = new TreeNode(4)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(1)).withRight(new TreeNode(3)))
            .withRight(new TreeNode(5));
        System.out.println("樹1 (有效 BST) - 中序驗證: " + validator.isValidBSTInorder(validBST));   // 預期: true
        System.out.println("樹1 (有效 BST) - 範圍驗證: " + validator.isValidBSTRange(validBST));     // 預期: true

        // 無效 BST 範例 (20 在左子樹，但大於根節點 10)
        //     10
        //    /  \
        //   20   5
        TreeNode invalidBST1 = new TreeNode(10)
            .withLeft(new TreeNode(20))
            .withRight(new TreeNode(5));
        System.out.println("樹2 (無效 BST - 20>10) - 中序驗證: " + validator.isValidBSTInorder(invalidBST1)); // 預期: false
        System.out.println("樹2 (無效 BST - 20>10) - 範圍驗證: " + validator.isValidBSTRange(invalidBST1));   // 預期: false

        // 無效 BST 範例 (8 在右子樹，但小於根節點 10)
        //     10
        //    /  \
        //   5    8
        TreeNode invalidBST2 = new TreeNode(10)
            .withLeft(new TreeNode(5))
            .withRight(new TreeNode(8));
        System.out.println("樹3 (無效 BST - 8<10) - 中序驗證: " + validator.isValidBSTInorder(invalidBST2)); // 預期: false
        System.out.println("樹3 (無效 BST - 8<10) - 範圍驗證: " + validator.isValidBSTRange(invalidBST2));   // 預期: false

        // 包含重複值的 BST (嚴格 BST 會視為無效)
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   2  <-- 重複
        TreeNode duplicateValBST = new TreeNode(4)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(1)).withRight(new TreeNode(2)))
            .withRight(new TreeNode(5));
        System.out.println("樹4 (重複值 2) - 中序驗證: " + validator.isValidBSTInorder(duplicateValBST)); // 預期: false (因為 2 <= 2)
        System.out.println("樹4 (重複值 2) - 範圍驗證: " + validator.isValidBSTRange(duplicateValBST));   // 預期: false (因為 2 >= 2)
        System.out.println("---");


        // --- 2. 找出BST中不符合規則的節點 & 3. 修復一棵有兩個節點位置錯誤的BST ---
        System.out.println("\n--- 找出並修復兩個節點錯誤的 BST ---");
        // 錯誤的 BST 範例 (5 和 1 被交換)
        //     4
        //    / \
        //   2   1  <-- 應該是 5
        //  / \
        // 5   3  <-- 應該是 1
        TreeNode brokenBST = new TreeNode(4)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(5)).withRight(new TreeNode(3)))
            .withRight(new TreeNode(1)); // 應該是 5
        System.out.println("原始錯誤 BST (層次遍歷):");
        validator.printTreeLevelOrder(brokenBST);
        System.out.println("驗證是否為有效 BST: " + validator.isValidBSTRange(brokenBST)); // 預期: false

        validator.recoverBST(brokenBST); // 執行修復
        System.out.println("修復後 BST (層次遍歷):");
        validator.printTreeLevelOrder(brokenBST);
        System.out.println("驗證修復後是否為有效 BST: " + validator.isValidBSTRange(brokenBST)); // 預期: true
        System.out.println("---");


        // --- 4. 計算需要移除多少個節點才能讓樹變成有效的BST ---
        System.out.println("\n--- 計算需要移除多少個節點才能變成有效的 BST ---");
        // 範例 1: 已經是 BST
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        System.out.println("樹1 (有效 BST) 需要移除的節點數: " + validator.minNodesToRemoveToMakeBST(validBST)); // 預期: 0

        // 範例 2: 無效 BST
        //   10
        //  /  \
        // 5    20
        //     /  \
        //    15  12 <- 錯誤
        TreeNode corruptTree1 = new TreeNode(10)
            .withLeft(new TreeNode(5))
            .withRight(new TreeNode(20).withLeft(new TreeNode(15)).withRight(new TreeNode(12))); // 12 在 20 的右邊但比 15 小，且比 20 小
        System.out.println("樹5 (中序: 5, 10, 15, 20, 12) 需要移除的節點數: " + validator.minNodesToRemoveToMakeBST(corruptTree1));
        // 中序序列: [5, 10, 15, 20, 12]
        // LIS: [5, 10, 15, 20] 長度為 4
        // 總節點數 5 - LIS 長度 4 = 1 (移除 12)
        // 預期: 1

        // 範例 3: 嚴重錯亂
        //   1
        //  / \
        // 5   2
        //    / \
        //   4   3
        TreeNode corruptTree2 = new TreeNode(1)
            .withLeft(new TreeNode(5))
            .withRight(new TreeNode(2).withLeft(new TreeNode(4)).withRight(new TreeNode(3)));
        System.out.println("樹6 (中序: 5, 1, 4, 2, 3) 需要移除的節點數: " + validator.minNodesToRemoveToMakeBST(corruptTree2));
        // 中序序列: [5, 1, 4, 2, 3]
        // LIS: [1, 2, 3] 或 [1, 4] 或 [1, 3] (取最長)，長度為 3
        // 總節點數 5 - LIS 長度 3 = 2 (移除 5, 4 (或 5, 2))
        // 預期: 2
        System.out.println("---");
    }
}