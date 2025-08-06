import java.util.*;

public class AdvancedBSTOperations {
    
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        
        public TreeNode(int data) {
            this.data = data;
        }
    }
    
    /**
     * 尋找第k小的元素
     * 使用中序走訪的特性
     */
    public static int kthSmallest(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>();
        inOrderForKth(root, result, k);
        
        if (result.size() >= k) {
            return result.get(k - 1);
        } else {
            throw new IllegalArgumentException("k 超出範圍");
        }
    }
    
    private static void inOrderForKth(TreeNode root, List<Integer> result, int k) {
        if (root == null || result.size() >= k) return;
        
        inOrderForKth(root.left, result, k);
        if (result.size() < k) {
            result.add(root.data);
        }
        inOrderForKth(root.right, result, k);
    }
    
    /**
     * 範圍查詢：找出在 [low, high] 範圍內的所有值
     */
    public static List<Integer> rangeBST(TreeNode root, int low, int high) {
        List<Integer> result = new ArrayList<>();
        rangeHelper(root, low, high, result);
        return result;
    }
    
    private static void rangeHelper(TreeNode root, int low, int high, List<Integer> result) {
        if (root == null) return;
        
        // 如果當前值在範圍內，加入結果
        if (root.data >= low && root.data <= high) {
            result.add(root.data);
        }
        
        // 根據BST特性決定是否需要探索子樹
        if (root.data > low) {
            rangeHelper(root.left, low, high, result);
        }
        if (root.data < high) {
            rangeHelper(root.right, low, high, result);
        }
    }
    
    /**
     * 計算BST中兩個節點的最低公共祖先
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, int p, int q) {
        if (root == null) return null;
        
        // 如果兩個值都小於根節點，LCA在左子樹
        if (p < root.data && q < root.data) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // 如果兩個值都大於根節點，LCA在右子樹
        if (p > root.data && q > root.data) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // 如果一個在左邊一個在右邊，當前節點就是LCA
        return root;
    }
    
    /**
     * 將排序陣列轉換為平衡BST
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        return arrayToBSTHelper(nums, 0, nums.length - 1);
    }
    
    private static TreeNode arrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        
        root.left = arrayToBSTHelper(nums, left, mid - 1);
        root.right = arrayToBSTHelper(nums, mid + 1, right);
        
        return root;
    }
    
    /**
     * 檢查BST是否平衡
     * 平衡：任意節點的左右子樹高度差不超過1
     */
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }
    
    private static int checkBalance(TreeNode root) {
        if (root == null) return 0;
        
        int leftHeight = checkBalance(root.left);
        if (leftHeight == -1) return -1;
        
        int rightHeight = checkBalance(root.right);
        if (rightHeight == -1) return -1;
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // 不平衡
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /**
     * 輔助方法：插入節點建立BST
     */
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        
        if (val < root.data) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        
        return root;
    }
    
    /**
     * 輔助方法：中序走訪
     */
    public static void inOrderTraversal(TreeNode root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.data + " ");
            inOrderTraversal(root.right);
        }
    }
    
    public static void main(String[] args) {
        // 建立測試BST
        TreeNode root = null;
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        
        for (int val : values) {
            root = insertIntoBST(root, val);
        }
        
        System.out.println("=== BST進階操作示範 ===");
        System.out.print("原始BST（中序走訪）: ");
        inOrderTraversal(root);
        System.out.println();
        
        // 第k小元素
        System.out.println("\n=== 第k小元素 ===");
        for (int k = 1; k <= 5; k++) {
            try {
                int kthElement = kthSmallest(root, k);
                System.out.printf("第 %d 小的元素: %d\n", k, kthElement);
            } catch (IllegalArgumentException e) {
                System.out.printf("第 %d 小的元素: %s\n", k, e.getMessage());
            }
        }
        
        // 範圍查詢
        System.out.println("\n=== 範圍查詢 ===");
        List<Integer> rangeResult = rangeBST(root, 25, 45);
        System.out.println("範圍 [25, 45] 內的元素: " + rangeResult);
        
        rangeResult = rangeBST(root, 15, 35);
        System.out.println("範圍 [15, 35] 內的元素: " + rangeResult);
        
        // 最低公共祖先
        System.out.println("\n=== 最低公共祖先 ===");
        TreeNode lca1 = lowestCommonAncestor(root, 10, 25);
        System.out.printf("節點 10 和 25 的LCA: %d\n", lca1.data);
        
        TreeNode lca2 = lowestCommonAncestor(root, 35, 45);
        System.out.printf("節點 35 和 45 的LCA: %d\n", lca2.data);
        
        TreeNode lca3 = lowestCommonAncestor(root, 20, 70);
        System.out.printf("節點 20 和 70 的LCA: %d\n", lca3.data);
        
        // 排序陣列轉BST
        System.out.println("\n=== 排序陣列轉平衡BST ===");
        int[] sortedArray = {1, 3, 5, 7, 9, 11, 13, 15, 17};
        TreeNode balancedRoot = sortedArrayToBST(sortedArray);
        System.out.print("由排序陣列建立的平衡BST: ");
        inOrderTraversal(balancedRoot);
        System.out.println();
        
        // 平衡檢查
        System.out.println("\n=== 平衡檢查 ===");
        System.out.printf("原始BST是否平衡: %s\n", isBalanced(root));
        System.out.printf("由陣列建立的BST是否平衡: %s\n", isBalanced(balancedRoot));
        
        // 建立不平衡的BST測試
        TreeNode unbalancedRoot = new TreeNode(1);
        unbalancedRoot.right = new TreeNode(2);
        unbalancedRoot.right.right = new TreeNode(3);
        unbalancedRoot.right.right.right = new TreeNode(4);
        System.out.printf("鏈狀BST是否平衡: %s\n", isBalanced(unbalancedRoot));
    }
}
