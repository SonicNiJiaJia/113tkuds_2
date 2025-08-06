// BSTConversionAndBalance.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BSTConversionAndBalance {

    // --- 節點定義 ---
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        // 為了雙向鏈結串列轉換，我們讓 left 和 right 在某些情況下充當 prev 和 next
        // 但這裡仍沿用 BST 的 left/right 命名
        TreeNode prev; // 在雙向鏈結串列中指向前一個節點
        TreeNode next; // 在雙向鏈結串列中指向後一個節點

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.prev = null;
            this.next = null;
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

    // --- 1. 將 BST 轉換為排序的雙向鏈結串列 ---

    private TreeNode head; // 雙向鏈結串列的頭節點
    private TreeNode tail; // 雙向鏈結串列的尾節點

    /**
     * 將 BST 轉換為排序的雙向鏈結串列。
     * 使用中序遍歷，並在遍歷過程中連結節點。
     * 轉換後，原樹的 left 和 right 指針將被用於 prev 和 next。
     *
     * @param root BST 的根節點
     * @return 雙向鏈結串列的頭節點
     */
    public TreeNode convertBSTToDoublyLinkedList(TreeNode root) {
        head = null;
        tail = null;
        inorderToLinkedList(root);
        return head;
    }

    private void inorderToLinkedList(TreeNode node) {
        if (node == null) {
            return;
        }

        // 遞迴處理左子樹
        inorderToLinkedList(node.left);

        // 處理當前節點：連結到雙向鏈結串列
        if (head == null) { // 如果是第一個節點
            head = node;
        } else {
            // 將當前節點連結到尾部
            tail.right = node; // 尾節點的 next 指向當前節點
            node.left = tail;  // 當前節點的 prev 指向尾節點
        }
        tail = node; // 更新尾節點為當前節點

        // 遞迴處理右子樹
        inorderToLinkedList(node.right);
    }

    // --- 2. 將排序陣列轉換為平衡的 BST ---

    /**
     * 將排序陣列轉換為平衡的 BST。
     * 使用遞迴和中點作為根節點的策略。
     *
     * @param nums 排序好的整數陣列 (升序)
     * @return 平衡的 BST 的根節點
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBSTRecursive(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTRecursive(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2; // 避免整數溢位
        TreeNode node = new TreeNode(nums[mid]);

        // 遞迴建立左子樹 (使用中點左邊的部分)
        node.left = sortedArrayToBSTRecursive(nums, start, mid - 1);
        // 遞迴建立右子樹 (使用中點右邊的部分)
        node.right = sortedArrayToBSTRecursive(nums, mid + 1, end);

        return node;
    }

    // --- 3. 檢查 BST 是否平衡，並計算平衡因子 ---

    // Pair 用於同時返回高度和平衡狀態
    static class BalanceInfo {
        boolean isBalanced;
        int height;

        BalanceInfo(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    /**
     * 檢查 BST 是否平衡，並計算平衡因子 (不是直接計算因子，而是判斷是否平衡)。
     * 平衡樹定義為：對於任意節點，其左右子樹的高度差不超過 1。
     *
     * @param root BST 的根節點
     * @return 如果樹是平衡的則返回 true，否則返回 false
     */
    public boolean isBalanced(TreeNode root) {
        return checkBalanceAndHeight(root).isBalanced;
    }

    private BalanceInfo checkBalanceAndHeight(TreeNode node) {
        if (node == null) {
            return new BalanceInfo(true, -1); // 空樹是平衡的，高度定義為 -1
        }

        // 遞迴檢查左子樹和右子樹
        BalanceInfo leftInfo = checkBalanceAndHeight(node.left);
        if (!leftInfo.isBalanced) { // 如果左子樹不平衡，則整棵樹不平衡
            return new BalanceInfo(false, 0); // 高度不重要，直接返回 false
        }

        BalanceInfo rightInfo = checkBalanceAndHeight(node.right);
        if (!rightInfo.isBalanced) { // 如果右子樹不平衡，則整棵樹不平衡
            return new BalanceInfo(false, 0); // 高度不重要，直接返回 false
        }

        // 檢查當前節點的高度差
        int heightDiff = Math.abs(leftInfo.height - rightInfo.height);
        if (heightDiff > 1) {
            return new BalanceInfo(false, 0); // 當前節點不平衡
        }

        // 如果當前節點平衡，計算其高度並返回
        int currentHeight = 1 + Math.max(leftInfo.height, rightInfo.height);
        return new BalanceInfo(true, currentHeight);
    }

    // --- 4. 將 BST 中每個節點的值改為所有大於等於該節點值的總和 ---
    // (Greatest Sum Tree / Convert BST to Greater Sum Tree)

    private int sum = 0; // 用於追蹤累計總和

    /**
     * 將 BST 中每個節點的值改為所有大於等於該節點值的總和。
     * 使用反向中序遍歷 (右 -> 根 -> 左)。
     *
     * @param root BST 的根節點
     * @return 轉換後的樹的根節點
     */
    public TreeNode convertBSTToGreaterSumTree(TreeNode root) {
        sum = 0; // 重置累計總和
        convertBSTRecursive(root);
        return root;
    }

    private void convertBSTRecursive(TreeNode node) {
        if (node == null) {
            return;
        }

        // 優先遍歷右子樹 (最大值在右邊)
        convertBSTRecursive(node.right);

        // 處理當前節點
        sum += node.val; // 累加當前節點原始值
        node.val = sum;  // 將節點值更新為累計總和

        // 遍歷左子樹
        convertBSTRecursive(node.left);
    }

    // --- 輔助方法：各種走訪，用於驗證 ---

    /**
     * 中序走訪
     */
    public List<Integer> getInorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, list);
        list.add(node.val);
        inorderTraversal(node.right, list);
    }

    /**
     * 列印雙向鏈結串列 (從頭到尾)
     */
    public List<Integer> printDoublyLinkedListForward(TreeNode headNode) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = headNode;
        while (current != null) {
            result.add(current.val);
            current = current.right; // 使用 right 作為 next
        }
        return result;
    }

    /**
     * 列印雙向鏈結串列 (從尾到頭)
     */
    public List<Integer> printDoublyLinkedListBackward(TreeNode headNode) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = headNode;
        if (current == null) return result;

        // 先找到尾巴
        while (current.right != null) {
            current = current.right;
        }
        // 從尾巴往前回溯
        while (current != null) {
            result.add(current.val);
            current = current.left; // 使用 left 作為 prev
        }
        return result;
    }


    /**
     * 層次遍歷列印樹 (用於驗證)
     */
    public void printTreeLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        List<List<Integer>> levels = new ArrayList<>();
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
                    currentLevel.add(null);
                }
            }
            // 移除尾部連續的 nulls
            int lastNonNull = currentLevel.size() - 1;
            while (lastNonNull >= 0 && currentLevel.get(lastNonNull) == null) {
                lastNonNull--;
            }
            if (lastNonNull >= 0) {
                levels.add(currentLevel.subList(0, lastNonNull + 1));
            } else if (!currentLevel.isEmpty() && levels.isEmpty()) { // Only root is null
                 levels.add(Collections.singletonList(null));
            }
        }
        for (List<Integer> level : levels) {
            System.out.println(level);
        }
    }


    public static void main(String[] args) {
        BSTConversionAndBalance solver = new BSTConversionAndBalance();

        // 範例 BST：
        //         10
        //        /  \
        //       5    15
        //      / \     \
        //     3   7    18
        TreeNode bstRoot = new TreeNode(10)
            .withLeft(new TreeNode(5).withLeft(new TreeNode(3)).withRight(new TreeNode(7)))
            .withRight(new TreeNode(15).withRight(new TreeNode(18)));

        System.out.println("--- BST 轉換與平衡操作 ---");
        System.out.println("原始 BST (層次遍歷):");
        solver.printTreeLevelOrder(bstRoot);
        System.out.println("原始 BST 中序遍歷: " + solver.getInorder(bstRoot)); // 預期: [3, 5, 7, 10, 15, 18]
        System.out.println("---");

        // 1. 將 BST 轉換為排序的雙向鏈結串列
        System.out.println("\n1. 將 BST 轉換為排序的雙向鏈結串列:");
        TreeNode dllHead = solver.convertBSTToDoublyLinkedList(bstRoot); // 注意：這會修改原 BST 結構
        System.out.println("雙向鏈結串列 (從頭到尾): " + solver.printDoublyLinkedListForward(dllHead));
        System.out.println("雙向鏈結串列 (從尾到頭): " + solver.printDoublyLinkedListBackward(dllHead));
        // 預期: [3, 5, 7, 10, 15, 18]
        // 預期: [18, 15, 10, 7, 5, 3]
        System.out.println("---");


        // 為了後續測試，重新建立一個原始 BST
        bstRoot = new TreeNode(10)
            .withLeft(new TreeNode(5).withLeft(new TreeNode(3)).withRight(new TreeNode(7)))
            .withRight(new TreeNode(15).withRight(new TreeNode(18)));

        // 2. 將排序陣列轉換為平衡的 BST
        System.out.println("\n2. 將排序陣列轉換為平衡的 BST:");
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("排序陣列: " + Arrays.toString(sortedArray));
        TreeNode balancedBST = solver.sortedArrayToBST(sortedArray);
        System.out.println("轉換後平衡 BST (層次遍歷):");
        solver.printTreeLevelOrder(balancedBST);
        System.out.println("轉換後平衡 BST 中序遍歷: " + solver.getInorder(balancedBST));
        // 預期中序: [1, 2, 3, 4, 5, 6, 7, 8, 9] (保持排序)
        // 預期根為 5，左右子樹平衡
        System.out.println("---");

        // 3. 檢查 BST 是否平衡，並計算平衡因子
        System.out.println("\n3. 檢查 BST 是否平衡:");
        System.out.println("原始 BST (不平衡的樹高): " + solver.isBalanced(bstRoot)); // 預期: false (左邊更深)
        System.out.println("剛剛轉換的平衡 BST: " + solver.isBalanced(balancedBST)); // 預期: true

        // 範例：非平衡樹
        //   1
        //    \
        //     2
        //      \
        //       3
        TreeNode unbalancedTree = new TreeNode(1).withRight(new TreeNode(2).withRight(new TreeNode(3)));
        System.out.println("非平衡樹:");
        solver.printTreeLevelOrder(unbalancedTree);
        System.out.println("是否平衡: " + solver.isBalanced(unbalancedTree)); // 預期: false
        System.out.println("---");

        // 4. 將 BST 中每個節點的值改為所有大於等於該節點值的總和
        System.out.println("\n4. 將 BST 中每個節點的值改為所有大於等於該節點值的總和:");
        // 重新建立一個 BST
        bstRoot = new TreeNode(10)
            .withLeft(new TreeNode(5).withLeft(new TreeNode(3)).withRight(new TreeNode(7)))
            .withRight(new TreeNode(15).withRight(new TreeNode(18)));

        System.out.println("原始 BST 中序遍歷: " + solver.getInorder(bstRoot)); // [3, 5, 7, 10, 15, 18]
        TreeNode greaterSumTree = solver.convertBSTToGreaterSumTree(bstRoot); // 這會修改 bstRoot
        System.out.println("轉換後 Greater Sum Tree 中序遍歷: " + solver.getInorder(greaterSumTree));
        // 原始中序: [3, 5, 7, 10, 15, 18]
        // 預期轉換後:
        // 18 -> 18
        // 15 -> 15 + 18 = 33
        // 10 -> 10 + 15 + 18 = 43
        // 7  -> 7 + 10 + 15 + 18 = 50
        // 5  -> 5 + 7 + 10 + 15 + 18 = 55
        // 3  -> 3 + 5 + 7 + 10 + 15 + 18 = 58
        // 預期結果: [58, 55, 50, 43, 33, 18]
        System.out.println("轉換後 Greater Sum Tree (層次遍歷):");
        solver.printTreeLevelOrder(greaterSumTree);
        System.out.println("---");

        // 測試空樹
        System.out.println("\n--- 測試空樹 ---");
        System.out.println("空樹轉 DLL: " + (solver.convertBSTToDoublyLinkedList(null) == null)); // 預期: true
        System.out.println("空陣列轉 BST: " + (solver.sortedArrayToBST(new int[]{}) == null)); // 預期: true
        System.out.println("空樹是否平衡: " + solver.isBalanced(null)); // 預期: true
        System.out.println("空樹轉 Greater Sum Tree: " + (solver.convertBSTToGreaterSumTree(null) == null)); // 預期: true
        System.out.println("---");
    }
}