// LevelOrderTraversalVariations.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap; // 使用 TreeMap 保持水平位置的順序

public class LevelOrderTraversalVariations {

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
    }

    // --- 1. 將每一層的節點分別儲存在不同的List中 ---

    /**
     * 將二元樹的每一層節點值分別儲存在不同的 List 中。
     * 使用標準層序遍歷（BFS）。
     *
     * @param root 樹的根節點
     * @return 包含所有層級節點值的 List of Lists
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // 當前層的節點數量
            List<Integer> currentLevelNodes = new ArrayList<>(); // 儲存當前層的節點值

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevelNodes.add(currentNode.val);

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            result.add(currentLevelNodes); // 將當前層的節點列表加入結果
        }
        return result;
    }

    // --- 2. 實作之字形層序走訪（奇數層從左到右，偶數層從右到左） ---

    /**
     * 實作二元樹的之字形層序走訪。
     * 奇數層（0-indexed, 即第 0, 2, 4... 層）從左到右，偶數層（1-indexed, 即第 1, 3, 5... 層）從右到左。
     *
     * @param root 樹的根節點
     * @return 包含之字形遍歷結果的 List of Lists
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0; // 追蹤當前層級 (0-indexed)

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevelNodes = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                currentLevelNodes.add(currentNode.val);

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            // 根據層級決定是否反轉當前層的列表
            if (level % 2 == 1) { // 奇數層 (1-indexed) 需要反轉，對應 0-indexed 的 1, 3, 5...
                Collections.reverse(currentLevelNodes);
            }
            result.add(currentLevelNodes);
            level++; // 進入下一層
        }
        return result;
    }

    // --- 3. 只列印每一層的最後一個節點 ---

    /**
     * 只列印二元樹每一層的最後一個節點。
     *
     * @param root 樹的根節點
     * @return 包含每一層最後一個節點值的列表
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            TreeNode lastNodeOfLevel = null; // 追蹤當前層的最後一個節點

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                lastNodeOfLevel = currentNode; // 每取一個節點，都更新 lastNodeOfLevel

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            // 迴圈結束後，lastNodeOfLevel 就是當前層的最後一個節點
            result.add(lastNodeOfLevel.val);
        }
        return result;
    }

    // --- 4. 實作垂直層序走訪（按照節點的水平位置分組） ---

    /**
     * 實作垂直層序走訪，按照節點的水平位置分組。
     * 水平位置定義：根節點為 0，左子節點為 parent_hd - 1，右子節點為 parent_hd + 1。
     * 使用 BFS 和一個 Map 來儲存每個水平位置的節點列表。
     *
     * @param root 樹的根節點
     * @return 包含垂直層序走訪結果的 List of Lists
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // Map<水平位置, List<節點值>>
        // 使用 TreeMap 確保水平位置的順序 (從最小的水平位置到最大的水平位置)
        Map<Integer, List<Integer>> columnMap = new TreeMap<>();

        // Queue 儲存 Pair<TreeNode, Integer>，Integer 代表節點的水平位置
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0)); // 根節點的水平位置為 0

        int minHd = 0, maxHd = 0; // 追蹤最小和最大的水平位置

        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> currentPair = queue.poll();
            TreeNode currentNode = currentPair.getKey();
            int hd = currentPair.getValue(); // 當前節點的水平位置

            // 將節點值添加到對應水平位置的列表中
            columnMap.computeIfAbsent(hd, k -> new ArrayList<>()).add(currentNode.val);

            // 更新最小和最大水平位置
            minHd = Math.min(minHd, hd);
            maxHd = Math.max(maxHd, hd);

            // 將子節點加入佇列，更新其水平位置
            if (currentNode.left != null) {
                queue.offer(new Pair<>(currentNode.left, hd - 1));
            }
            if (currentNode.right != null) {
                queue.offer(new Pair<>(currentNode.right, hd + 1));
            }
        }

        // 根據 TreeMap 的順序，將每個水平位置的列表加入結果
        for (int hd = minHd; hd <= maxHd; hd++) {
            if (columnMap.containsKey(hd)) {
                result.add(columnMap.get(hd));
            }
        }
        return result;
    }

    // 輔助類別：用於儲存節點及其水平位置的 Pair
    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }


    public static void main(String[] args) {
        LevelOrderTraversalVariations solver = new LevelOrderTraversalVariations();

        // 範例樹：
        //         3
        //        / \
        //       9  20
        //      /   / \
        //     5   15  7
        //          \
        //           12

        TreeNode root = new TreeNode(3)
            .withLeft(new TreeNode(9).withLeft(new TreeNode(5)))
            .withRight(new TreeNode(20).withLeft(new TreeNode(15).withRight(new TreeNode(12))).withRight(new TreeNode(7)));

        System.out.println("--- 樹的各種層序走訪變形 ---");
        System.out.println("範例樹 (層次結構):");
        System.out.println("        3");
        System.out.println("       / \\");
        System.out.println("      9  20");
        System.out.println("     /   / \\");
        System.out.println("    5   15  7");
        System.out.println("         \\");
        System.out.println("          12");
        System.out.println("---");

        // 1. 將每一層的節點分別儲存在不同的List中
        System.out.println("\n1. 將每一層的節點分別儲存在不同的 List 中:");
        List<List<Integer>> levels = solver.levelOrder(root);
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Level " + i + ": " + levels.get(i));
        }
        // 預期:
        // Level 0: [3]
        // Level 1: [9, 20]
        // Level 2: [5, 15, 7]
        // Level 3: [12]
        System.out.println("---");

        // 2. 實作之字形層序走訪
        System.out.println("\n2. 之字形層序走訪 (奇數層從左到右，偶數層從右到左 - 0-indexed):");
        List<List<Integer>> zigzagLevels = solver.zigzagLevelOrder(root);
        for (int i = 0; i < zigzagLevels.size(); i++) {
            System.out.println("Level " + i + ": " + zigzagLevels.get(i));
        }
        // 預期:
        // Level 0: [3]
        // Level 1: [20, 9] (從右到左)
        // Level 2: [5, 15, 7] (從左到右)
        // Level 3: [12] (從右到左)
        System.out.println("---");

        // 3. 只列印每一層的最後一個節點
        System.out.println("\n3. 只列印每一層的最後一個節點 (右側視圖):");
        List<Integer> rightSideViewNodes = solver.rightSideView(root);
        System.out.println("每層的最後一個節點: " + rightSideViewNodes);
        // 預期: [3, 20, 7, 12]
        System.out.println("---");

        // 4. 實作垂直層序走訪
        System.out.println("\n4. 垂直層序走訪 (按照節點的水平位置分組):");
        List<List<Integer>> verticalOrderResult = solver.verticalOrder(root);
        System.out.println("垂直層序走訪結果 (從左到右): " + verticalOrderResult);
        // 預期:
        // -2: [5]
        // -1: [9]
        // 0:  [3, 15]
        // 1:  [20, 12]
        // 2:  [7]
        // 最終輸出會是: [[5], [9], [3, 15], [20, 12], [7]]
        System.out.println("---");

        // 測試空樹
        System.out.println("\n--- 測試空樹 ---");
        TreeNode emptyRoot = null;
        System.out.println("層序遍歷 (空樹): " + solver.levelOrder(emptyRoot));
        System.out.println("之字形層序遍歷 (空樹): " + solver.zigzagLevelOrder(emptyRoot));
        System.out.println("每層的最後一個節點 (空樹): " + solver.rightSideView(emptyRoot));
        System.out.println("垂直層序遍歷 (空樹): " + solver.verticalOrder(emptyRoot));
        System.out.println("---");
    }
}