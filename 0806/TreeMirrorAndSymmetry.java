// TreeMirrorAndSymmetry.java

import java.util.LinkedList;
import java.util.Queue;

public class TreeMirrorAndSymmetry {

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

    // --- 判斷一棵二元樹是否為對稱樹 ---

    /**
     * 判斷一棵二元樹是否為對稱樹 (Symmetric Tree)。
     * 一棵樹是對稱的，若它的左右子樹是彼此的鏡像。
     *
     * @param root 樹的根節點
     * @return 如果樹是對稱的則返回 true，否則返回 false
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true; // 空樹是對稱的
        }
        // 檢查根節點的左右子樹是否互為鏡像
        return isMirror(root.left, root.right);
    }

    /**
     * 輔助方法：判斷兩棵樹 (或子樹) 是否互為鏡像。
     *
     * @param t1 第一棵樹的根節點
     * @param t2 第二棵樹的根節點
     * @return 如果兩棵樹互為鏡像則返回 true，否則返回 false
     */
    private boolean isMirror(TreeNode t1, TreeNode t2) {
        // 兩者皆為空，表示都到達葉子節點外，對稱
        if (t1 == null && t2 == null) {
            return true;
        }
        // 其中一個為空，另一個不為空，不對稱
        if (t1 == null || t2 == null) {
            return false;
        }
        // 值不同，不對稱
        if (t1.val != t2.val) {
            return false;
        }

        // 遞迴檢查：
        // t1 的左子樹必須與 t2 的右子樹鏡像
        // t1 的右子樹必須與 t2 的左子樹鏡像
        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    // --- 將一棵二元樹轉換為其鏡像樹 ---

    /**
     * 將一棵二元樹轉換為其鏡像樹。
     * (這會修改原始樹)
     *
     * @param root 樹的根節點
     * @return 轉換為鏡像後的樹的根節點
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 遞迴地反轉左右子樹
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        // 交換當前節點的左右子節點
        root.left = right;
        root.right = left;

        return root;
    }

    // --- 比較兩棵二元樹是否互為鏡像 ---

    /**
     * 比較兩棵二元樹是否互為鏡像。
     *
     * @param p 第一棵樹的根節點
     * @param q 第二棵樹的根節點
     * @return 如果兩棵樹互為鏡像則返回 true，否則返回 false
     */
    public boolean areTreesMirror(TreeNode p, TreeNode q) {
        return isMirror(p, q); // 直接使用 isMirror 輔助方法
    }

    // --- 檢查一棵樹是否為另一棵樹的子樹 ---

    /**
     * 檢查一棵樹 (subRoot) 是否為另一棵樹 (root) 的子樹。
     *
     * @param root    主樹的根節點
     * @param subRoot 子樹的根節點
     * @return 如果 subRoot 是 root 的子樹則返回 true，否則返回 false
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true; // 空樹總是任何樹的子樹
        }
        if (root == null) {
            return false; // 非空子樹不能是空樹的子樹
        }

        // 檢查當前節點的子樹是否與 subRoot 相同 (樹結構和值都相同)
        if (isSameTree(root, subRoot)) {
            return true;
        }

        // 遞迴檢查左子樹或右子樹是否包含 subRoot
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /**
     * 輔助方法：判斷兩棵樹是否結構相同且值相同 (用於 isSubtree)。
     *
     * @param p 第一棵樹的根節點
     * @param q 第二棵樹的根節點
     * @return 如果兩棵樹結構和值都相同則返回 true，否則返回 false
     */
    private boolean isSameTree(TreeNode p, TreeNode q) {
        // 兩者皆為空，表示都到達葉子節點外，相同
        if (p == null && q == null) {
            return true;
        }
        // 其中一個為空，另一個不為空，或值不同，不相同
        if (p == null || q == null || p.val != q.val) {
            return false;
        }

        // 遞迴檢查左右子樹是否相同
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // --- 輔助方法：層次遍歷列印樹 (用於驗證) ---
    public void printTreeLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node == null) { // 打印 null 節點以便於觀察結構
                    System.out.print("null ");
                } else {
                    System.out.print(node.val + " ");
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            System.out.println(); // 換行以表示新的一層
        }
    }


    public static void main(String[] args) {
        TreeMirrorAndSymmetry ops = new TreeMirrorAndSymmetry();

        // --- 測試判斷一棵二元樹是否為對稱樹 ---
        System.out.println("--- 判斷是否為對稱樹 ---");
        // 對稱樹
        //      1
        //     / \
        //    2   2
        //   / \ / \
        //  3  4 4  3
        TreeNode symmetricRoot = new TreeNode(1)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(3)).withRight(new TreeNode(4)))
            .withRight(new TreeNode(2).withLeft(new TreeNode(4)).withRight(new TreeNode(3)));
        System.out.println("樹1是否對稱: " + ops.isSymmetric(symmetricRoot)); // 預期: true

        // 不對稱樹 (值不對稱)
        //      1
        //     / \
        //    2   2
        //   / \ / \
        //  3  4 3  4
        TreeNode asymmetricValueRoot = new TreeNode(1)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(3)).withRight(new TreeNode(4)))
            .withRight(new TreeNode(2).withLeft(new TreeNode(3)).withRight(new TreeNode(4)));
        System.out.println("樹2是否對稱: " + ops.isSymmetric(asymmetricValueRoot)); // 預期: false (因為 2 的左子樹 (3) 和右子樹 (3) 的值是相同的，但位置不對)

        // 不對稱樹 (結構不對稱)
        //      1
        //     / \
        //    2   2
        //     \   \
        //      3   3
        TreeNode asymmetricStructureRoot = new TreeNode(1)
            .withLeft(new TreeNode(2).withRight(new TreeNode(3)))
            .withRight(new TreeNode(2).withRight(new TreeNode(3)));
        System.out.println("樹3是否對稱: " + ops.isSymmetric(asymmetricStructureRoot)); // 預期: false (因為 2 的左子節點都是 null, 而右子節點都有值)

        // 單節點樹
        TreeNode singleNodeRoot = new TreeNode(5);
        System.out.println("單節點樹是否對稱: " + ops.isSymmetric(singleNodeRoot)); // 預期: true

        // 空樹
        TreeNode nullRoot = null;
        System.out.println("空樹是否對稱: " + ops.isSymmetric(nullRoot)); // 預期: true
        System.out.println("---");

        // --- 測試將一棵二元樹轉換為其鏡像樹 ---
        System.out.println("\n--- 轉換為鏡像樹 ---");
        // 原始樹 (來自測試1的symmetricRoot, 但現在作為非對稱的例子來反轉)
        //      1
        //     / \
        //    2   2
        //   / \ / \
        //  3  4 4  3
        TreeNode originalTree = new TreeNode(1)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(3)).withRight(new TreeNode(4)))
            .withRight(new TreeNode(2).withLeft(new TreeNode(4)).withRight(new TreeNode(3)));

        System.out.println("原始樹 (層次遍歷):");
        ops.printTreeLevelOrder(originalTree);

        TreeNode invertedTree = ops.invertTree(originalTree); // 反轉操作會修改原始樹
        System.out.println("反轉後的樹 (層次遍歷):");
        // 預期：
        //      1
        //     / \
        //    2   2
        //   / \ / \
        //  3  4 4  3 (是的，因為原始樹就是對稱的，反轉後看起來一樣)

        // 我們用一個非對稱的樹來測試反轉會更明顯
        // 原始樹：
        //      4
        //     / \
        //    2   7
        //   / \ /
        //  1  3 6
        TreeNode asymmetricOriginal = new TreeNode(4)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(1)).withRight(new TreeNode(3)))
            .withRight(new TreeNode(7).withLeft(new TreeNode(6)));
        System.out.println("\n--- 測試非對稱樹的反轉 ---");
        System.out.println("原始非對稱樹 (層次遍歷):");
        ops.printTreeLevelOrder(asymmetricOriginal);
        TreeNode invertedAsymmetricTree = ops.invertTree(asymmetricOriginal); // 這會修改 originalTree
        System.out.println("反轉後的非對稱樹 (層次遍歷):");
        // 預期：
        //      4
        //     / \
        //    7   2
        //     \ / \
        //      6 3  1
        ops.printTreeLevelOrder(invertedAsymmetricTree);
        System.out.println("---");

        // --- 測試比較兩棵二元樹是否互為鏡像 ---
        System.out.println("\n--- 比較兩棵樹是否互為鏡像 ---");
        // 樹 P:
        //      1
        //     / \
        //    2   3
        //   / \
        //  4   5
        TreeNode treeP = new TreeNode(1)
            .withLeft(new TreeNode(2).withLeft(new TreeNode(4)).withRight(new TreeNode(5)))
            .withRight(new TreeNode(3));

        // 樹 Q (P 的鏡像):
        //      1
        //     / \
        //    3   2
        //       / \
        //      5   4
        TreeNode treeQ = new TreeNode(1)
            .withLeft(new TreeNode(3))
            .withRight(new TreeNode(2).withLeft(new TreeNode(5)).withRight(new TreeNode(4)));
        System.out.println("樹P是否與樹Q互為鏡像: " + ops.areTreesMirror(treeP, treeQ)); // 預期: true

        // 樹 R (不是 P 的鏡像):
        //      1
        //     / \
        //    3   2
        //       / \
        //      4   5
        TreeNode treeR = new TreeNode(1)
            .withLeft(new TreeNode(3))
            .withRight(new TreeNode(2).withLeft(new TreeNode(4)).withRight(new TreeNode(5)));
        System.out.println("樹P是否與樹R互為鏡像: " + ops.areTreesMirror(treeP, treeR)); // 預期: false
        System.out.println("---");

        // --- 測試檢查一棵樹是否為另一棵樹的子樹 ---
        System.out.println("\n--- 檢查是否為子樹 ---");
        // 主樹 (root):
        //      3
        //     / \
        //    4   5
        //   / \
        //  1   2
        TreeNode mainTree = new TreeNode(3)
            .withLeft(new TreeNode(4).withLeft(new TreeNode(1)).withRight(new TreeNode(2)))
            .withRight(new TreeNode(5));

        // 子樹 1 (subRoot1): 是 mainTree 的左子樹
        //    4
        //   / \
        //  1   2
        TreeNode subTree1 = new TreeNode(4)
            .withLeft(new TreeNode(1)).withRight(new TreeNode(2));
        System.out.println("subTree1 是否為 mainTree 的子樹: " + ops.isSubtree(mainTree, subTree1)); // 預期: true

        // 子樹 2 (subRoot2): 是 mainTree 的右子樹
        //    5
        TreeNode subTree2 = new TreeNode(5);
        System.out.println("subTree2 是否為 mainTree 的子樹: " + ops.isSubtree(mainTree, subTree2)); // 預期: true

        // 子樹 3 (subRoot3): 結構和值都相同，但不在 mainTree 中
        //    4
        //   /
        //  1
        TreeNode subTree3 = new TreeNode(4).withLeft(new TreeNode(1));
        System.out.println("subTree3 是否為 mainTree 的子樹: " + ops.isSubtree(mainTree, subTree3)); // 預期: true (因為4-1子樹存在)

        // 子樹 4 (subRoot4): 不同的樹
        //    4
        //   / \
        //  1   6
        TreeNode subTree4 = new TreeNode(4)
            .withLeft(new TreeNode(1)).withRight(new TreeNode(6));
        System.out.println("subTree4 是否為 mainTree 的子樹: " + ops.isSubtree(mainTree, subTree4)); // 預期: false (因為 2 != 6)

        // 子樹 5 (空子樹)
        TreeNode nullSubTree = null;
        System.out.println("空樹是否為 mainTree 的子樹: " + ops.isSubtree(mainTree, nullSubTree)); // 預期: true

        // 主樹為空
        System.out.println("subTree1 是否為空樹的子樹: " + ops.isSubtree(nullRoot, subTree1)); // 預期: false
        System.out.println("---");
    }
}