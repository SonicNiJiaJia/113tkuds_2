/**
 * AVL 樹範圍查詢練習
 * 實作高效的範圍查詢功能，利用 BST 性質進行剪枝優化
 */

import java.util.*;

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

public class AVLRangeQueryExercise {
    private AVLNode root;
    private int visitedNodes; // 用於統計實際訪問的節點數
    
    // 建構子
    public AVLRangeQueryExercise() {
        this.root = null;
        this.visitedNodes = 0;
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
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    /**
     * AVL 樹插入操作
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
        
        // 3. 重新平衡
        return rebalance(node);
    }
    
    private AVLNode rebalance(AVLNode node) {
        int balance = getBalanceFactor(node);
        
        // Left Left Case
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }
        
        // Left Right Case
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right Right Case
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        
        // Right Left Case
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    /**
     * 範圍查詢主方法 - 找出 [min, max] 範圍內的所有元素
     * 時間複雜度: O(log n + k)，其中 k 是結果數量
     * 空間複雜度: O(k) 用於儲存結果
     * 
     * @param min 範圍下界（包含）
     * @param max 範圍上界（包含）
     * @return 範圍內所有元素的有序列表
     */
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> result = new ArrayList<>();
        visitedNodes = 0; // 重置訪問計數器
        
        System.out.println("開始範圍查詢: [" + min + ", " + max + "]");
        
        if (min > max) {
            System.out.println("無效範圍: min > max");
            return result;
        }
        
        rangeQueryRec(root, min, max, result);
        
        System.out.println("範圍查詢完成，訪問節點數: " + visitedNodes + "/" + size());
        System.out.println("找到元素: " + result);
        
        return result;
    }
    
    /**
     * 範圍查詢遞迴實現 - 利用 BST 性質進行剪枝
     */
    private void rangeQueryRec(AVLNode node, int min, int max, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        visitedNodes++; // 計數訪問的節點
        System.out.println("訪問節點: " + node.data);
        
        // 利用 BST 性質進行剪枝優化
        
        // 如果當前節點值大於最大值，只需搜尋左子樹
        if (node.data > max) {
            System.out.println("  節點 " + node.data + " > " + max + "，只搜尋左子樹");
            rangeQueryRec(node.left, min, max, result);
        }
        // 如果當前節點值小於最小值，只需搜尋右子樹
        else if (node.data < min) {
            System.out.println("  節點 " + node.data + " < " + min + "，只搜尋右子樹");
            rangeQueryRec(node.right, min, max, result);
        }
        // 當前節點在範圍內，需要搜尋兩個子樹
        else {
            System.out.println("  節點 " + node.data + " 在範圍內，加入結果");
            
            // 先搜尋左子樹（保持有序）
            rangeQueryRec(node.left, min, max, result);
            
            // 將當前節點加入結果
            result.add(node.data);
            
            // 再搜尋右子樹
            rangeQueryRec(node.right, min, max, result);
        }
    }
    
    /**
     * 優化版範圍查詢 - 使用迭代中序遍歷
     * 在某些情況下可能更高效，特別是當範圍很小時
     */
    public List<Integer> rangeQueryIterative(int min, int max) {
        List<Integer> result = new ArrayList<>();
        if (min > max || root == null) {
            return result;
        }
        
        Stack<AVLNode> stack = new Stack<>();
        AVLNode current = root;
        int visitedCount = 0;
        
        System.out.println("使用迭代方法進行範圍查詢: [" + min + ", " + max + "]");
        
        // 找到第一個 >= min 的節點
        while (current != null) {
            visitedCount++;
            if (current.data >= min) {
                stack.push(current);
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        // 中序遍歷收集範圍內的節點
        while (!stack.isEmpty()) {
            current = stack.pop();
            visitedCount++;
            
            if (current.data > max) {
                break; // 超出範圍，停止遍歷
            }
            
            result.add(current.data);
            
            // 處理右子樹
            current = current.right;
            while (current != null) {
                visitedCount++;
                stack.push(current);
                current = current.left;
            }
        }
        
        System.out.println("迭代查詢完成，訪問節點數: " + visitedCount + "/" + size());
        System.out.println("找到元素: " + result);
        
        return result;
    }
    
    /**
     * 計算範圍內元素數量（不需要儲存所有元素）
     */
    public int countInRange(int min, int max) {
        return countInRangeRec(root, min, max);
    }
    
    private int countInRangeRec(AVLNode node, int min, int max) {
        if (node == null) {
            return 0;
        }
        
        if (node.data > max) {
            return countInRangeRec(node.left, min, max);
        } else if (node.data < min) {
            return countInRangeRec(node.right, min, max);
        } else {
            return 1 + countInRangeRec(node.left, min, max) + countInRangeRec(node.right, min, max);
        }
    }
    
    /**
     * 找出範圍內的最小值
     */
    public Integer findMinInRange(int min, int max) {
        return findMinInRangeRec(root, min, max);
    }
    
    private Integer findMinInRangeRec(AVLNode node, int min, int max) {
        if (node == null) {
            return null;
        }
        
        if (node.data > max) {
            return findMinInRangeRec(node.left, min, max);
        } else if (node.data < min) {
            return findMinInRangeRec(node.right, min, max);
        } else {
            // 當前節點在範圍內
            Integer leftMin = findMinInRangeRec(node.left, min, max);
            return leftMin != null ? leftMin : node.data;
        }
    }
    
    /**
     * 找出範圍內的最大值
     */
    public Integer findMaxInRange(int min, int max) {
        return findMaxInRangeRec(root, min, max);
    }
    
    private Integer findMaxInRangeRec(AVLNode node, int min, int max) {
        if (node == null) {
            return null;
        }
        
        if (node.data > max) {
            return findMaxInRangeRec(node.left, min, max);
        } else if (node.data < min) {
            return findMaxInRangeRec(node.right, min, max);
        } else {
            // 當前節點在範圍內
            Integer rightMax = findMaxInRangeRec(node.right, min, max);
            return rightMax != null ? rightMax : node.data;
        }
    }
    
    /**
     * 輔助方法
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
                node.data + " (h=" + node.height + ")");
            
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
     * 測試主程式
     */
    public static void main(String[] args) {
        System.out.println("=== AVL 樹範圍查詢練習 ===\n");
        
        AVLRangeQueryExercise avl = new AVLRangeQueryExercise();
        
        // 建立測試樹
        System.out.println("建立測試樹，插入數據: 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 65, 75, 90");
        int[] data = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 65, 75, 90};
        
        for (int value : data) {
            avl.insert(value);
        }
        
        System.out.println("\n初始樹:");
        avl.displayTree();
        avl.inOrderTraversal();
        System.out.println("樹的總節點數: " + avl.size());
        System.out.println();
        
        // 測試 1: 基本範圍查詢
        System.out.println("=== 測試 1: 基本範圍查詢 ===");
        
        // 測試不同大小的範圍
        int[][] ranges = {
            {25, 45},    // 小範圍
            {30, 70},    // 中等範圍
            {10, 90},    // 大範圍
            {40, 40},    // 單點查詢
            {15, 55},    // 跨越多個分支
            {100, 200}   // 超出範圍
        };
        
        for (int[] range : ranges) {
            System.out.println("\n--- 查詢範圍 [" + range[0] + ", " + range[1] + "] ---");
            List<Integer> result = avl.rangeQuery(range[0], range[1]);
            System.out.println("結果數量: " + result.size());
            System.out.println("效率: 訪問了 " + avl.visitedNodes + "/" + avl.size() + " 個節點");
            System.out.println();
        }
        
        // 測試 2: 比較遞迴與迭代方法
        System.out.println("=== 測試 2: 遞迴 vs 迭代方法比較 ===");
        
        int min = 35, max = 65;
        System.out.println("查詢範圍: [" + min + ", " + max + "]");
        
        System.out.println("\n遞迴方法:");
        List<Integer> recursiveResult = avl.rangeQuery(min, max);
        
        System.out.println("\n迭代方法:");
        List<Integer> iterativeResult = avl.rangeQueryIterative(min, max);
        
        System.out.println("\n結果一致性: " + recursiveResult.equals(iterativeResult));
        System.out.println();
        
        // 測試 3: 範圍統計功能
        System.out.println("=== 測試 3: 範圍統計功能 ===");
        
        int[] testRanges = {20, 80};
        System.out.println("範圍 [" + testRanges[0] + ", " + testRanges[1] + "] 統計:");
        System.out.println("元素數量: " + avl.countInRange(testRanges[0], testRanges[1]));
        System.out.println("最小值: " + avl.findMinInRange(testRanges[0], testRanges[1]));
        System.out.println("最大值: " + avl.findMaxInRange(testRanges[0], testRanges[1]));
        System.out.println("所有元素: " + avl.rangeQuery(testRanges[0], testRanges[1]));
        System.out.println();
        
        // 測試 4: 邊界情況
        System.out.println("=== 測試 4: 邊界情況 ===");
        
        // 無效範圍
        System.out.println("無效範圍 [50, 30]:");
        List<Integer> invalidResult = avl.rangeQuery(50, 30);
        System.out.println("結果: " + invalidResult);
        
        // 空範圍
        System.out.println("\n空範圍 [22, 23] (範圍內無元素):");
        List<Integer> emptyResult = avl.rangeQuery(22, 23);
        System.out.println("結果: " + emptyResult);
        
        // 單邊無界
        System.out.println("\n查詢所有 <= 40 的元素:");
        List<Integer> leftBoundResult = avl.rangeQuery(Integer.MIN_VALUE, 40);
        System.out.println("結果: " + leftBoundResult);
        
        System.out.println("\n查詢所有 >= 60 的元素:");
        List<Integer> rightBoundResult = avl.rangeQuery(60, Integer.MAX_VALUE);
        System.out.println("結果: " + rightBoundResult);
        System.out.println();
        
        // 測試 5: 效能測試
        System.out.println("=== 測試 5: 效能分析 ===");
        
        // 創建大型樹進行效能測試
        AVLRangeQueryExercise largeAVL = new AVLRangeQueryExercise();
        System.out.println("建立大型樹 (1000個節點)...");
        
        Random random = new Random(42); // 固定種子以獲得可重複的結果
        Set<Integer> inserted = new HashSet<>();
        
        while (inserted.size() < 1000) {
            int value = random.nextInt(10000);
            if (inserted.add(value)) {
                largeAVL.insert(value);
            }
        }
        
        System.out.println("大型樹建立完成，節點數: " + largeAVL.size());
        
        // 測試不同大小範圍的效率
        int[] rangeSizes = {10, 50, 100, 500};
        
        for (int rangeSize : rangeSizes) {
            int start = 2000;
            int end = start + rangeSize * 10;
            
            System.out.println("\n範圍大小約 " + rangeSize + " 個元素的查詢:");
            List<Integer> perfResult = largeAVL.rangeQuery(start, end);
            System.out.println("實際結果數: " + perfResult.size());
            System.out.println("訪問效率: " + largeAVL.visitedNodes + "/" + largeAVL.size() + 
                             " (" + String.format("%.1f", 100.0 * largeAVL.visitedNodes / largeAVL.size()) + "%)");
        }
        
        System.out.println("\n=== 範圍查詢測試完成 ===");
    }
}