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
    
    // 更新節點高度
    public void updateHeight() {
        this.height = 1 + Math.max(getHeight(left), getHeight(right));
    }
    
    // 獲取節點高度（處理空節點）
    public static int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    // 計算平衡因子
    public int getBalanceFactor() {
        return getHeight(left) - getHeight(right);
    }

    public int getBalance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBalance'");
    }
}

// AVL 樹旋轉操作類別
public class AVLRotations {
    
    // 右旋操作 (Right Rotation)
    // 用於處理左-左（LL）不平衡情況
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉
        x.right = y;
        y.left = T2;
        
        // 更新高度（先更新y，再更新x）
        y.updateHeight();
        x.updateHeight();
        
        return x; // 返回新的根節點
    }
    
    // 左旋操作 (Left Rotation)
    // 用於處理右-右（RR）不平衡情況
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉
        y.left = x;
        x.right = T2;
        
        // 更新高度（先更新x，再更新y）
        x.updateHeight();
        y.updateHeight();
        
        return y; // 返回新的根節點
    }
    
    // 左-右旋轉 (Left-Right Rotation)
    // 用於處理左-右（LR）不平衡情況
    public static AVLNode leftRightRotate(AVLNode node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }
    
    // 右-左旋轉 (Right-Left Rotation)
    // 用於處理右-左（RL）不平衡情況
    public static AVLNode rightLeftRotate(AVLNode node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }
    
    // 測試方法
    public static void main(String[] args) {
        // 創建測試節點
        AVLNode root = new AVLNode(10);
        root.left = new AVLNode(5);
        root.left.left = new AVLNode(3);
        
        // 更新高度
        root.left.left.updateHeight();
        root.left.updateHeight();
        root.updateHeight();
        
        System.out.println("旋轉前根節點: " + root.data);
        System.out.println("平衡因子: " + root.getBalanceFactor());
        
        // 執行右旋
        if (root.getBalanceFactor() > 1) {
            root = rightRotate(root);
            System.out.println("右旋後根節點: " + root.data);
        }
    }
}

// 完整的 AVL 樹實現
class AVLTree {
    private AVLNode root;
    
    // 插入節點
    public void insert(int data) {
        root = insert(root, data);
    }
    
    private AVLNode insert(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        } else {
            return node; // 重複值不插入
        }
        
        // 2. 更新高度
        node.updateHeight();
        
        // 3. 獲取平衡因子
        int balance = node.getBalanceFactor();
        
        // 4. 如果不平衡，進行旋轉
        // Left Left Case
        if (balance > 1 && data < node.left.data) {
            return AVLRotations.rightRotate(node);
        }
        
        // Right Right Case
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }
        
        // Left Right Case
        if (balance > 1 && data > node.left.data) {
            return AVLRotations.leftRightRotate(node);
        }
        
        // Right Left Case
        if (balance < -1 && data < node.right.data) {
            return AVLRotations.rightLeftRotate(node);
        }
        
        return node;
    }
    
    // 中序遍歷
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }
    
    private void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }
}