/**
 * AVL 樹旋轉操作練習
 * 實作四種旋轉操作並測試各種不平衡情況
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

public class AVLRotationExercise {
    private AVLNode root;
    
    // 建構子
    public AVLRotationExercise() {
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
    
    /**
     * 1. 右旋操作 (Right Rotation)
     * 用於修復左-左(LL)不平衡
     * 
     *     y                x
     *    / \              / \
     *   x   T3    -->    T1  y
     *  / \                  / \
     * T1  T2               T2  T3
     * 
     * 時間複雜度: O(1)
     */
    public AVLNode rightRotate(AVLNode y) {
        System.out.println("執行右旋操作，根節點: " + y.data);
        
        // 儲存節點
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉
        x.right = y;
        y.left = T2;
        
        // 更新高度（先更新y，再更新x）
        updateHeight(y);
        updateHeight(x);
        
        System.out.println("右旋完成，新根節點: " + x.data);
        return x; // 返回新的根節點
    }
    
    /**
     * 2. 左旋操作 (Left Rotation)
     * 用於修復右-右(RR)不平衡
     * 
     *   x                  y
     *  / \                / \
     * T1  y      -->     x   T3
     *    / \            / \
     *   T2  T3         T1  T2
     * 
     * 時間複雜度: O(1)
     */
    public AVLNode leftRotate(AVLNode x) {
        System.out.println("執行左旋操作，根節點: " + x.data);
        
        // 儲存節點
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉
        y.left = x;
        x.right = T2;
        
        // 更新高度（先更新x，再更新y）
        updateHeight(x);
        updateHeight(y);
        
        System.out.println("左旋完成，新根節點: " + y.data);
        return y; // 返回新的根節點
    }
    
    /**
     * 3. 左右旋操作 (Left-Right Rotation)
     * 用於修復左-右(LR)不平衡
     * 
     * 第一步：對左子樹進行左旋
     * 第二步：對根節點進行右旋
     * 
     *     z            z              y
     *    / \          / \            / \
     *   x   T4  -->  y   T4   -->   x   z
     *  / \          / \            / \ / \
     * T1  y        x   T3         T1 T2 T3 T4
     *    / \      / \
     *   T2  T3   T1  T2
     */
    public AVLNode leftRightRotate(AVLNode z) {
        System.out.println("執行左右旋操作，根節點: " + z.data);
        System.out.println("第一步：對左子樹 " + z.left.data + " 進行左旋");
        
        // 第一步：對左子樹進行左旋
        z.left = leftRotate(z.left);
        
        System.out.println("第二步：對根節點 " + z.data + " 進行右旋");
        
        // 第二步：對根節點進行右旋
        AVLNode result = rightRotate(z);
        
        System.out.println("左右旋完成，新根節點: " + result.data);
        return result;
    }
    
    /**
     * 4. 右左旋操作 (Right-Left Rotation)
     * 用於修復右-左(RL)不平衡
     * 
     * 第一步：對右子樹進行右旋
     * 第二步：對根節點進行左旋
     * 
     *   x           x               y
     *  / \         / \             / \
     * T1  z  -->  T1  y    -->    x   z
     *    / \         / \         / \ / \
     *   y   T4      T2  z       T1 T2 T3 T4
     *  / \             / \
     * T2  T3          T3  T4
     */
    public AVLNode rightLeftRotate(AVLNode x) {
        System.out.println("執行右左旋操作，根節點: " + x.data);
        System.out.println("第一步：對右子樹 " + x.right.data + " 進行右旋");
        
        // 第一步：對右子樹進行右旋
        x.right = rightRotate(x.right);
        
        System.out.println("第二步：對根節點 " + x.data + " 進行左旋");
        
        // 第二步：對根節點進行左旋
        AVLNode result = leftRotate(x);
        
        System.out.println("右左旋完成，新根節點: " + result.data);
        return result;
    }
    
    /**
     * AVL 插入操作（包含自動平衡）
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
        
        // 2. 更新高度
        updateHeight(node);
        
        // 3. 獲取平衡因子
        int balance = getBalanceFactor(node);
        
        System.out.println("節點 " + node.data + " 的平衡因子: " + balance);
        
        // 4. 如果不平衡，進行相應的旋轉
        // Left Left Case (LL)
        if (balance > 1 && data < node.left.data) {
            System.out.println("檢測到 LL 不平衡");
            return rightRotate(node);
        }
        
        // Right Right Case (RR)
        if (balance < -1 && data > node.right.data) {
            System.out.println("檢測到 RR 不平衡");
            return leftRotate(node);
        }
        
        // Left Right Case (LR)
        if (balance > 1 && data > node.left.data) {
            System.out.println("檢測到 LR 不平衡");
            return leftRightRotate(node);
        }
        
        // Right Left Case (RL)
        if (balance < -1 && data < node.right.data) {
            System.out.println("檢測到 RL 不平衡");
            return rightLeftRotate(node);
        }
        
        return node;
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
        System.out.println("樹的結構:");
        displayTreeRec(root, "", true);
    }
    
    private void displayTreeRec(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.data + " (h=" + node.height + ", bf=" + getBalanceFactor(node) + ")");
            
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
     * 手動創建特定不平衡情況進行測試
     */
    public static AVLNode createLLCase() {
        // 創建 LL 不平衡情況
        //     30
        //    /
        //   20
        //  /
        // 10
        AVLNode root = new AVLNode(30);
        root.left = new AVLNode(20);
        root.left.left = new AVLNode(10);
        
        // 更新高度
        root.left.left.height = 1;
        root.left.height = 2;
        root.height = 3;
        
        return root;
    }
    
    public static AVLNode createRRCase() {
        // 創建 RR 不平衡情況
        // 10
        //   \
        //    20
        //     \
        //      30
        AVLNode root = new AVLNode(10);
        root.right = new AVLNode(20);
        root.right.right = new AVLNode(30);
        
        // 更新高度
        root.right.right.height = 1;
        root.right.height = 2;
        root.height = 3;
        
        return root;
    }
    
    public static AVLNode createLRCase() {
        // 創建 LR 不平衡情況
        //   30
        //  /
        // 10
        //   \
        //    20
        AVLNode root = new AVLNode(30);
        root.left = new AVLNode(10);
        root.left.right = new AVLNode(20);
        
        // 更新高度
        root.left.right.height = 1;
        root.left.height = 2;
        root.height = 3;
        
        return root;
    }
    
    public static AVLNode createRLCase() {
        // 創建 RL 不平衡情況
        // 10
        //   \
        //    30
        //   /
        //  20
        AVLNode root = new AVLNode(10);
        root.right = new AVLNode(30);
        root.right.left = new AVLNode(20);
        
        // 更新高度
        root.right.left.height = 1;
        root.right.height = 2;
        root.height = 3;
        
        return root;
    }
    
    /**
     * 測試主程式
     */
    public static void main(String[] args) {
        System.out.println("=== AVL 樹旋轉操作練習 ===\n");
        
        AVLRotationExercise avl = new AVLRotationExercise();
        
        // 測試 1: LL 情況 - 需要右旋
        System.out.println("測試 1: Left-Left (LL) 不平衡 - 需要右旋");
        System.out.println("插入順序: 30, 20, 10");
        AVLNode llRoot = createLLCase();
        System.out.println("旋轉前:");
        AVLRotationExercise temp = new AVLRotationExercise();
        temp.root = llRoot;
        temp.displayTree();
        
        AVLNode balancedLL = avl.rightRotate(llRoot);
        temp.root = balancedLL;
        System.out.println("旋轉後:");
        temp.displayTree();
        System.out.println();
        
        // 測試 2: RR 情況 - 需要左旋
        System.out.println("測試 2: Right-Right (RR) 不平衡 - 需要左旋");
        System.out.println("插入順序: 10, 20, 30");
        AVLNode rrRoot = createRRCase();
        System.out.println("旋轉前:");
        temp.root = rrRoot;
        temp.displayTree();
        
        AVLNode balancedRR = avl.leftRotate(rrRoot);
        temp.root = balancedRR;
        System.out.println("旋轉後:");
        temp.displayTree();
        System.out.println();
        
        // 測試 3: LR 情況 - 需要左右旋
        System.out.println("測試 3: Left-Right (LR) 不平衡 - 需要左右旋");
        System.out.println("插入順序: 30, 10, 20");
        AVLNode lrRoot = createLRCase();
        System.out.println("旋轉前:");
        temp.root = lrRoot;
        temp.displayTree();
        
        AVLNode balancedLR = avl.leftRightRotate(lrRoot);
        temp.root = balancedLR;
        System.out.println("旋轉後:");
        temp.displayTree();
        System.out.println();
        
        // 測試 4: RL 情況 - 需要右左旋
        System.out.println("測試 4: Right-Left (RL) 不平衡 - 需要右左旋");
        System.out.println("插入順序: 10, 30, 20");
        AVLNode rlRoot = createRLCase();
        System.out.println("旋轉前:");
        temp.root = rlRoot;
        temp.displayTree();
        
        AVLNode balancedRL = avl.rightLeftRotate(rlRoot);
        temp.root = balancedRL;
        System.out.println("旋轉後:");
        temp.displayTree();
        System.out.println();
        
        // 測試 5: 完整的 AVL 插入過程
        System.out.println("測試 5: 完整的 AVL 插入過程");
        AVLRotationExercise completeAVL = new AVLRotationExercise();
        int[] insertOrder = {10, 20, 30, 40, 50, 25};
        
        for (int data : insertOrder) {
            System.out.println("\n--- 插入 " + data + " ---");
            completeAVL.insert(data);
            completeAVL.displayTree();
        }
        
        System.out.println("\n最終樹的中序遍歷:");
        completeAVL.inOrderTraversal();
        
        // 測試 6: 邊界情況
        System.out.println("\n測試 6: 邊界情況");
        
        // 空節點旋轉
        System.out.println("空節點右旋測試:");
        try {
            AVLNode nullResult = avl.rightRotate(null);
            System.out.println("結果: " + nullResult);
        } catch (Exception e) {
            System.out.println("錯誤: " + e.getMessage());
        }
        
        // 單節點旋轉
        System.out.println("單節點旋轉測試:");
        AVLNode singleNode = new AVLNode(42);
        try {
            AVLNode singleResult = avl.rightRotate(singleNode);
            System.out.println("結果: " + singleResult);
        } catch (Exception e) {
            System.out.println("錯誤: " + e.getMessage());
        }
        
        // 只有左子樹的右旋
        System.out.println("只有左子樹的右旋:");
        AVLNode leftOnly = new AVLNode(20);
        leftOnly.left = new AVLNode(10);
        leftOnly.height = 2;
        leftOnly.left.height = 1;
        
        AVLNode leftOnlyResult = avl.rightRotate(leftOnly);
        temp.root = leftOnlyResult;
        temp.displayTree();
    }
}