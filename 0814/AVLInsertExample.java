import java.util.*;

/**
 * 支援版本控制的持久化 AVL 樹實作
 * 使用路徑複製技術實現版本控制，節點不可變，版本間共享未修改的節點
 */
public class AVLInsertExample {
    
    /**
     * 不可變的 AVL 樹節點
     */
    private static class AVLNode {
        final int data;
        final AVLNode left;
        final AVLNode right;
        final int height;
        
        public AVLNode(int data, AVLNode left, AVLNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(getHeight(left), getHeight(right));
        }
        
        // 單一節點構造器
        public AVLNode(int data) {
            this(data, null, null);
        }
        
        // 獲取平衡因子
        public int getBalance() {
            return getHeight(left) - getHeight(right);
        }
    }
    
    /**
     * AVL 旋轉操作類別
     */
    private static class AVLRotations {
        
        // 右旋轉 (用於 Left-Left 情況)
        public static AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;
            
            // 執行旋轉，創建新節點（保持不可變性）
            AVLNode newY = new AVLNode(y.data, T2, y.right);
            AVLNode newX = new AVLNode(x.data, x.left, newY);
            
            return newX;
        }
        
        // 左旋轉 (用於 Right-Right 情況)
        public static AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;
            
            // 執行旋轉，創建新節點（保持不可變性）
            AVLNode newX = new AVLNode(x.data, x.left, T2);
            AVLNode newY = new AVLNode(y.data, newX, y.right);
            
            return newY;
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
         * 時間複雜度: O(log n), 空間複雜度: O(log n)
         */
        public boolean search(int data) {
            return searchNode(root, data);
        }
        
        private boolean searchNode(AVLNode node, int data) {
            if (node == null) return false;
            if (data == node.data) return true;
            if (data < node.data) return searchNode(node.left, data);
            return searchNode(node.right, data);
        }
        
        /**
         * 中序遍歷
         */
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            printInOrder(root, result);
            return result;
        }
        
        private void printInOrder(AVLNode node, List<Integer> result) {
            if (node != null) {
                printInOrder(node.left, result);
                result.add(node.data);
                printInOrder(node.right, result);
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
         * 驗證是否為有效的 AVL 樹
         */
        public boolean isValidAVL() {
            return checkAVL(root) != -1;
        }
        
        private int checkAVL(AVLNode node) {
            if (node == null) return 0;
            
            int leftHeight = checkAVL(node.left);
            int rightHeight = checkAVL(node.right);
            
            if (leftHeight == -1 || rightHeight == -1) return -1;
            
            if (Math.abs(leftHeight - rightHeight) > 1) return -1;
            
            return Math.max(leftHeight, rightHeight) + 1;
        }
        
        /**
         * 列印樹狀結構（顯示平衡因子）
         */
        public void printTree() {
            System.out.print("版本 " + versionId + ": ");
            printTreeStructure(root);
            System.out.println();
        }
        
        private void printTreeStructure(AVLNode node) {
            if (node != null) {
                printTreeStructure(node.left);
                System.out.print(node.data + "(" + node.getBalance() + ") ");
                printTreeStructure(node.right);
            }
        }
    }
    
    // 版本管理
    private List<AVLVersion> versions;
    private int nextVersionId;
    
    // 當前工作版本（用於標準 AVL 樹操作）
    private AVLVersion currentVersion;
    
    public AVLInsertExample() {
        this.versions = new ArrayList<>();
        this.nextVersionId = 0;
        // 創建初始空版本
        this.currentVersion = new AVLVersion(null, nextVersionId++);
        versions.add(currentVersion);
    }
    
    /**
     * 獲取節點高度
     */
    private static int getHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }
    
    /**
     * 持久化插入 - 插入節點到指定版本，返回新版本
     * 時間複雜度: O(log n), 空間複雜度: O(log n)
     */
    public AVLVersion persistentInsert(int versionId, int data) {
        if (versionId < 0 || versionId >= versions.size()) {
            throw new IllegalArgumentException("Invalid version ID: " + versionId);
        }
        
        AVLVersion baseVersion = versions.get(versionId);
        AVLNode newRoot = insertNode(baseVersion.root, data);
        AVLVersion newVersion = new AVLVersion(newRoot, nextVersionId++);
        versions.add(newVersion);
        return newVersion;
    }
    
    /**
     * 遞歸插入節點（路徑複製）
     */
    private AVLNode insertNode(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            // 路徑複製：創建新節點，左子樹為遞歸插入結果，右子樹共享原節點
            AVLNode newLeft = insertNode(node.left, data);
            node = new AVLNode(node.data, newLeft, node.right);
        } else if (data > node.data) {
            // 路徑複製：創建新節點，右子樹為遞歸插入結果，左子樹共享原節點
            AVLNode newRight = insertNode(node.right, data);
            node = new AVLNode(node.data, node.left, newRight);
        } else {
            // 重複值不插入
            return node;
        }
        
        // 2. 檢查平衡因子
        int balance = node.getBalance();
        
        // 3. 處理不平衡情況
        
        // Left Left 情況
        if (balance > 1 && data < node.left.data) {
            return AVLRotations.rightRotate(node);
        }
        
        // Right Right 情況
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }
        
        // Left Right 情況
        if (balance > 1 && data > node.left.data) {
            AVLNode newLeft = AVLRotations.leftRotate(node.left);
            node = new AVLNode(node.data, newLeft, node.right);
            return AVLRotations.rightRotate(node);
        }
        
        // Right Left 情況
        if (balance < -1 && data < node.right.data) {
            AVLNode newRight = AVLRotations.rightRotate(node.right);
            node = new AVLNode(node.data, node.left, newRight);
            return AVLRotations.leftRotate(node);
        }
        
        // 返回節點
        return node;
    }
    
    /**
     * 持久化刪除 - 刪除節點，返回新版本
     * 時間複雜度: O(log n), 空間複雜度: O(log n)
     */
    public AVLVersion persistentDelete(int versionId, int data) {
        if (versionId < 0 || versionId >= versions.size()) {
            throw new IllegalArgumentException("Invalid version ID: " + versionId);
        }
        
        AVLVersion baseVersion = versions.get(versionId);
        AVLNode newRoot = deleteNode(baseVersion.root, data);
        AVLVersion newVersion = new AVLVersion(newRoot, nextVersionId++);
        versions.add(newVersion);
        return newVersion;
    }
    
    /**
     * 找最小值節點
     */
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * 遞歸刪除節點（路徑複製）
     */
    private AVLNode deleteNode(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) return null;
        
        if (data < node.data) {
            AVLNode newLeft = deleteNode(node.left, data);
            node = new AVLNode(node.data, newLeft, node.right);
        } else if (data > node.data) {
            AVLNode newRight = deleteNode(node.right, data);
            node = new AVLNode(node.data, node.left, newRight);
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else {
                    // 創建新節點替代
                    node = temp;
                }
            } else {
                // 有兩個子節點的情況
                AVLNode temp = findMin(node.right);
                // 創建新節點，用後繼節點的值替換
                AVLNode newRight = deleteNode(node.right, temp.data);
                node = new AVLNode(temp.data, node.left, newRight);
            }
        }
        
        if (node == null) return node;
        
        // 2. 檢查平衡因子並修復
        int balance = node.getBalance();
        
        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return AVLRotations.rightRotate(node);
        }
        
        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            AVLNode newLeft = AVLRotations.leftRotate(node.left);
            node = new AVLNode(node.data, newLeft, node.right);
            return AVLRotations.rightRotate(node);
        }
        
        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return AVLRotations.leftRotate(node);
        }
        
        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            AVLNode newRight = AVLRotations.rightRotate(node.right);
            node = new AVLNode(node.data, node.left, newRight);
            return AVLRotations.leftRotate(node);
        }
        
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
    
    // ========== 標準 AVL 樹接口 ==========
    
    /**
     * 標準插入方法 - 插入到當前工作版本
     * 時間複雜度: O(log n), 空間複雜度: O(log n)
     */
    public void insert(int data) {
        AVLNode newRoot = insertNode(currentVersion.root, data);
        currentVersion = new AVLVersion(newRoot, nextVersionId++);
        versions.add(currentVersion);
    }
    
    /**
     * 標準搜尋方法 - 在當前工作版本中搜尋
     * 時間複雜度: O(log n), 空間複雜度: O(log n)
     */
    public boolean search(int data) {
        return currentVersion.search(data);
    }
    
    /**
     * 標準刪除方法 - 從當前工作版本刪除
     * 時間複雜度: O(log n), 空間複雜度: O(log n)
     */
    public void delete(int data) {
        AVLNode newRoot = deleteNode(currentVersion.root, data);
        currentVersion = new AVLVersion(newRoot, nextVersionId++);
        versions.add(currentVersion);
    }
    
    /**
     * 列印樹狀結構（當前版本）
     */
    public void printTree() {
        printInOrder(currentVersion.root);
        System.out.println();
    }
    
    private void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }
    
    /**
     * 獲取當前樹的大小
     */
    public int size() {
        return currentVersion.size();
    }
    
    /**
     * 驗證當前樹是否為有效的 AVL 樹
     */
    public boolean isValidAVL() {
        return currentVersion.isValidAVL();
    }
    
    /**
     * 獲取根節點（用於測試）
     */
    public AVLNode getRoot() {
        return currentVersion.root;
    }
    
    // ========== 持久化 AVL 樹接口 ==========
    
    /**
     * 列出所有版本的資訊
     */
    public void printVersionHistory() {
        System.out.println("版本歷史：");
        for (AVLVersion version : versions) {
            System.out.printf("版本 %d: 大小 = %d, 有效AVL = %s%n", 
                version.getVersionId(), 
                version.size(), 
                version.isValidAVL() ? "是" : "否");
        }
    }
    
    // 測試方法
    public static void main(String[] args) {
        AVLInsertExample tree = new AVLInsertExample();
        
        System.out.println("=== 持久化 AVL 樹測試 ===\n");
        
        // 測試標準 AVL 樹操作
        System.out.println("0. 測試標準 AVL 樹操作：");
        AVLInsertExample standardTree = new AVLInsertExample();
        int[] standardValues = {10, 20, 30, 40, 50, 25};
        
        System.out.println("插入序列: 10, 20, 30, 40, 50, 25");
        for (int value : standardValues) {
            System.out.print("插入 " + value + " -> ");
            standardTree.insert(value);
            System.out.print("樹狀態: ");
            standardTree.printTree();
        }
        
        System.out.println("搜尋測試:");
        System.out.println("搜尋 25: " + standardTree.search(25));
        System.out.println("搜尋 35: " + standardTree.search(35));
        System.out.println("樹大小: " + standardTree.size());
        System.out.println("是否平衡: " + standardTree.isValidAVL());
        System.out.println();
        
        // 測試插入操作
        System.out.println("1. 測試持久化插入操作：");
        AVLVersion v1 = tree.persistentInsert(0, 10);  // 基於版本0插入
        AVLVersion v2 = tree.persistentInsert(1, 20);  // 基於版本1插入
        AVLVersion v3 = tree.persistentInsert(2, 30);  // 基於版本2插入
        AVLVersion v4 = tree.persistentInsert(3, 25);  // 基於版本3插入
        AVLVersion v5 = tree.persistentInsert(4, 40);  // 基於版本4插入
        AVLVersion v6 = tree.persistentInsert(5, 50);  // 基於版本5插入
        AVLVersion v7 = tree.persistentInsert(6, 60);  // 基於版本6插入
        
        // 顯示所有版本的中序遍歷
        System.out.println("各版本的中序遍歷結果：");
        for (int i = 0; i < tree.getVersionCount(); i++) {
            AVLVersion version = tree.getVersion(i);
            System.out.printf("版本 %d: %s%n", i, version.inorderTraversal());
        }
        System.out.println();
        
        // 顯示樹狀結構（包含平衡因子）
        System.out.println("2. 各版本的樹狀結構（數值(平衡因子)）：");
        for (int i = 1; i < tree.getVersionCount(); i++) {
            tree.getVersion(i).printTree();
        }
        System.out.println();
        
        // 測試搜尋功能
        System.out.println("3. 測試搜尋功能：");
        AVLVersion latestVersion = tree.getLatestVersion();
        int[] searchKeys = {10, 25, 35, 50, 60};
        for (int key : searchKeys) {
            boolean found = latestVersion.search(key);
            System.out.printf("搜尋鍵 %d: %s%n", key, found ? "找到" : "未找到");
        }
        System.out.println();
        
        // 測試刪除操作
        System.out.println("4. 測試持久化刪除操作：");
        AVLVersion v8 = tree.persistentDelete(7, 30);  // 從最新版本刪除30
        AVLVersion v9 = tree.persistentDelete(8, 20);  // 刪除20
        
        System.out.println("刪除前後對比：");
        System.out.printf("刪除前版本7: %s%n", tree.getVersion(7).inorderTraversal());
        System.out.printf("刪除30後版本8: %s%n", v8.inorderTraversal());
        System.out.printf("刪除20後版本9: %s%n", v9.inorderTraversal());
        System.out.println();
        
        // 測試版本查詢
        System.out.println("5. 測試歷史版本查詢：");
        AVLVersion oldVersion = tree.getVersion(3);  // 獲取版本3
        System.out.printf("版本3的內容: %s%n", oldVersion.inorderTraversal());
        System.out.printf("版本3搜尋20: %s%n", oldVersion.search(20) ? "找到" : "未找到");
        System.out.printf("版本3搜尋40: %s%n", oldVersion.search(40) ? "找到" : "未找到");
        System.out.println();
        
        // 測試 AVL 有效性
        System.out.println("6. 測試 AVL 樹有效性：");
        for (int i = 0; i < tree.getVersionCount(); i++) {
            AVLVersion version = tree.getVersion(i);
            System.out.printf("版本 %d 是否為有效AVL: %s%n", i, version.isValidAVL());
        }
        System.out.println();
        
        // 測試分支版本（基於舊版本創建新分支）
        System.out.println("7. 測試版本分支：");
        AVLVersion branch1 = tree.persistentInsert(3, 15);  // 基於版本3創建分支
        AVLVersion branch2 = tree.persistentInsert(3, 35);  // 基於版本3創建另一分支
        
        System.out.printf("原版本3: %s%n", tree.getVersion(3).inorderTraversal());
        System.out.printf("分支版本%d(+15): %s%n", branch1.getVersionId(), branch1.inorderTraversal());
        System.out.printf("分支版本%d(+35): %s%n", branch2.getVersionId(), branch2.inorderTraversal());
        System.out.println();
        
        // 最終版本歷史
        System.out.println("8. 最終版本歷史：");
        tree.printVersionHistory();
        
        // 空間優化展示
        System.out.println("\n9. 空間優化特點：");
        System.out.println("✓ 每個版本只複製從根到修改點的路徑上的節點");
        System.out.println("✓ 未修改的子樹在版本間共享，大幅節省記憶體");
        System.out.println("✓ 節點完全不可變，確保版本間資料安全");
        System.out.println("✓ 支援基於任意版本創建分支");
        System.out.println("✓ 維持 AVL 樹的平衡特性和時間複雜度");
        System.out.println("✓ 時間複雜度：O(log n) 插入/刪除/查詢");
        System.out.println("✓ 空間複雜度：每次操作 O(log n) 額外空間");
    }
}