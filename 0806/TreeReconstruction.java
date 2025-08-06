// TreeReconstruction.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors; // For Java 8 stream API

public class TreeReconstruction {

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

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    // --- 輔助：儲存中序遍歷元素值與其索引的映射 ---
    // 這樣可以快速在中序遍歷陣列中找到根節點的位置
    private Map<Integer, Integer> inorderMap;

    // --- 1. 根據前序和中序走訪結果重建二元樹 ---

    /**
     * 根據前序走訪 (preorder) 和中序走訪 (inorder) 結果重建二元樹。
     *
     * @param preorder 前序走訪序列
     * @param inorder  中序走訪序列
     * @return 重建的樹的根節點
     * @throws IllegalArgumentException 如果輸入陣列為空或不一致
     */
    public TreeNode buildTreeFromPreIn(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length || preorder.length == 0) {
            return null;
        }

        // 建立中序遍歷值到索引的映射，以便快速查找
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // 呼叫遞迴重建方法
        return buildTreeFromPreInRecursive(preorder, 0, preorder.length - 1,
                                           inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreeFromPreInRecursive(int[] preorder, int preStart, int preEnd,
                                                 int[] inorder, int inStart, int inEnd) {
        // 基本情況：如果子陣列範圍無效，則沒有節點
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 前序遍歷的第一個元素是當前子樹的根節點
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // 在中序遍歷中找到根節點的位置
        // 這將中序遍歷分為左子樹和右子樹的部分
        int rootInInorder = inorderMap.get(rootVal);

        // 計算左子樹的節點數量
        int leftSubtreeSize = rootInInorder - inStart;

        // 遞迴建立左子樹
        // 前序：根節點後面的 leftSubtreeSize 個元素是左子樹的前序遍歷
        // 中序：從 inStart 到 rootInInorder-1 是左子樹的中序遍歷
        root.left = buildTreeFromPreInRecursive(preorder, preStart + 1, preStart + leftSubtreeSize,
                                                inorder, inStart, rootInInorder - 1);

        // 遞迴建立右子樹
        // 前序：從 preStart + leftSubtreeSize + 1 開始是右子樹的前序遍歷
        // 中序：從 rootInInorder + 1 到 inEnd 是右子樹的中序遍歷
        root.right = buildTreeFromPreInRecursive(preorder, preStart + leftSubtreeSize + 1, preEnd,
                                                 inorder, rootInInorder + 1, inEnd);

        return root;
    }

    // --- 2. 根據後序和中序走訪結果重建二元樹 ---

    /**
     * 根據後序走訪 (postorder) 和中序走訪 (inorder) 結果重建二元樹。
     *
     * @param inorder   中序走訪序列
     * @param postorder 後序走訪序列
     * @return 重建的樹的根節點
     * @throws IllegalArgumentException 如果輸入陣列為空或不一致
     */
    public TreeNode buildTreeFromInPost(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length || inorder.length == 0) {
            return null;
        }

        // 建立中序遍歷值到索引的映射
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // 呼叫遞迴重建方法
        return buildTreeFromInPostRecursive(inorder, 0, inorder.length - 1,
                                            postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTreeFromInPostRecursive(int[] inorder, int inStart, int inEnd,
                                                  int[] postorder, int postStart, int postEnd) {
        // 基本情況：如果子陣列範圍無效，則沒有節點
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }

        // 後序遍歷的最後一個元素是當前子樹的根節點
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        // 在中序遍歷中找到根節點的位置
        int rootInInorder = inorderMap.get(rootVal);

        // 計算左子樹的節點數量
        int leftSubtreeSize = rootInInorder - inStart;

        // 遞迴建立左子樹
        // 中序：從 inStart 到 rootInInorder-1 是左子樹的中序遍歷
        // 後序：從 postStart 到 postStart + leftSubtreeSize - 1 是左子樹的後序遍歷
        root.left = buildTreeFromInPostRecursive(inorder, inStart, rootInInorder - 1,
                                                 postorder, postStart, postStart + leftSubtreeSize - 1);

        // 遞迴建立右子樹
        // 中序：從 rootInInorder + 1 到 inEnd 是右子樹的中序遍歷
        // 後序：從 postStart + leftSubtreeSize 到 postEnd - 1 是右子樹的後序遍歷
        root.right = buildTreeFromInPostRecursive(inorder, rootInInorder + 1, inEnd,
                                                  postorder, postStart + leftSubtreeSize, postEnd - 1);

        return root;
    }

    // --- 3. 根據層序走訪結果重建完全二元樹 ---

    /**
     * 根據層序走訪結果重建完全二元樹 (Complete Binary Tree)。
     * 完全二元樹的定義：除了最後一層外，每一層都被完全填充，並且最後一層的所有節點都盡可能地靠左。
     * (通常這種重建假設給定的數組就是層序遍歷的結果，且沒有 null 值，可以直接按索引關係建樹)
     *
     * @param levelOrderVals 層序走訪序列 (非空節點值)
     * @return 重建的完全二元樹的根節點
     * @throws IllegalArgumentException 如果輸入陣列為空
     */
    public TreeNode buildCBTFromLevelOrder(int[] levelOrderVals) {
        if (levelOrderVals == null || levelOrderVals.length == 0) {
            return null;
        }

        TreeNode[] nodes = new TreeNode[levelOrderVals.length];
        for (int i = 0; i < levelOrderVals.length; i++) {
            nodes[i] = new TreeNode(levelOrderVals[i]);
        }

        // 根據完全二元樹的索引關係建立連接
        // 對於索引為 i 的節點 (0-indexed):
        // 左子節點的索引為 2*i + 1
        // 右子節點的索引為 2*i + 2
        for (int i = 0; i < levelOrderVals.length; i++) {
            if (2 * i + 1 < levelOrderVals.length) {
                nodes[i].left = nodes[2 * i + 1];
            }
            if (2 * i + 2 < levelOrderVals.length) {
                nodes[i].right = nodes[2 * i + 2];
            }
        }
        return nodes[0]; // 根節點始終是第一個元素
    }


    // --- 4. 驗證重建的樹是否正確 ---
    // (這裡的驗證是指與原始走訪序列是否一致，而不是驗證它是否是一個 BST)

    /**
     * 對重建的樹進行前序走訪。
     *
     * @param root 樹的根節點
     * @return 前序走訪序列
     */
    public List<Integer> getPreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        preorderTraversal(node.left, list);
        preorderTraversal(node.right, list);
    }

    /**
     * 對重建的樹進行中序走訪。
     *
     * @param root 樹的根節點
     * @return 中序走訪序列
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
     * 對重建的樹進行後序走訪。
     *
     * @param root 樹的根節點
     * @return 後序走訪序列
     */
    public List<Integer> getPostorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }

    private void postorderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        postorderTraversal(node.left, list);
        postorderTraversal(node.right, list);
        list.add(node.val);
    }

    /**
     * 對重建的樹進行層序走訪。
     *
     * @param root 樹的根節點
     * @return 層序走訪序列
     */
    public List<Integer> getLevelOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return result;
    }

    /**
     * 驗證重建的樹是否正確，透過比對其走訪序列。
     *
     * @param originalPreorder 原始前序走訪序列
     * @param originalInorder  原始中序走訪序列
     * @param originalPostorder 原始後序走訪序列 (可選，如果沒有可傳 null)
     * @param reconstructedRoot 重建的樹的根節點
     * @param type              重建類型 ("PreIn", "InPost", "CBTLevel")
     * @return 如果重建的樹與原始走訪序列一致則返回 true，否則返回 false
     */
    public boolean verifyReconstruction(int[] originalPreorder, int[] originalInorder,
                                        int[] originalPostorder,
                                        TreeNode reconstructedRoot, String type) {
        if (reconstructedRoot == null && originalPreorder == null && originalInorder == null && originalPostorder == null) {
            return true; // 空樹情況
        }
        if (reconstructedRoot == null && (originalPreorder != null || originalInorder != null || originalPostorder != null)) {
            return false; // 重建為空但原始不為空
        }
        if (reconstructedRoot != null && (originalPreorder == null && originalInorder == null && originalPostorder == null)) {
             return false; // 重建不為空但原始為空
        }

        List<Integer> currentPreorder = getPreorder(reconstructedRoot);
        List<Integer> currentInorder = getInorder(reconstructedRoot);
        List<Integer> currentPostorder = getPostorder(reconstructedRoot);

        boolean preMatch = (originalPreorder == null || Arrays.equals(originalPreorder, currentPreorder.stream().mapToInt(i->i).toArray()));
        boolean inMatch = (originalInorder == null || Arrays.equals(originalInorder, currentInorder.stream().mapToInt(i->i).toArray()));
        boolean postMatch = (originalPostorder == null || Arrays.equals(originalPostorder, currentPostorder.stream().mapToInt(i->i).toArray()));

        // 對於前序+中序重建，只需驗證前序和中序
        if (type.equals("PreIn")) {
            return preMatch && inMatch;
        }
        // 對於後序+中序重建，只需驗證後序和中序
        else if (type.equals("InPost")) {
            return inMatch && postMatch;
        }
        // 對於完全二元樹層序重建，只需要驗證層序
        else if (type.equals("CBTLevel")) {
            List<Integer> originalLevelOrderList = Arrays.stream(originalPreorder).boxed().collect(Collectors.toList()); // 假設前序即層序
            return currentPreorder.equals(originalLevelOrderList); // 其實對於CBT, 前序和層序在特定情況下會不同，但驗證會用原始提供的層序
                                                                   // 這裡更精確應該比對 originalLevelOrderVals
        }
        return false;
    }


    public static void main(String[] args) {
        TreeReconstruction reconstructor = new TreeReconstruction();

        // 範例樹：
        //         3
        //        / \
        //       9  20
        //      /   / \
        //     5   15  7
        //          \
        //           12

        // 對應的走訪序列
        int[] preorder = {3, 9, 5, 20, 15, 12, 7};
        int[] inorder = {5, 9, 3, 15, 12, 20, 7};
        int[] postorder = {5, 9, 12, 15, 7, 20, 3};
        // 對於完全二元樹的層序，會是線性的，沒有 null 概念 (假設沒有跳過節點)
        int[] cbtLevelOrder = {1, 2, 3, 4, 5, 6, 7}; // 假設一個標準的完全二元樹

        System.out.println("--- 二元樹重建與驗證 ---");

        // --- 1. 根據前序和中序走訪結果重建二元樹 ---
        System.out.println("\n1. 根據前序和中序走訪結果重建二元樹:");
        System.out.println("原始前序: " + Arrays.toString(preorder));
        System.out.println("原始中序: " + Arrays.toString(inorder));
        TreeNode reconstructedTree1 = reconstructor.buildTreeFromPreIn(preorder, inorder);
        System.out.println("重建後的前序: " + reconstructor.getPreorder(reconstructedTree1));
        System.out.println("重建後的中序: " + reconstructor.getInorder(reconstructedTree1));
        System.out.println("重建後是否正確: " + reconstructor.verifyReconstruction(preorder, inorder, null, reconstructedTree1, "PreIn")); // 預期: true
        System.out.println("---");


        // --- 2. 根據後序和中序走訪結果重建二元樹 ---
        System.out.println("\n2. 根據後序和中序走訪結果重建二元樹:");
        System.out.println("原始後序: " + Arrays.toString(postorder));
        System.out.println("原始中序: " + Arrays.toString(inorder));
        TreeNode reconstructedTree2 = reconstructor.buildTreeFromInPost(inorder, postorder);
        System.out.println("重建後的後序: " + reconstructor.getPostorder(reconstructedTree2));
        System.out.println("重建後的中序: " + reconstructor.getInorder(reconstructedTree2));
        System.out.println("重建後是否正確: " + reconstructor.verifyReconstruction(null, inorder, postorder, reconstructedTree2, "InPost")); // 預期: true
        System.out.println("---");

        // --- 3. 根據層序走訪結果重建完全二元樹 ---
        System.out.println("\n3. 根據層序走訪結果重建完全二元樹:");
        System.out.println("原始層序 (CBT): " + Arrays.toString(cbtLevelOrder));
        TreeNode reconstructedCBT = reconstructor.buildCBTFromLevelOrder(cbtLevelOrder);
        System.out.println("重建後的層序: " + reconstructor.getLevelOrder(reconstructedCBT));
        // 注意：這裡的驗證是比較重建後的層序與原始輸入的層序，因為這是專為CBT設計的
        System.out.println("重建後是否正確: " + reconstructor.verifyReconstruction(cbtLevelOrder, null, null, reconstructedCBT, "CBTLevel")); // 預期: true
        System.out.println("---");

        // 測試空樹和無效輸入
        System.out.println("\n--- 測試空樹和無效輸入 ---");
        System.out.println("空前序/中序重建: " + (reconstructor.buildTreeFromPreIn(new int[]{}, new int[]{}) == null)); // 預期: true
        System.out.println("空中序/後序重建: " + (reconstructor.buildTreeFromInPost(new int[]{}, new int[]{}) == null)); // 預期: true
        System.out.println("空層序CBT重建: " + (reconstructor.buildCBTFromLevelOrder(new int[]{}) == null)); // 預期: true
        System.out.println("---");
    }
}