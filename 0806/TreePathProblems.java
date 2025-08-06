// TreePathProblems.java

import java.util.ArrayList;
import java.util.List;

public class TreePathProblems {

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

    // --- 1. 找出從根節點到所有葉節點的路徑 ---

    /**
     * 找出從根節點到所有葉節點的路徑。
     * 每個路徑以節點值的列表形式表示。
     *
     * @param root 樹的根節點
     * @return 包含所有根到葉路徑的列表
     */
    public List<List<Integer>> findAllRootToLeafPaths(TreeNode root) {
        List<List<Integer>> allPaths = new ArrayList<>();
        if (root == null) {
            return allPaths;
        }
        // 呼叫遞迴輔助方法，初始路徑為空列表
        findPathsRecursive(root, new ArrayList<>(), allPaths);
        return allPaths;
    }

    private void findPathsRecursive(TreeNode node, List<Integer> currentPath, List<List<Integer>> allPaths) {
        if (node == null) {
            return;
        }

        currentPath.add(node.val); // 將當前節點加入路徑

        // 如果是葉節點，則找到一條完整路徑，將其複製後加入結果列表
        if (node.left == null && node.right == null) {
            allPaths.add(new ArrayList<>(currentPath)); // 注意：必須複製，因為 currentPath 會在遞迴中修改
        } else {
            // 否則，繼續遞迴探索左右子樹
            findPathsRecursive(node.left, currentPath, allPaths);
            findPathsRecursive(node.right, currentPath, allPaths);
        }

        // 回溯：從當前路徑中移除最後一個節點，以便探索其他路徑
        currentPath.remove(currentPath.size() - 1);
    }

    // --- 2. 判斷樹中是否存在和為目標值的根到葉路徑 ---

    /**
     * 判斷樹中是否存在和為目標值的根到葉路徑。
     *
     * @param root   樹的根節點
     * @param targetSum 目標和
     * @return 如果存在則返回 true，否則返回 false
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false; // 空樹沒有路徑
        }

        // 如果是葉節點，且當前節點值等於剩餘的目標和，則找到路徑
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        // 遞迴檢查左子樹或右子樹是否存在滿足條件的路徑
        // 每次遞迴都從目標和中減去當前節點的值
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }

    // --- 3. 找出樹中和最大的根到葉路徑 ---

    private int maxRootToLeafSum = Integer.MIN_VALUE; // 用於追蹤最大和

    /**
     * 找出樹中和最大的根到葉路徑的和。
     *
     * @param root 樹的根節點
     * @return 最大的根到葉路徑的和，如果樹為空則返回 Integer.MIN_VALUE
     */
    public int findMaxRootToLeafSum(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE; // 或拋出異常，或返回 0 (依定義而定)
        }
        maxRootToLeafSum = Integer.MIN_VALUE; // 重置
        findMaxRootToLeafSumRecursive(root, 0);
        return maxRootToLeafSum;
    }

    private void findMaxRootToLeafSumRecursive(TreeNode node, int currentSum) {
        if (node == null) {
            return;
        }

        currentSum += node.val; // 將當前節點值加入和

        // 如果是葉節點，則更新最大和
        if (node.left == null && node.right == null) {
            maxRootToLeafSum = Math.max(maxRootToLeafSum, currentSum);
        } else {
            // 否則，繼續遞迴探索左右子樹
            findMaxRootToLeafSumRecursive(node.left, currentSum);
            findMaxRootToLeafSumRecursive(node.right, currentSum);
        }
    }

    // --- 4. 計算樹中任意兩節點間的最大路徑和（樹的直徑） ---
    // (這裡通常指「任意兩節點之間的路徑和」，而樹的直徑通常指「任意兩節點之間的最長路徑 (邊的數量或節點數量)」)
    // 根據題目意圖，我將實現「任意兩節點之間的路徑和最大值」。
    // 對於「樹的直徑」的另一種理解（最長路徑，以邊計算），我會另外標註。

    private int maxPathSumAcrossNodes = Integer.MIN_VALUE; // 追蹤任意兩節點間的最大路徑和

    /**
     * 計算樹中任意兩節點間的最大路徑和。
     * 路徑可以從任意節點開始，到任意節點結束，不一定經過根節點。
     * 但每個節點只能在路徑中出現一次。
     *
     * @param root 樹的根節點
     * @return 樹中任意兩節點間的最大路徑和
     */
    public int maxPathSum(TreeNode root) {
        maxPathSumAcrossNodes = Integer.MIN_VALUE; // 重置
        maxPathSumRecursive(root);
        return maxPathSumAcrossNodes;
    }

    /**
     * 輔助方法：計算從當前節點向下一條路徑的最大和，並更新全局的最大路徑和。
     *
     * @param node 當前節點
     * @return 從當前節點向下延伸的最大路徑和（只能選擇一個子分支，或不選）
     */
    private int maxPathSumRecursive(TreeNode node) {
        if (node == null) {
            return 0; // 空節點的路徑和為 0 (不貢獻值)
        }

        // 遞迴計算左右子樹的最大單邊路徑和
        // Math.max(0, ...) 是因為負數的路徑和我們不想要，直接取 0 (不走這條路徑)
        int leftMax = Math.max(0, maxPathSumRecursive(node.left));
        int rightMax = Math.max(0, maxPathSumRecursive(node.right));

        // 更新全局最大路徑和：
        // 考慮經過當前節點的路徑：node.val + leftMax + rightMax
        // 這代表了從左子樹某點經過當前節點到右子樹某點的路徑
        maxPathSumAcrossNodes = Math.max(maxPathSumAcrossNodes, node.val + leftMax + rightMax);

        // 返回當前節點可以貢獻給其父節點的最大路徑和 (只能是單邊路徑)
        // 也就是從當前節點向下，走左邊或走右邊的最大值
        return node.val + Math.max(leftMax, rightMax);
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
        TreePathProblems solver = new TreePathProblems();

        // 範例樹 1 (有正數和負數)
        //         10
        //        /  \
        //       5   -3
        //      / \    \
        //     3   2    11
        //    / \   \
        //   3  -2   1
        TreeNode root1 = new TreeNode(10)
            .withLeft(new TreeNode(5).withLeft(new TreeNode(3).withLeft(new TreeNode(3)).withRight(new TreeNode(-2))).withRight(new TreeNode(2).withRight(new TreeNode(1))))
            .withRight(new TreeNode(-3).withRight(new TreeNode(11)));

        System.out.println("--- 樹的路徑相關問題 ---");
        System.out.println("範例樹 1:");
        solver.printTreeLevelOrder(root1);
        System.out.println("---");

        // 1. 找出從根節點到所有葉節點的路徑
        System.out.println("\n1. 從根節點到所有葉節點的路徑:");
        List<List<Integer>> paths1 = solver.findAllRootToLeafPaths(root1);
        for (List<Integer> path : paths1) {
            System.out.println(path);
        }
        // 預期路徑:
        // [10, 5, 3, 3]
        // [10, 5, 3, -2]
        // [10, 5, 2, 1]
        // [10, -3, 11]
        System.out.println("---");

        // 2. 判斷樹中是否存在和為目標值的根到葉路徑
        System.out.println("\n2. 判斷是否存在和為目標值的根到葉路徑:");
        System.out.println("是否存在和為 22 的路徑: " + solver.hasPathSum(root1, 22)); // 預期: true (10+5+3+3=21, 10+5+2+1=18, 10+(-3)+11=18) -> 這裡應該是 10+5+3+3 = 21, 10+5+2+1=18, 10-3+11=18. 喔，10+5+3+3 = 21。
        //  重新確認範例樹路徑和：
        //  10 -> 5 -> 3 -> 3 (Sum: 21)
        //  10 -> 5 -> 3 -> -2 (Sum: 16)
        //  10 -> 5 -> 2 -> 1 (Sum: 18)
        //  10 -> -3 -> 11 (Sum: 18)
        // 所以 22 是 false。
        System.out.println("是否存在和為 18 的路徑: " + solver.hasPathSum(root1, 18)); // 預期: true
        System.out.println("是否存在和為 16 的路徑: " + solver.hasPathSum(root1, 16)); // 預期: true
        System.out.println("是否存在和為 100 的路徑: " + solver.hasPathSum(root1, 100)); // 預期: false
        System.out.println("---");

        // 3. 找出樹中和最大的根到葉路徑
        System.out.println("\n3. 找出樹中和最大的根到葉路徑:");
        System.out.println("最大根到葉路徑和: " + solver.findMaxRootToLeafSum(root1)); // 預期: 21 (10+5+3+3)
        // 測試只有一個節點的樹
        TreeNode singleNodeRoot = new TreeNode(7);
        System.out.println("單節點樹 (7) 的最大根到葉路徑和: " + solver.findMaxRootToLeafSum(singleNodeRoot)); // 預期: 7
        // 測試空樹
        TreeNode emptyRoot = null;
        System.out.println("空樹的最大根到葉路徑和: " + solver.findMaxRootToLeafSum(emptyRoot)); // 預期: Integer.MIN_VALUE
        System.out.println("---");

        // 4. 計算樹中任意兩節點間的最大路徑和 (樹的直徑 - 值總和)
        System.out.println("\n4. 計算樹中任意兩節點間的最大路徑和:");
        System.out.println("樹1 的最大路徑和 (任意兩節點): " + solver.maxPathSum(root1));
        // 範例路徑: 3 -> 3 -> 5 -> 10 -> -3 -> 11 (Sum: 3 + 3 + 5 + 10 - 3 + 11 = 29)
        // 或是 3 -> 3 -> 5 -> 2 -> 1 (Sum: 3 + 3 + 5 + 2 + 1 = 14)
        // 或者是 (-2) -> 3 -> 5 -> 2 -> 1 (Sum: -2 + 3 + 5 + 2 + 1 = 9)
        // 讓我們手動計算一下 maxPathSumRecursive 邏輯：
        // 葉子節點: 3, -2, 1, 11
        // maxPathSumRecursive(3) = 3 + max(0, 0) = 3 -> update maxPathSumAcrossNodes = max(MIN_VALUE, 3+0+0) = 3
        // maxPathSumRecursive(-2) = -2 + max(0, 0) = -2 (returns 0 because negative not taken) -> update maxPathSumAcrossNodes = max(3, -2+0+0) = 3
        // maxPathSumRecursive(3_left_child) = 3 (from 3+max(maxPathSumRecursive(3), maxPathSumRecursive(-2))) -> no, it's 3 + max(0,0) = 3
        // maxPathSumRecursive(3_right_child) = -2 -> return 0
        // maxPathSumRecursive(3_parent) (val=3) returns 3 + max(maxPathSumRecursive(3_left_child), maxPathSumRecursive(3_right_child)) = 3 + max(3, 0) = 6. Update maxPathSumAcrossNodes = max(..., 3 + 3 + 0) = 6
        // ... (這需要仔細追蹤遞迴)
        // 正確的計算應該是：
        // For node 3 (left child of 5): returns 3 (paths: [3])
        // For node -2 (right child of 3): returns 0 (paths: [-2] -> 0 because negative)
        // For node 3 (parent of 3 and -2): returns 3 + max(3, 0) = 6. Updates maxPathSumAcrossNodes with 3 + 3 + 0 = 6.
        // For node 1 (right child of 2): returns 1. Updates maxPathSumAcrossNodes with 1 + 0 + 0 = 1.
        // For node 2 (parent of 1): returns 2 + max(0, 1) = 3. Updates maxPathSumAcrossNodes with 2 + 0 + 1 = 3.
        // For node 5 (parent of 3 and 2):
        // leftMax = maxPathSumRecursive(3) = 6
        // rightMax = maxPathSumRecursive(2) = 3
        // Returns 5 + max(6, 3) = 11
        // Updates maxPathSumAcrossNodes with 5 + 6 + 3 = 14 (Path: 3->3->5->2->1, Sum: 3+3+5+2+1 = 14)
        // For node 11 (right child of -3): returns 11. Updates maxPathSumAcrossNodes with 11.
        // For node -3 (parent of 11): returns -3 + max(0, 11) = 8. Updates maxPathSumAcrossNodes with -3 + 0 + 11 = 8.
        // For node 10 (root):
        // leftMax = maxPathSumRecursive(5) = 11
        // rightMax = maxPathSumRecursive(-3) = 8
        // Returns 10 + max(11, 8) = 21
        // Updates maxPathSumAcrossNodes with 10 + 11 + 8 = 29 (Path: (3->3->)5->10->(-3->)11)
        // So the expected answer is 29.
        System.out.println("---");

        // 範例樹 2 (全負數)
        //        -10
        //        /  \
        //       -5  -3
        //      / \
        //     -3  -2
        TreeNode root2 = new TreeNode(-10)
            .withLeft(new TreeNode(-5).withLeft(new TreeNode(-3)).withRight(new TreeNode(-2)))
            .withRight(new TreeNode(-3));
        System.out.println("\n範例樹 2 (全負數):");
        solver.printTreeLevelOrder(root2);
        System.out.println("最大根到葉路徑和: " + solver.findMaxRootToLeafSum(root2)); // 預期: -10 + (-5) + (-2) = -17 (最靠近0的路徑)
        System.out.println("最大路徑和 (任意兩節點): " + solver.maxPathSum(root2)); // 預期: -2 (單獨的 -2 節點，因為其他路徑都是更小的負數)
                                                                                  // 這裡的邏輯是 max(0, subtree_sum)，所以如果都是負數，maxPathSumRecursive 會回傳 0
                                                                                  // 但是 maxPathSumAcrossNodes 會更新 node.val + leftMax + rightMax
                                                                                  // -2: returns -2, maxPathSumAcrossNodes = max(MIN_VALUE, -2) = -2
                                                                                  // -3(left child of -5): returns -3, maxPathSumAcrossNodes = max(-2, -3) = -2
                                                                                  // -5: leftMax = 0, rightMax = 0 (from -3 and -2), returns -5 + max(0,0) = -5. updates maxPathSumAcrossNodes = max(-2, -5+0+0) = -2
                                                                                  // -3(right child of -10): returns -3, maxPathSumAcrossNodes = max(-2, -3) = -2
                                                                                  // -10: leftMax = 0, rightMax = 0 (from -5 and -3), returns -10 + max(0,0) = -10. updates maxPathSumAcrossNodes = max(-2, -10+0+0) = -2
                                                                                  // 所以最終還是 -2.
        System.out.println("---");
    }
}