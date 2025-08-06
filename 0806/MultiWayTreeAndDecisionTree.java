// MultiWayTreeAndDecisionTree.java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class MultiWayTreeAndDecisionTree {

    // --- 1. 建立一個可以有任意多個子節點的多路樹 ---
    // (這裡也稱為 N 叉樹或通用樹)
    static class MultiWayTreeNode {
        String data; // 節點數據，可以是任意類型，這裡用 String 方便表示決策樹
        List<MultiWayTreeNode> children;

        MultiWayTreeNode(String data) {
            this.data = data;
            this.children = new ArrayList<>();
        }

        public void addChild(MultiWayTreeNode child) {
            this.children.add(child);
        }

        public String getData() {
            return data;
        }

        public List<MultiWayTreeNode> getChildren() {
            return children;
        }

        // 方便列印時查看節點
        @Override
        public String toString() {
            return data;
        }
    }

    // --- 2. 實作多路樹的深度優先和廣度優先走訪 ---

    /**
     * 多路樹的深度優先走訪 (DFS)。
     * 這裡使用前序遍歷 (根 -> 子節點)。
     *
     * @param root 多路樹的根節點
     * @return 深度優先走訪的節點數據列表
     */
    public List<String> dfsTraversal(MultiWayTreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        dfsRecursive(root, result);
        return result;
    }

    private void dfsRecursive(MultiWayTreeNode node, List<String> result) {
        result.add(node.data); // 訪問當前節點
        for (MultiWayTreeNode child : node.children) {
            dfsRecursive(child, result); // 遞迴訪問每個子節點
        }
    }

    /**
     * 多路樹的廣度優先走訪 (BFS)。
     *
     * @param root 多路樹的根節點
     * @return 廣度優先走訪的節點數據列表
     */
    public List<String> bfsTraversal(MultiWayTreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<MultiWayTreeNode> queue = new LinkedList<>();
        queue.offer(root); // 將根節點加入佇列

        while (!queue.isEmpty()) {
            MultiWayTreeNode currentNode = queue.poll(); // 從佇列頭取出節點
            result.add(currentNode.data); // 訪問當前節點

            // 將所有子節點加入佇列
            for (MultiWayTreeNode child : currentNode.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    // --- 3. 模擬簡單的決策樹結構（如猜數字遊戲） ---

    /**
     * 模擬一個簡單的決策樹，用於猜測用戶想的數字 (1-10)。
     * 這是建立一個硬編碼的決策樹。
     *
     * 決策樹結構範例：
     * Is it <= 5?
     * /          \
     * Yes            No (Is it <= 7?)
     * /   \            /          \
     * Is it <= 2?   Is it <= 4?  Is it <= 9?  (Is it 10?) -> "The number is 10!"
     * /    \        /    \       /      \
     * "1"   "3"    "4"    "5"   "8"     "9"
     * (Leaf nodes are the guesses)
     */
    public MultiWayTreeNode buildSimpleGuessingDecisionTree() {
        // Root node
        MultiWayTreeNode root = new MultiWayTreeNode("Is the number <= 5?");

        // Level 1 children
        MultiWayTreeNode yes5 = new MultiWayTreeNode("Is the number <= 2?");
        MultiWayTreeNode no5 = new MultiWayTreeNode("Is the number <= 7?");
        root.addChild(yes5); // Yes branch for <= 5
        root.addChild(no5);  // No branch for > 5

        // Level 2 children for 'yes5'
        MultiWayTreeNode yes2 = new MultiWayTreeNode("Is the number <= 1?"); // For numbers 1,2
        MultiWayTreeNode no2 = new MultiWayTreeNode("Is the number <= 4?");  // For numbers 3,4,5
        yes5.addChild(yes2);
        yes5.addChild(no2);

        // Level 2 children for 'no5'
        MultiWayTreeNode yes7 = new MultiWayTreeNode("Is the number <= 9?"); // For numbers 6,7,8,9
        MultiWayTreeNode no7 = new MultiWayTreeNode("Is the number 10?");   // For number 10
        no5.addChild(yes7);
        no5.addChild(no7);

        // Level 3 children (Leaf nodes - direct answers)
        // For 'yes2'
        yes2.addChild(new MultiWayTreeNode("Is the number 1?")); // 1
        yes2.addChild(new MultiWayTreeNode("Is the number 2?")); // 2

        // For 'no2'
        no2.addChild(new MultiWayTreeNode("Is the number 3?")); // 3
        no2.addChild(new MultiWayTreeNode("Is the number 4?")); // 4
        no2.addChild(new MultiWayTreeNode("Is the number 5?")); // 5 (this branch implies <= 5 and > 4)

        // For 'yes7'
        yes7.addChild(new MultiWayTreeNode("Is the number 6?")); // 6
        yes7.addChild(new MultiWayTreeNode("Is the number 7?")); // 7
        yes7.addChild(new MultiWayTreeNode("Is the number 8?")); // 8
        yes7.addChild(new MultiWayTreeNode("Is the number 9?")); // 9

        // For 'no7'
        no7.addChild(new MultiWayTreeNode("The number is 10!")); // 10

        return root;
    }

    /**
     * 執行簡單的決策樹遊戲。
     *
     * @param root 決策樹的根節點
     */
    public void playGuessingGame(MultiWayTreeNode root) {
        Scanner scanner = new Scanner(System.in);
        MultiWayTreeNode currentNode = root;

        System.out.println("\n--- 數字猜謎遊戲 (1-10) ---");
        System.out.println("請想一個 1 到 10 之間的數字，我會試著猜出來。");

        while (currentNode != null) {
            System.out.println(currentNode.getData());

            // 判斷是否為葉節點 (沒有子節點，即是答案)
            if (currentNode.getChildren().isEmpty()) {
                System.out.println("我猜到了！" + currentNode.getData());
                break;
            }

            // 模擬決策：這裡需要手動輸入 Y/N
            // 由於 MultiWayTreeNode 的 children 是 List，我們需要定義哪一個是 'Yes' 哪一個是 'No'
            // 這裡假設第一個子節點是 'Yes' 分支，第二個子節點是 'No' 分支
            if (currentNode.getChildren().size() >= 2) {
                System.out.print("請輸入 'Y' (是) 或 'N' (否): ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.equals("Y")) {
                    currentNode = currentNode.getChildren().get(0); // 走 Yes 分支
                } else if (input.equals("N")) {
                    currentNode = currentNode.getChildren().get(1); // 走 No 分支
                } else {
                    System.out.println("無效輸入，請輸入 'Y' 或 'N'。");
                    // 這裡可以選擇讓用戶重新輸入，或者直接結束
                    break;
                }
            } else if (currentNode.getChildren().size() == 1) {
                // 如果只有一個子節點，且不是葉子，通常表示這是唯一的路徑，直接向下走
                currentNode = currentNode.getChildren().get(0);
            } else {
                // 沒有子節點但也不是答案？(應該不會發生在正確構建的樹中)
                System.out.println("遊戲結束，似乎發生了錯誤。");
                break;
            }
        }
        scanner.close();
        System.out.println("--- 遊戲結束 ---");
    }

    // --- 4. 計算多路樹的高度和每個節點的度數 ---

    /**
     * 計算多路樹的高度。
     * 樹的高度定義為從根節點到最遠葉節點的最長路徑上的邊數。
     * (或者節點數，如果高度是從根節點到最遠葉節點的節點數量 - 1)
     * 這裡採用邊數定義。
     *
     * @param root 多路樹的根節點
     * @return 樹的高度，空樹為 -1，只有根節點的樹為 0
     */
    public int calculateHeight(MultiWayTreeNode root) {
        if (root == null) {
            return -1; // 空樹的高度定義為 -1
        }
        if (root.children.isEmpty()) {
            return 0; // 葉節點的高度定義為 0
        }

        int maxHeight = -1; // 初始化為 -1，因為子樹的高度至少為 0
        for (MultiWayTreeNode child : root.children) {
            maxHeight = Math.max(maxHeight, calculateHeight(child));
        }
        return 1 + maxHeight; // 當前節點的高度 = 1 + 最高子樹的高度
    }

    /**
     * 計算多路樹中每個節點的度數 (子節點數量)。
     *
     * @param root 多路樹的根節點
     * @return 一個 Map，鍵為節點數據，值為其度數
     */
    public Map<String, Integer> calculateDegrees(MultiWayTreeNode root) {
        Map<String, Integer> degrees = new HashMap<>();
        if (root == null) {
            return degrees;
        }

        Queue<MultiWayTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            MultiWayTreeNode currentNode = queue.poll();
            degrees.put(currentNode.getData(), currentNode.children.size()); // 記錄當前節點的度數

            for (MultiWayTreeNode child : currentNode.children) {
                queue.offer(child); // 將子節點加入佇列以便計算它們的度數
            }
        }
        return degrees;
    }


    public static void main(String[] args) {
        MultiWayTreeAndDecisionTree treeOperations = new MultiWayTreeAndDecisionTree();

        System.out.println("--- 多路樹與決策樹操作 ---");

        // 建立一個範例多路樹
        //           Root (A)
        //          /  |  \
        //         B   C   D
        //        / \     /|\
        //       E   F   G H I
        //      /
        //     J
        MultiWayTreeNode rootA = new MultiWayTreeNode("A");
        MultiWayTreeNode nodeB = new MultiWayTreeNode("B");
        MultiWayTreeNode nodeC = new MultiWayTreeNode("C");
        MultiWayTreeNode nodeD = new MultiWayTreeNode("D");

        rootA.addChild(nodeB);
        rootA.addChild(nodeC);
        rootA.addChild(nodeD);

        MultiWayTreeNode nodeE = new MultiWayTreeNode("E");
        MultiWayTreeNode nodeF = new MultiWayTreeNode("F");
        nodeB.addChild(nodeE);
        nodeB.addChild(nodeF);

        MultiWayTreeNode nodeG = new MultiWayTreeNode("G");
        MultiWayTreeNode nodeH = new MultiWayTreeNode("H");
        MultiWayTreeNode nodeI = new MultiWayTreeNode("I");
        nodeD.addChild(nodeG);
        nodeD.addChild(nodeH);
        nodeD.addChild(nodeI);

        MultiWayTreeNode nodeJ = new MultiWayTreeNode("J");
        nodeE.addChild(nodeJ);

        System.out.println("範例多路樹已建立。");
        System.out.println("---");

        // --- 2. 實作多路樹的深度優先和廣度優先走訪 ---
        System.out.println("\n2. 多路樹走訪:");
        System.out.println("深度優先走訪 (DFS): " + treeOperations.dfsTraversal(rootA));
        // 預期: [A, B, E, J, F, C, D, G, H, I]
        System.out.println("廣度優先走訪 (BFS): " + treeOperations.bfsTraversal(rootA));
        // 預期: [A, B, C, D, E, F, G, H, I, J]
        System.out.println("---");

        // --- 4. 計算多路樹的高度和每個節點的度數 ---
        System.out.println("\n4. 多路樹的高度和度數:");
        System.out.println("樹的高度: " + treeOperations.calculateHeight(rootA)); // 預期: 3 (J 是最深的葉子，A->B->E->J, 3 條邊)
        Map<String, Integer> degrees = treeOperations.calculateDegrees(rootA);
        System.out.println("每個節點的度數:");
        degrees.forEach((nodeData, degree) -> System.out.println("  節點 " + nodeData + ": " + degree + " 度"));
        // 預期: A:3, B:2, C:0, D:3, E:1, F:0, G:0, H:0, I:0, J:0
        System.out.println("---");

        // --- 3. 模擬簡單的決策樹結構（如猜數字遊戲） ---
        // 注意：這個遊戲會要求用戶輸入，所以請準備互動
        System.out.println("\n3. 模擬簡單的決策樹 (猜數字遊戲):");
        MultiWayTreeNode decisionTreeRoot = treeOperations.buildSimpleGuessingDecisionTree();
        // 執行遊戲，這會阻塞主線程直到遊戲結束
        // treeOperations.playGuessingGame(decisionTreeRoot);
        System.out.println("要開始數字猜謎遊戲嗎？ (請取消註解 main 方法中的 playGuessingGame 調用來執行)");
        System.out.println("---");


        // 測試空樹
        System.out.println("\n--- 測試空樹 ---");
        System.out.println("空樹 DFS: " + treeOperations.dfsTraversal(null)); // 預期: []
        System.out.println("空樹 BFS: " + treeOperations.bfsTraversal(null)); // 預期: []
        System.out.println("空樹高度: " + treeOperations.calculateHeight(null)); // 預期: -1
        System.out.println("空樹度數: " + treeOperations.calculateDegrees(null)); // 預期: {}
        System.out.println("---");
    }
}