/**
 * AVL 樹刪除操作練習
 * 實作完整的 AVL 樹刪除功能，包含三種刪除情況和自動重新平衡
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
    
    @Override
    public String toString() {
        return "Node(" + data + ", h=" + height + ")";
    }
}

public class AVLDeleteExercise {
    private AVLNode root;
    
    // 建構子
    public AVLDeleteExercise() {
        this.root = null;
    }
    
    // 獲取節點高度
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    // 更新節點高度
    private void updateHeight(AVLNode node) {
        if (node != null) {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            node.height = 1 + Math.max(leftHeight, rightHeight);
        }
    }
    
    // 計算平衡因子
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }
    
    // 右旋操作
    private AVLNode rightRotate(AVLNode y) {
        System.out.println("執行右旋，節點: " + y.data);
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    // 左旋操作
    private AVLNode leftRotate(AVLNode x) {
        System.out.println("執行左旋，節點: " + x.data);
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    // 左右旋操作
    private AVLNode leftRightRotate(AVLNode node) {
        System.out.println("執行左右旋，節點: " + node.data);
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }
    
    // 右左旋操作
    private AVLNode rightLeftRotate(AVLNode node) {
        System.out.println("執行右左旋，節點: " + node.data);
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }
    
    /**
     * AVL 樹插入操作（用於建立測試樹）
     */
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private AVLNode insertRec(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            return node; // 重複值不插入
        }
        
        // 2. 更新高度和重新平衡
        return rebalance(node, data, "插入");
    }
    
    /**
     * AVL 樹刪除操作 - 主要功能
     */
    public boolean delete(int data) {
        int initialSize = size();
        root = deleteRec(root, data);
        int finalSize = size();
        
        boolean deleted = (finalSize < initialSize);
        if (deleted) {
            System.out.println("成功刪除節點: " + data);
        } else {
            System.out.println("節點 " + data + " 不存在，無法刪除");
        }
        
        return deleted;
    }
    
    private AVLNode deleteRec(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) {
            return node; // 節點不存在
        }
        
        if (data < node.data) {
            System.out.println("在左子樹中尋找節點 " + data);
            node.left = deleteRec(node.left, data);
        } else if (data > node.data) {
            System.out.println("在右子樹中尋找節點 " + data);
            node.right = deleteRec(node.right, data);
        } else {
            // 找到要刪除的節點
            System.out.println("找到要刪除的節點: " + data);
            node = handleNodeDeletion(node);
            if (node == null) {
                return null; // 節點已被刪除
            }
        }
        
        // 2. 更新高度和重新平衡
        return rebalance(node, data, "刪除");
    }
    
    /**
     * 處理節點刪除的三種情況
     */
    private AVLNode handleNodeDeletion(AVLNode node) {
        // 情況 1: 葉子節點（沒有子節點）
        if (node.left == null && node.right == null) {
            System.out.println("情況 1: 刪除葉子節點 " + node.data);
            return null;
        }
        
        // 情況 2: 只有一個子節點
        if (node.left == null) {
            System.out.println("情況 2: 刪除只有右子節點的節點 " + node.data);
            return node.right;
        }
        if (node.right == null) {
            System.out.println("情況 2: 刪除只有左子節點的節點 " + node.data);
            return node.left;
        }
        
        // 情況 3: 有兩個子節點
        System.out.println("情況 3: 刪除有兩個子節點的節點 " + node.data);
        
        // 找到右子樹的最小值（後繼節點）
        AVLNode successor = findMin(node.right);
        System.out.println("找到後繼節點: " + successor.data);
        
        // 用後繼節點的值替換當前節點的值
        node.data = successor.data;
        System.out.println("將節點值替換為: " + successor.data);
        
        // 遞迴刪除後繼節點
        System.out.println("遞迴刪除後繼節點 " + successor.data);
        node.right = deleteRec(node.right, successor.data);
        
        return node;
    }
    
    /**
     * 找到子樹中的最小節點（最左節點）
     */
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * 找到子樹中的最大節點（最右節點）
     */
    private AVLNode findMax(AVLNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    /**
     * 重新平衡節點
     */
    private AVLNode rebalance(AVLNode node, int data, String operation) {
        // 更新高度
        updateHeight(node);
        
        // 獲取平衡因子
        int balance = getBalanceFactor(node);
        
        if (Math.abs(balance) > 1) {
            System.out.println(operation + "後檢測到不平衡，節點 " + node.data + " 平衡因子: " + balance);
        }
        
        // Left Left Case
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            System.out.println("檢測到 LL 不平衡");
            return rightRotate(node);
        }
        
        // Left Right Case
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            System.out.println("檢測到 LR 不平衡");
            return leftRightRotate(node);
        }
        
        // Right Right Case
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            System.out.println("檢測到 RR 不平衡");
            return leftRotate(node);
        }
        
        // Right Left Case
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            System.out.println("檢測到 RL 不平衡");
            return rightLeftRotate(node);
        }
        
        return node;
    }
    
    /**
     * 搜尋節點
     */
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(AVLNode node, int data) {
        if (node == null) {
            return false;
        }
        
        if (data == node.data) {
            return true;
        }
        
        return data < node.data ? 
            searchRec(node.left, data) : 
            searchRec(node.right, data);
    }
    
    /**
     * 計算樹的節點數量
     */
    public int size() {
        return sizeRec(root);
    }
    
    private int sizeRec(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }
    
    /**
     * 檢查是否為有效的 AVL 樹
     */
    public boolean isValidAVL() {
        return isValidAVLRec(root);
    }
    
    private boolean isValidAVLRec(AVLNode node) {
        if (node == null) {
            return true;
        }
        
        int balance = getBalanceFactor(node);
        if (Math.abs(balance) > 1) {
            return false;
        }
        
        return isValidAVLRec(node.left) && isValidAVLRec(node.right);
    }
    
    /**
     * 中序遍歷
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
     * 顯示樹的結構
     */
    public void displayTree() {
        if (root == null) {
            System.out.println("樹為空");
            return;
        }
        System.out.println("樹的結構:");
        displayTreeRec(root, "", true);
    }
    
    private void displayTreeRec(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                node.data + " (h=" + node.height + ", bf=" + getBalanceFactor(node) + ")");
            
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    displayTreeRec(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    displayTreeRec(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    /**
     * 顯示樹的詳細資訊
     */
    public void displayTreeInfo() {
        System.out.println("=== AVL 樹資訊 ===");
        System.out.println("節點數量: " + size());
        System.out.println("樹的高度: " + getHeight(root));
        System.out.println("是否為有效的 AVL 樹: " + (isValidAVL() ? "是" : "否"));
        inOrderTraversal();
        displayTree();
        System.out.println();
    }
    
    /**
     * 測試主程式
     */
    public static void main(String[] args) {
        System.out.println("=== AVL 樹刪除操作練習 ===\n");
        
        AVLDeleteExercise avl = new AVLDeleteExercise();
        
        // 建立測試樹
        System.out.println("建立測試樹，插入順序: 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45");
        int[] insertData = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int data : insertData) {
            System.out.println("插入: " + data);
            avl.insert(data);
        }
        
        System.out.println("\n初始樹:");
        avl.displayTreeInfo();
        
        // 測試 1: 刪除葉子節點
        System.out.println("=== 測試 1: 刪除葉子節點 ===");
        System.out.println("刪除葉子節點 10:");
        avl.delete(10);
        avl.displayTreeInfo();
        
        System.out.println("刪除葉子節點 45:");
        avl.delete(45);
        avl.displayTreeInfo();
        
        // 測試 2: 刪除只有一個子節點的節點
        System.out.println("=== 測試 2: 刪除只有一個子節點的節點 ===");
        System.out.println("刪除只有左子節點的節點 25:");
        avl.delete(25);
        avl.displayTreeInfo();
        
        // 測試 3: 刪除有兩個子節點的節點
        System.out.println("=== 測試 3: 刪除有兩個子節點的節點 ===");
        System.out.println("刪除有兩個子節點的節點 30:");
        avl.delete(30);
        avl.displayTreeInfo();
        
        System.out.println("刪除根節點 50:");
        avl.delete(50);
        avl.displayTreeInfo();
        
        // 測試 4: 刪除導致不平衡的情況
        System.out.println("=== 測試 4: 創建會導致不平衡的刪除場景 ===");
        AVLDeleteExercise avl2 = new AVLDeleteExercise();
        
        // 插入數據創建特定結構
        int[] data2 = {10, 5, 15, 2, 7, 12, 20, 1, 3, 6, 8};
        for (int data : data2) {
            avl2.insert(data);
        }
        
        System.out.println("初始樹:");
        avl2.displayTreeInfo();
        
        System.out.println("刪除節點 5 (會導致不平衡):");
        avl2.delete(5);
        avl2.displayTreeInfo();
        
        // 測試 5: 邊界情況
        System.out.println("=== 測試 5: 邊界情況 ===");
        
        // 刪除不存在的節點
        System.out.println("嘗試刪除不存在的節點 999:");
        avl.delete(999);
        
        // 刪除空樹中的節點
        AVLDeleteExercise emptyAVL = new AVLDeleteExercise();
        System.out.println("嘗試從空樹中刪除節點 10:");
        emptyAVL.delete(10);
        
        // 刪除單節點樹
        AVLDeleteExercise singleNodeAVL = new AVLDeleteExercise();
        singleNodeAVL.insert(42);
        System.out.println("單節點樹刪除前:");
        singleNodeAVL.displayTreeInfo();
        
        System.out.println("刪除單節點樹的根節點:");
        singleNodeAVL.delete(42);
        singleNodeAVL.displayTreeInfo();
        
        // 測試 6: 連續刪除測試
        System.out.println("=== 測試 6: 連續刪除所有節點 ===");
        AVLDeleteExercise avl3 = new AVLDeleteExercise();
        int[] data3 = {50, 30, 70, 20, 40, 60, 80};
        
        for (int data : data3) {
            avl3.insert(data);
        }
        
        System.out.println("初始樹:");
        avl3.displayTreeInfo();
        
        // 按不同順序刪除所有節點
        int[] deleteOrder = {20, 80, 30, 70, 40, 60, 50};
        for (int data : deleteOrder) {
            System.out.println("--- 刪除 " + data + " ---");
            avl3.delete(data);
            if (avl3.size() > 0) {
                avl3.displayTreeInfo();
            } else {
                System.out.println("樹已為空");
                break;
            }
        }
        
        System.out.println("\n=== 所有測試完成 ===");
    }
}