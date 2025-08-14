/**
 * AVL 樹基礎練習
 * 實作一個簡化版的 AVL 樹，包含基本操作和驗證功能
 */

// AVL 樹節點類別
class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1; // 新節點的高度為1
    }
}

public class AVLBasicExercise {
    private AVLNode root;
    
    // 建構子
    public AVLBasicExercise() {
        this.root = null;
    }
    
    /**
     * 1. 插入節點 (標準 BST 插入，不進行旋轉平衡)
     * 時間複雜度: O(log n) 平均情況, O(n) 最壞情況
     */
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private AVLNode insertRec(AVLNode node, int data) {
        // 基本情況：找到插入位置
        if (node == null) {
            return new AVLNode(data);
        }
        
        // 遞迴插入
        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            // 重複值，不插入
            return node;
        }
        
        // 更新高度
        updateHeight(node);
        
        return node;
    }
    
    /**
     * 2. 搜尋節點
     * 時間複雜度: O(log n) 平均情況, O(n) 最壞情況
     */
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(AVLNode node, int data) {
        // 基本情況：節點不存在
        if (node == null) {
            return false;
        }
        
        // 找到目標節點
        if (data == node.data) {
            return true;
        }
        
        // 遞迴搜尋左或右子樹
        if (data < node.data) {
            return searchRec(node.left, data);
        } else {
            return searchRec(node.right, data);
        }
    }
    
    /**
     * 3. 計算樹的高度
     * 時間複雜度: O(n)
     */
    public int getHeight() {
        return getHeight(root);
    }
    
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    /**
     * 更新節點高度
     */
    private void updateHeight(AVLNode node) {
        if (node != null) {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            node.height = 1 + Math.max(leftHeight, rightHeight);
        }
    }
    
    /**
     * 計算平衡因子 (左子樹高度 - 右子樹高度)
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }
    
    /**
     * 4. 檢查是否為有效的 AVL 樹
     * 時間複雜度: O(n)
     */
    public boolean isValidAVL() {
        return isValidAVL(root);
    }
    
    private boolean isValidAVL(AVLNode node) {
        // 空樹是有效的 AVL 樹
        if (node == null) {
            return true;
        }
        
        // 檢查當前節點的平衡因子
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false; // 平衡因子超出範圍 [-1, 1]
        }
        
        // 遞迴檢查左右子樹
        return isValidAVL(node.left) && isValidAVL(node.right);
    }
    
    /**
     * 中序遍歷 (用於測試和驗證)
     */
    public void inOrderTraversal() {
        System.out.print("中序遍歷: ");
        inOrderRec(root);
        System.out.println();
    }
    
    private void inOrderRec(AVLNode node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.data + " ");
            inOrderRec(node.right);
        }
    }
    
    /**
     * 顯示樹的詳細資訊
     */
    public void displayTreeInfo() {
        System.out.println("=== AVL 樹資訊 ===");
        System.out.println("樹的高度: " + getHeight());
        System.out.println("是否為有效的 AVL 樹: " + (isValidAVL() ? "是" : "否"));
        inOrderTraversal();
        displayBalanceFactors();
    }
    
    /**
     * 顯示每個節點的平衡因子
     */
    public void displayBalanceFactors() {
        System.out.print("節點平衡因子: ");
        displayBalanceFactorsRec(root);
        System.out.println();
    }
    
    private void displayBalanceFactorsRec(AVLNode node) {
        if (node != null) {
            displayBalanceFactorsRec(node.left);
            System.out.print(node.data + "(" + getBalanceFactor(node) + ") ");
            displayBalanceFactorsRec(node.right);
        }
    }
    
    /**
     * 測試主程式
     */
    public static void main(String[] args) {
        AVLBasicExercise avl = new AVLBasicExercise();
        
        System.out.println("=== AVL 樹基礎練習測試 ===\n");
        
        // 測試 1: 平衡的插入
        System.out.println("測試 1: 插入平衡數據");
        int[] balancedData = {10, 5, 15, 3, 7, 12, 18};
        for (int data : balancedData) {
            avl.insert(data);
            System.out.println("插入 " + data);
        }
        avl.displayTreeInfo();
        
        // 測試 2: 搜尋功能
        System.out.println("\n測試 2: 搜尋功能");
        int[] searchTargets = {7, 20, 10, 100};
        for (int target : searchTargets) {
            boolean found = avl.search(target);
            System.out.println("搜尋 " + target + ": " + (found ? "找到" : "未找到"));
        }
        
        // 測試 3: 不平衡的插入
        System.out.println("\n測試 3: 創建不平衡樹");
        AVLBasicExercise unbalancedAVL = new AVLBasicExercise();
        int[] unbalancedData = {1, 2, 3, 4, 5, 6, 7}; // 會形成鏈狀結構
        for (int data : unbalancedData) {
            unbalancedAVL.insert(data);
        }
        unbalancedAVL.displayTreeInfo();
        
        // 測試 4: 邊界情況
        System.out.println("\n測試 4: 邊界情況");
        AVLBasicExercise emptyAVL = new AVLBasicExercise();
        System.out.println("空樹高度: " + emptyAVL.getHeight());
        System.out.println("空樹是否為有效 AVL: " + emptyAVL.isValidAVL());
        System.out.println("搜尋空樹中的元素: " + emptyAVL.search(10));
        
        // 單節點樹測試
        emptyAVL.insert(42);
        System.out.println("單節點樹高度: " + emptyAVL.getHeight());
        System.out.println("單節點樹是否為有效 AVL: " + emptyAVL.isValidAVL());
    }
}