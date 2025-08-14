import java.util.*;

/**
 * 支援版本控制的持久化 AVL 樹實作
 * 使用路徑複製技術實現版本控制，節點不可變，版本間共享未修改的節點
 */
public class PersistentAVLExercise {
    
    /**
     * 不可變的 AVL 樹節點
     */
    private static class AVLNode {
        final int key;
        final int value;
        final AVLNode left;
        final AVLNode right;
        final int height;
        
        public AVLNode(int key, int value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(getHeight(left), getHeight(right));
        }
        
        // 單一節點構造器
        public AVLNode(int key, int value) {
            this(key, value, null, null);
        }
    }
    
    /**
     * AVL 樹的版本，每個版本都有自己的根節點
     */
    public static class AVLVersion {
        private final AVLNode root;
        private final int versionId;
        
        public AVLVersion(AVLNode root, int versionId) {
            this.root = root;
            this.versionId = versionId;
        }
        
        public int getVersionId() {
            return versionId;
        }
        
        /**
         * 搜尋指定的鍵值
         */
        public Integer search(int key) {
            return search(root, key);
        }
        
        private Integer search(AVLNode node, int key) {
            if (node == null) return null;
            
            if (key == node.key) return node.value;
            else if (key < node.key) return search(node.left, key);
            else return search(node.right, key);
        }
        
        /**
         * 中序遍歷
         */
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            inorder(root, result);
            return result;
        }
        
        private void inorder(AVLNode node, List<Integer> result) {
            if (node != null) {
                inorder(node.left, result);
                result.add(node.key);
                inorder(node.right, result);
            }
        }
        
        /**
         * 獲取樹的大小
         */
        public int size() {
            return size(root);
        }
        
        private int size(AVLNode node) {
            if (node == null) return 0;
            return 1 + size(node.left) + size(node.right);
        }
        
        /**
         * 檢查樹的高度平衡性
         */
        public boolean isBalanced() {
            return isBalanced(root);
        }
        
        private boolean isBalanced(AVLNode node) {
            if (node == null) return true;
            
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            
            return Math.abs(leftHeight - rightHeight) <= 1 
                && isBalanced(node.left) 
                && isBalanced(node.right);
        }
    }
    
    // 版本管理
    private List<AVLVersion> versions;
    private int nextVersionId;
    
    public PersistentAVLExercise() {
        this.versions = new ArrayList<>();
        this.nextVersionId = 0;
        // 創建初始空版本
        versions.add(new AVLVersion(null, nextVersionId++));
    }
    
    /**
     * 獲取節點高度
     */
    private static int getHeight(AVLNode node) {
        return node == null ? 0 : node.height;
    }
    
    /**
     * 計算平衡因子
     */
    private static int getBalance(AVLNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    /**
     * 右旋轉
     */
    private static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉，創建新節點
        AVLNode newY = new AVLNode(y.key, y.value, T2, y.right);
        AVLNode newX = new AVLNode(x.key, x.value, x.left, newY);
        
        return newX;
    }
    
    /**
     * 左旋轉
     */
    private static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉，創建新節點
        AVLNode newX = new AVLNode(x.key, x.value, x.left, T2);
        AVLNode newY = new AVLNode(y.key, y.value, newX, y.right);
        
        return newY;
    }
    
    /**
     * 插入節點到指定版本，返回新版本
     */
    public AVLVersion insert(int versionId, int key, int value) {
        if (versionId < 0 || versionId >= versions.size()) {
            throw new IllegalArgumentException("Invalid version ID: " + versionId);
        }
        
        AVLVersion baseVersion = versions.get(versionId);
        AVLNode newRoot = insert(baseVersion.root, key, value);
        AVLVersion newVersion = new AVLVersion(newRoot, nextVersionId++);
        versions.add(newVersion);
        return newVersion;
    }
    
    /**
     * 遞歸插入節點（路徑複製）
     */
    private AVLNode insert(AVLNode node, int key, int value) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(key, value);
        }
        
        if (key < node.key) {
            // 路徑複製：創建新節點，左子樹為遞歸插入結果，右子樹共享原節點
            AVLNode newLeft = insert(node.left, key, value);
            node = new AVLNode(node.key, node.value, newLeft, node.right);
        } else if (key > node.key) {
            // 路徑複製：創建新節點，右子樹為遞歸插入結果，左子樹共享原節點
            AVLNode newRight = insert(node.right, key, value);
            node = new AVLNode(node.key, node.value, node.left, newRight);
        } else {
            // 鍵值相同，更新值
            return new AVLNode(key, value, node.left, node.right);
        }
        
        // 2. 獲取平衡因子
        int balance = getBalance(node);
        
        // 3. 如果節點不平衡，進行旋轉調整
        
        // 左左情況
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }
        
        // 右右情況
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }
        
        // 左右情況
        if (balance > 1 && key > node.left.key) {
            AVLNode newLeft = leftRotate(node.left);
            node = new AVLNode(node.key, node.value, newLeft, node.right);
            return rightRotate(node);
        }
        
        // 右左情況
        if (balance < -1 && key < node.right.key) {
            AVLNode newRight = rightRotate(node.right);
            node = new AVLNode(node.key, node.value, node.left, newRight);
            return leftRotate(node);
        }
        
        // 返回未改變的節點
        return node;
    }
    
    /**
     * 獲取指定版本
     */
    public AVLVersion getVersion(int versionId) {
        if (versionId < 0 || versionId >= versions.size()) {
            throw new IllegalArgumentException("Invalid version ID: " + versionId);
        }
        return versions.get(versionId);
    }
    
    /**
     * 獲取最新版本
     */
    public AVLVersion getLatestVersion() {
        return versions.get(versions.size() - 1);
    }
    
    /**
     * 獲取版本數量
     */
    public int getVersionCount() {
        return versions.size();
    }
    
    /**
     * 列出所有版本的資訊
     */
    public void printVersionHistory() {
        System.out.println("版本歷史：");
        for (AVLVersion version : versions) {
            System.out.printf("版本 %d: 大小 = %d, 平衡 = %s%n", 
                version.getVersionId(), 
                version.size(), 
                version.isBalanced() ? "是" : "否");
        }
    }
    
    // 測試方法
    public static void main(String[] args) {
        PersistentAVLExercise tree = new PersistentAVLExercise();
        
        System.out.println("=== 持久化 AVL 樹測試 ===\n");
        
        // 測試插入操作
        System.out.println("1. 測試插入操作：");
        AVLVersion v1 = tree.insert(0, 10, 100);  // 基於版本0插入
        AVLVersion v2 = tree.insert(1, 20, 200);  // 基於版本1插入
        AVLVersion v3 = tree.insert(2, 30, 300);  // 基於版本2插入
        AVLVersion v4 = tree.insert(3, 25, 250);  // 基於版本3插入
        AVLVersion v5 = tree.insert(4, 40, 400);  // 基於版本4插入
        AVLVersion v6 = tree.insert(5, 50, 500);  // 基於版本5插入
        
        // 顯示所有版本的中序遍歷
        System.out.println("各版本的中序遍歷結果：");
        for (int i = 0; i < tree.getVersionCount(); i++) {
            AVLVersion version = tree.getVersion(i);
            System.out.printf("版本 %d: %s%n", i, version.inorderTraversal());
        }
        System.out.println();
        
        // 測試搜尋功能
        System.out.println("2. 測試搜尋功能：");
        AVLVersion latestVersion = tree.getLatestVersion();
        int[] searchKeys = {10, 25, 35, 50};
        for (int key : searchKeys) {
            Integer result = latestVersion.search(key);
            System.out.printf("搜尋鍵 %d: %s%n", key, result != null ? "找到值 " + result : "未找到");
        }
        System.out.println();
        
        // 測試版本查詢
        System.out.println("3. 測試歷史版本查詢：");
        AVLVersion oldVersion = tree.getVersion(2);  // 獲取版本2
        System.out.printf("版本2的內容: %s%n", oldVersion.inorderTraversal());
        System.out.printf("版本2搜尋鍵20: %s%n", oldVersion.search(20));
        System.out.printf("版本2搜尋鍵40: %s%n", oldVersion.search(40));
        System.out.println();
        
        // 測試平衡性
        System.out.println("4. 測試 AVL 樹平衡性：");
        for (int i = 0; i < tree.getVersionCount(); i++) {
            AVLVersion version = tree.getVersion(i);
            System.out.printf("版本 %d 是否平衡: %s%n", i, version.isBalanced());
        }
        System.out.println();
        
        // 顯示版本歷史
        System.out.println("5. 版本歷史概覽：");
        tree.printVersionHistory();
        System.out.println();
        
        // 測試分支版本（基於舊版本創建新分支）
        System.out.println("6. 測試版本分支：");
        AVLVersion branch1 = tree.insert(2, 15, 150);  // 基於版本2創建分支
        AVLVersion branch2 = tree.insert(2, 35, 350);  // 基於版本2創建另一分支
        
        System.out.printf("原版本2: %s%n", tree.getVersion(2).inorderTraversal());
        System.out.printf("分支版本%d: %s%n", branch1.getVersionId(), branch1.inorderTraversal());
        System.out.printf("分支版本%d: %s%n", branch2.getVersionId(), branch2.inorderTraversal());
        System.out.println();
        
        // 最終版本歷史
        System.out.println("7. 最終版本歷史：");
        tree.printVersionHistory();
        
        // 空間優化展示
        System.out.println("\n8. 空間優化說明：");
        System.out.println("- 每個版本只複製從根到插入點的路徑上的節點");
        System.out.println("- 未修改的子樹在版本間共享");
        System.out.println("- 節點不可變，確保版本安全性");
        System.out.println("- 時間複雜度：O(log n) 插入，O(log n) 查詢");
        System.out.println("- 空間複雜度：每次插入 O(log n) 額外空間");
    }
}