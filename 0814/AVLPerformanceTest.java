import java.util.*;

/**
 * 完整的 AVL 樹實現與性能測試
 */
public class AVLPerformanceTest {
    
    /**
     * AVL 樹節點類
     */
    static class AVLNode {
        int data;
        AVLNode left, right;
        int height;
        
        public AVLNode(int data) {
            this.data = data;
            this.height = 1;
        }
        
        // 計算平衡因子
        public int getBalance() {
            int leftHeight = (left != null) ? left.height : 0;
            int rightHeight = (right != null) ? right.height : 0;
            return leftHeight - rightHeight;
        }
        
        // 更新節點高度
        public void updateHeight() {
            int leftHeight = (left != null) ? left.height : 0;
            int rightHeight = (right != null) ? right.height : 0;
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }
    }
    
    /**
     * AVL 樹實現類
     */
    static class AVLTree {
        private AVLNode root;
        private int size;
        
        public AVLTree() {
            this.root = null;
            this.size = 0;
        }
        
        /**
         * 獲取節點高度
         */
        private int getHeight(AVLNode node) {
            return (node != null) ? node.height : 0;
        }
        
        /**
         * 右旋轉 (用於 Left-Left 情況)
         */
        private AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;
            
            // 執行旋轉
            x.right = y;
            y.left = T2;
            
            // 更新高度
            y.updateHeight();
            x.updateHeight();
            
            return x;
        }
        
        /**
         * 左旋轉 (用於 Right-Right 情況)
         */
        private AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;
            
            // 執行旋轉
            y.left = x;
            x.right = T2;
            
            // 更新高度
            x.updateHeight();
            y.updateHeight();
            
            return y;
        }
        
        /**
         * 插入方法
         */
        public void insert(int data) {
            root = insertNode(root, data);
        }
        
        /**
         * 遞歸插入節點
         */
        private AVLNode insertNode(AVLNode node, int data) {
            // 1. 標準 BST 插入
            if (node == null) {
                size++;
                return new AVLNode(data);
            }
            
            if (data < node.data) {
                node.left = insertNode(node.left, data);
            } else if (data > node.data) {
                node.right = insertNode(node.right, data);
            } else {
                // 重複值不插入
                return node;
            }
            
            // 2. 更新節點高度
            node.updateHeight();
            
            // 3. 檢查平衡因子
            int balance = node.getBalance();
            
            // 4. 處理不平衡情況
            
            // Left Left 情況
            if (balance > 1 && data < node.left.data) {
                return rightRotate(node);
            }
            
            // Right Right 情況
            if (balance < -1 && data > node.right.data) {
                return leftRotate(node);
            }
            
            // Left Right 情況
            if (balance > 1 && data > node.left.data) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            
            // Right Left 情況
            if (balance < -1 && data < node.right.data) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            
            // 返回節點
            return node;
        }
        
        /**
         * 搜尋方法
         */
        public boolean search(int data) {
            return searchNode(root, data);
        }
        
        /**
         * 遞歸搜尋節點
         */
        private boolean searchNode(AVLNode node, int data) {
            if (node == null) {
                return false;
            }
            
            if (data == node.data) {
                return true;
            } else if (data < node.data) {
                return searchNode(node.left, data);
            } else {
                return searchNode(node.right, data);
            }
        }
        
        /**
         * 刪除方法
         */
        public void delete(int data) {
            root = deleteNode(root, data);
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
         * 遞歸刪除節點
         */
        private AVLNode deleteNode(AVLNode node, int data) {
            // 1. 標準 BST 刪除
            if (node == null) {
                return node;
            }
            
            if (data < node.data) {
                node.left = deleteNode(node.left, data);
            } else if (data > node.data) {
                node.right = deleteNode(node.right, data);
            } else {
                // 找到要刪除的節點
                if (node.left == null || node.right == null) {
                    AVLNode temp = (node.left != null) ? node.left : node.right;
                    if (temp == null) {
                        temp = node;
                        node = null;
                    } else {
                        node = temp;
                    }
                    size--;
                } else {
                    // 有兩個子節點的情況
                    AVLNode temp = findMin(node.right);
                    node.data = temp.data;
                    node.right = deleteNode(node.right, temp.data);
                    size++;  // 補償上面的減法，因為實際只刪除一個節點
                }
            }
            
            if (node == null) {
                return node;
            }
            
            // 2. 更新高度
            node.updateHeight();
            
            // 3. 檢查平衡因子並修復
            int balance = node.getBalance();
            
            // Left Left 情況
            if (balance > 1 && node.left.getBalance() >= 0) {
                return rightRotate(node);
            }
            
            // Left Right 情況
            if (balance > 1 && node.left.getBalance() < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            
            // Right Right 情況
            if (balance < -1 && node.right.getBalance() <= 0) {
                return leftRotate(node);
            }
            
            // Right Left 情況
            if (balance < -1 && node.right.getBalance() > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            
            return node;
        }
        
        /**
         * 獲取樹的大小
         */
        public int size() {
            return size;
        }
        
        /**
         * 獲取樹的高度
         */
        public int height() {
            return getHeight(root);
        }
        
        /**
         * 檢查是否為空
         */
        public boolean isEmpty() {
            return root == null;
        }
        
        /**
         * 中序遍歷
         */
        public void inorderTraversal() {
            inorderHelper(root);
            System.out.println();
        }
        
        private void inorderHelper(AVLNode node) {
            if (node != null) {
                inorderHelper(node.left);
                System.out.print(node.data + " ");
                inorderHelper(node.right);
            }
        }
        
        /**
         * 驗證是否為有效的 AVL 樹
         */
        public boolean isValidAVL() {
            return checkAVL(root) != -1;
        }
        
        private int checkAVL(AVLNode node) {
            if (node == null) {
                return 0;
            }
            
            int leftHeight = checkAVL(node.left);
            int rightHeight = checkAVL(node.right);
            
            if (leftHeight == -1 || rightHeight == -1) {
                return -1;
            }
            
            if (Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
    
    /**
     * 主測試方法
     */
    public static void main(String[] args) {
        System.out.println("=== AVL 樹性能測試 ===\n");
        
        // 基本功能測試
        System.out.println("1. 基本功能測試：");
        AVLTree basicTree = new AVLTree();
        int[] testValues = {10, 20, 30, 40, 50, 25};
        
        System.out.print("插入序列: ");
        for (int value : testValues) {
            System.out.print(value + " ");
            basicTree.insert(value);
        }
        System.out.println();
        
        System.out.print("中序遍歷: ");
        basicTree.inorderTraversal();
        
        System.out.println("樹大小: " + basicTree.size());
        System.out.println("樹高度: " + basicTree.height());
        System.out.println("是否平衡: " + basicTree.isValidAVL());
        
        System.out.println("搜尋測試:");
        System.out.println("搜尋 25: " + (basicTree.search(25) ? "找到" : "未找到"));
        System.out.println("搜尋 35: " + (basicTree.search(35) ? "找到" : "未找到"));
        System.out.println();
        
        // 性能測試
        System.out.println("2. 性能測試：");
        performanceTest(1000, "小規模");
        performanceTest(10000, "中規模");
        performanceTest(100000, "大規模");
        
        // 比較測試
        System.out.println("\n3. 與理論時間複雜度比較：");
        compareComplexity();
    }
    
    /**
     * 性能測試方法
     */
    private static void performanceTest(int n, String scale) {
        System.out.println("\n--- " + scale + "測試 (n = " + n + ") ---");
        
        AVLTree avlTree = new AVLTree();
        Random random = new Random(42); // 使用固定種子保證可重現性
        
        // 生成測試資料
        List<Integer> insertData = new ArrayList<>();
        List<Integer> searchData = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            insertData.add(random.nextInt(n * 10));
        }
        
        for (int i = 0; i < Math.min(n, 10000); i++) {
            searchData.add(random.nextInt(n * 10));
        }
        
        // 插入測試
        long startTime = System.nanoTime();
        for (int value : insertData) {
            avlTree.insert(value);
        }
        long insertTime = System.nanoTime() - startTime;
        
        // 搜尋測試
        startTime = System.nanoTime();
        int foundCount = 0;
        for (int value : searchData) {
            if (avlTree.search(value)) {
                foundCount++;
            }
        }
        long searchTime = System.nanoTime() - startTime;
        
        // 刪除測試
        startTime = System.nanoTime();
        for (int i = 0; i < Math.min(n / 10, 1000); i++) {
            avlTree.delete(insertData.get(i));
        }
        long deleteTime = System.nanoTime() - startTime;
        
        // 輸出結果
        System.out.printf("插入 %d 個元素耗時: %d ms\n", n, insertTime / 1_000_000);
        System.out.printf("搜尋 %d 次耗時: %d ms (找到 %d 個)\n", 
                         searchData.size(), searchTime / 1_000_000, foundCount);
        System.out.printf("刪除 %d 個元素耗時: %d ms\n", 
                         Math.min(n / 10, 1000), deleteTime / 1_000_000);
        System.out.printf("最終樹大小: %d, 高度: %d\n", avlTree.size(), avlTree.height());
        System.out.printf("是否仍然平衡: %s\n", avlTree.isValidAVL() ? "是" : "否");
        
        // 計算平均時間
        double avgInsertTime = (double) insertTime / n / 1000; // 微秒
        double avgSearchTime = (double) searchTime / searchData.size() / 1000; // 微秒
        
        System.out.printf("平均插入時間: %.2f μs\n", avgInsertTime);
        System.out.printf("平均搜尋時間: %.2f μs\n", avgSearchTime);
    }
    
    /**
     * 時間複雜度比較
     */
    private static void compareComplexity() {
        int[] sizes = {1000, 5000, 10000, 50000, 100000};
        
        System.out.println("規模\t插入時間(ms)\t理論比值\t實際比值");
        System.out.println("--------------------------------------------");
        
        long previousTime = 0;
        double previousLogN = 0;
        
        for (int n : sizes) {
            AVLTree tree = new AVLTree();
            Random random = new Random(42);
            
            long startTime = System.nanoTime();
            for (int i = 0; i < n; i++) {
                tree.insert(random.nextInt(n * 10));
            }
            long insertTime = System.nanoTime() - startTime;
            long timeMs = insertTime / 1_000_000;
            
            double logN = Math.log(n) / Math.log(2);
            double theoreticalRatio = (previousLogN > 0) ? logN / previousLogN : 1.0;
            double actualRatio = (previousTime > 0) ? (double) timeMs / previousTime : 1.0;
            
            System.out.printf("%d\t%d\t\t%.2f\t\t%.2f\n", 
                             n, timeMs, theoreticalRatio, actualRatio);
            
            previousTime = timeMs;
            previousLogN = logN;
        }
        
        System.out.println("\n說明：");
        System.out.println("- 理論比值：基於 O(n log n) 的理論時間複雜度");
        System.out.println("- 實際比值：實際測量時間的比值");
        System.out.println("- 兩者接近說明 AVL 樹確實達到了理論時間複雜度");
    }
}