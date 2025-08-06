// BSTKthElement.java

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BSTKthElement {

    // --- 節點定義 ---
    // 為了支援動態插入刪除的第k小元素查詢，我們需要在節點中額外儲存子樹大小 (size)
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int size; // 儲存以該節點為根的子樹的節點總數（包含自身）

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.size = 1; // 新節點初始化時，大小為 1
        }
    }

    private TreeNode root;

    public BSTKthElement() {
        this.root = null;
    }

    // --- 輔助方法：獲取節點大小 ---
    private int getSize(TreeNode node) {
        return node == null ? 0 : node.size;
    }

    // --- 輔助方法：更新節點大小 ---
    // 在插入或刪除後，需要更新路徑上所有祖先節點的 size
    private void updateSize(TreeNode node) {
        if (node != null) {
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }
    }

    // --- 插入節點 (支援動態更新 size) ---
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
        // 如果 val == current.val，通常 BST 不處理重複，或根據需求決定是否插入

        updateSize(current); // 插入後更新當前節點的大小
        return current;
    }

    // --- 刪除節點 (支援動態更新 size) ---
    public void delete(int val) {
        root = deleteRecursive(root, val);
    }

    private TreeNode deleteRecursive(TreeNode current, int val) {
        if (current == null) {
            return null; // 未找到要刪除的節點
        }

        if (val < current.val) {
            current.left = deleteRecursive(current.left, val);
        } else if (val > current.val) {
            current.right = deleteRecursive(current.right, val);
        } else {
            // 找到了要刪除的節點
            // 情況 1: 葉子節點或只有一個子節點
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            // 情況 2: 有兩個子節點
            // 找到右子樹中的最小值 (或左子樹中的最大值)
            TreeNode minNodeInRightSubtree = findMinNode(current.right);
            current.val = minNodeInRightSubtree.val; // 將其值複製到當前節點
            current.right = deleteRecursive(current.right, minNodeInRightSubtree.val); // 刪除右子樹中的最小值
        }

        updateSize(current); // 刪除後更新當前節點的大小
        return current;
    }

    // 輔助方法：找到以 node 為根的子樹中的最小節點
    private TreeNode findMinNode(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    // --- 找出BST中第k小的元素 ---

    /**
     * 找出 BST 中第 k 小的元素。
     * 使用遞迴和子樹大小 (size) 屬性。
     * 時間複雜度：平均 O(log N)，最壞 O(N)
     *
     * @param k 第 k 小 (k 從 1 開始計數)
     * @return 第 k 小的元素值
     * @throws NoSuchElementException 如果樹為空或 k 超出範圍
     */
    public int findKthSmallest(int k) {
        if (root == null || k <= 0 || k > getSize(root)) {
            throw new NoSuchElementException("Invalid k or tree is empty for kth smallest element.");
        }
        return findKthSmallestRecursive(root, k);
    }

    private int findKthSmallestRecursive(TreeNode node, int k) {
        // 左子樹的大小
        int leftSize = getSize(node.left);

        if (k <= leftSize) {
            // 第 k 小在左子樹中
            return findKthSmallestRecursive(node.left, k);
        } else if (k == leftSize + 1) {
            // 第 k 小就是當前節點 (因為左子樹有 leftSize 個節點比它小)
            return node.val;
        } else {
            // 第 k 小在右子樹中，需要找右子樹中的第 (k - leftSize - 1) 小
            return findKthSmallestRecursive(node.right, k - leftSize - 1);
        }
    }

    // --- 找出BST中第k大的元素 ---

    /**
     * 找出 BST 中第 k 大的元素。
     * 使用遞迴和子樹大小 (size) 屬性。
     * 時間複雜度：平均 O(log N)，最壞 O(N)
     *
     * @param k 第 k 大 (k 從 1 開始計數)
     * @return 第 k 大的元素值
     * @throws NoSuchElementException 如果樹為空或 k 超出範圍
     */
    public int findKthLargest(int k) {
        if (root == null || k <= 0 || k > getSize(root)) {
            throw new NoSuchElementException("Invalid k or tree is empty for kth largest element.");
        }
        return findKthLargestRecursive(root, k);
    }

    private int findKthLargestRecursive(TreeNode node, int k) {
        // 右子樹的大小
        int rightSize = getSize(node.right);

        if (k <= rightSize) {
            // 第 k 大在右子樹中
            return findKthLargestRecursive(node.right, k);
        } else if (k == rightSize + 1) {
            // 第 k 大就是當前節點 (因為右子樹有 rightSize 個節點比它大)
            return node.val;
        } else {
            // 第 k 大在左子樹中，需要找左子樹中的第 (k - rightSize - 1) 大
            return findKthLargestRecursive(node.left, k - rightSize - 1);
        }
    }


    // --- 找出BST中第k小到第j小之間的所有元素 ---

    /**
     * 找出 BST 中第 k 小到第 j 小 (包含 k 和 j) 之間的所有元素。
     * 使用遞迴中序遍歷。
     *
     * @param k 第 k 小 (包含)
     * @param j 第 j 小 (包含)
     * @return 包含所有符合條件元素的列表，如果 k > j，則返回空列表
     * @throws IllegalArgumentException 如果 k 或 j 無效
     */
    public List<Integer> findKthToJthSmallest(int k, int j) {
        if (k <= 0 || j <= 0 || k > j || root == null) {
            // 處理無效的 k, j 範圍或空樹
            if (root == null && (k > 0 || j > 0)) {
                throw new NoSuchElementException("Tree is empty.");
            }
            if (k > j) {
                return new ArrayList<>(); // k > j 則返回空列表
            }
            if (k <= 0 || j <= 0) {
                 throw new IllegalArgumentException("k and j must be positive integers.");
            }
            if (k > getSize(root) || j > getSize(root)) {
                throw new IllegalArgumentException("k or j out of tree size range.");
            }
        }

        List<Integer> result = new ArrayList<>();
        // 為了避免重複遍歷，我們將找到第k小和第j小的值，然後進行範圍查詢
        // 或者直接使用中序遍歷並計數
        inorderTraverseAndCollect(root, k, j, new int[]{0}, result); // 使用一個 int[] 作為計數器，以便在遞迴中修改
        return result;
    }

    private void inorderTraverseAndCollect(TreeNode node, int k, int j, int[] count, List<Integer> result) {
        if (node == null) {
            return;
        }

        // 優先遍歷左子樹
        inorderTraverseAndCollect(node.left, k, j, count, result);

        // 處理當前節點
        count[0]++; // 訪問一個節點，計數器遞增
        if (count[0] >= k && count[0] <= j) {
            result.add(node.val);
        }

        // 如果已經收集到足夠的元素 (即 count[0] > j)，則可以停止遍歷右子樹
        // 這是一個優化，避免不必要的遍歷
        if (count[0] > j) {
            return;
        }

        // 遍歷右子樹
        inorderTraverseAndCollect(node.right, k, j, count, result);
    }

    // --- 輔助方法：中序遍歷以可視化 BST (帶有 size 信息) ---
    public void inorderTraversalWithSizes(TreeNode node) {
        if (node != null) {
            inorderTraversalWithSizes(node.left);
            System.out.print("(" + node.val + ", s:" + node.size + ") ");
            inorderTraversalWithSizes(node.right);
        }
    }


    public static void main(String[] args) {
        BSTKthElement bst = new BSTKthElement();

        // 插入節點
        // 樹的結構大致會是：
        //         50 (s:8)
        //        /      \
        //     30 (s:4)   70 (s:3)
        //    /    \     /    \
        // 20 (s:2) 40 (s:1) 60 (s:1) 80 (s:1)
        // /
        // 10 (s:1)
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10};
        for (int val : values) {
            bst.insert(val);
        }

        System.out.println("--- BST Kth 元素查詢系統 ---");
        System.out.print("BST 中序遍歷 (值, size): ");
        bst.inorderTraversalWithSizes(bst.root);
        System.out.println("\n總節點數: " + bst.getSize(bst.root));
        System.out.println("---");

        // 測試找出第k小的元素
        try {
            System.out.println("第 1 小的元素: " + bst.findKthSmallest(1)); // 預期: 10
            System.out.println("第 3 小的元素: " + bst.findKthSmallest(3)); // 預期: 30
            System.out.println("第 5 小的元素: " + bst.findKthSmallest(5)); // 預期: 50
            System.out.println("第 8 小的元素: " + bst.findKthSmallest(8)); // 預期: 80
            // System.out.println("第 0 小的元素: " + bst.findKthSmallest(0)); // 預期: 拋出 NoSuchElementException
            // System.out.println("第 9 小的元素: " + bst.findKthSmallest(9)); // 預期: 拋出 NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("錯誤: " + e.getMessage());
        }
        System.out.println("---");

        // 測試找出第k大的元素
        try {
            System.out.println("第 1 大的元素: " + bst.findKthLargest(1)); // 預期: 80
            System.out.println("第 3 大的元素: " + bst.findKthLargest(3)); // 預期: 60
            System.out.println("第 5 大的元素: " + bst.findKthLargest(5)); // 預期: 40
            System.out.println("第 8 大的元素: " + bst.findKthLargest(8)); // 預期: 10
        } catch (NoSuchElementException e) {
            System.out.println("錯誤: " + e.getMessage());
        }
        System.out.println("---");

        // 測試找出第k小到第j小之間的所有元素
        try {
            System.out.println("第 2 小到第 6 小的元素: " + bst.findKthToJthSmallest(2, 6)); // 預期: [20, 30, 40, 50, 60]
            System.out.println("第 1 小到第 1 小的元素: " + bst.findKthToJthSmallest(1, 1)); // 預期: [10]
            System.out.println("第 7 小到第 8 小的元素: " + bst.findKthToJthSmallest(7, 8)); // 預期: [70, 80]
            System.out.println("第 5 小到第 3 小的元素 (k>j): " + bst.findKthToJthSmallest(5, 3)); // 預期: []
            // System.out.println("第 0 小到第 3 小的元素: " + bst.findKthToJthSmallest(0, 3)); // 預期: 拋出 IllegalArgumentException
            // System.out.println("第 1 小到第 9 小的元素: " + bst.findKthToJthSmallest(1, 9)); // 預期: 拋出 IllegalArgumentException
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("錯誤: " + e.getMessage());
        }
        System.out.println("---");

        // 測試動態插入刪除後的第k小元素查詢
        System.out.println("\n--- 測試動態插入刪除後的第k小元素查詢 ---");
        System.out.println("初始總節點數: " + bst.getSize(bst.root)); // 8
        System.out.println("初始第 5 小: " + bst.findKthSmallest(5)); // 50

        System.out.println("\n插入 55");
        bst.insert(55);
        System.out.print("插入 55 後 BST 中序遍歷 (值, size): ");
        bst.inorderTraversalWithSizes(bst.root);
        System.out.println("\n新總節點數: " + bst.getSize(bst.root)); // 9
        System.out.println("新的第 5 小: " + bst.findKthSmallest(5)); // 50 (55插入到50的右子樹)
        System.out.println("新的第 6 小: " + bst.findKthSmallest(6)); // 55

        System.out.println("\n刪除 40");
        bst.delete(40);
        System.out.print("刪除 40 後 BST 中序遍歷 (值, size): ");
        bst.inorderTraversalWithSizes(bst.root);
        System.out.println("\n新總節點數: " + bst.getSize(bst.root)); // 8
        System.out.println("新的第 5 小: " + bst.findKthSmallest(5)); // 55 (原來的 50 的右邊的 60)
        System.out.println("新的第 4 小: " + bst.findKthSmallest(4)); // 50

        System.out.println("\n刪除 50 (根節點)");
        bst.delete(50);
        System.out.print("刪除 50 後 BST 中序遍歷 (值, size): ");
        bst.inorderTraversalWithSizes(bst.root);
        System.out.println("\n新總節點數: " + bst.getSize(bst.root)); // 7
        System.out.println("新的第 4 小: " + bst.findKthSmallest(4)); // 55
        System.out.println("---");
    }
}