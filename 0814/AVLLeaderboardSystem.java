/**
 * AVL 樹排行榜系統
 * 支援高效的排名查詢、分數更新和前 K 名查詢功能
 */

import java.util.*;

// 玩家資訊類別
class Player {
    String name;
    int score;
    long timestamp; // 用於處理同分情況
    
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        this.timestamp = System.nanoTime(); // 越晚加入的時間戳越大
    }
    
    public Player(String name, int score, long timestamp) {
        this.name = name;
        this.score = score;
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return name + "(" + score + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return Objects.equals(name, player.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

// 擴展的 AVL 節點，包含額外的排行榜資訊
class LeaderboardNode {
    Player player;
    LeaderboardNode left, right;
    int height;
    int size; // 子樹大小（包含自己）
    
    public LeaderboardNode(Player player) {
        this.player = player;
        this.left = null;
        this.right = null;
        this.height = 1;
        this.size = 1;
    }
    
    @Override
    public String toString() {
        return "Node(" + player + ", h=" + height + ", s=" + size + ")";
    }
}

public class AVLLeaderboardSystem {
    private LeaderboardNode root;
    private Map<String, Player> playerMap; // 快速查找玩家
    
    public AVLLeaderboardSystem() {
        this.root = null;
        this.playerMap = new HashMap<>();
    }
    
    // 獲取節點高度
    private int getHeight(LeaderboardNode node) {
        return node == null ? 0 : node.height;
    }
    
    // 獲取子樹大小
    private int getSize(LeaderboardNode node) {
        return node == null ? 0 : node.size;
    }
    
    // 更新節點高度和子樹大小
    private void updateNode(LeaderboardNode node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }
    }
    
    // 計算平衡因子
    private int getBalanceFactor(LeaderboardNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    // 比較兩個玩家的分數（分數高的排前面，分數相同時時間戳小的排前面）
    private int comparePlayer(Player p1, Player p2) {
        if (p1.score != p2.score) {
            return Integer.compare(p2.score, p1.score); // 分數高的排前面
        }
        return Long.compare(p1.timestamp, p2.timestamp); // 時間戳小的（早加入的）排前面
    }
    
    // 右旋操作
    private LeaderboardNode rightRotate(LeaderboardNode y) {
        LeaderboardNode x = y.left;
        LeaderboardNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateNode(y);
        updateNode(x);
        
        return x;
    }
    
    // 左旋操作
    private LeaderboardNode leftRotate(LeaderboardNode x) {
        LeaderboardNode y = x.right;
        LeaderboardNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateNode(x);
        updateNode(y);
        
        return y;
    }
    
    /**
     * 1. 添加玩家分數
     * 時間複雜度: O(log n)
     */
    public boolean addPlayer(String name, int score) {
        if (playerMap.containsKey(name)) {
            System.out.println("玩家 " + name + " 已存在，請使用 updateScore 更新分數");
            return false;
        }
        
        Player newPlayer = new Player(name, score);
        playerMap.put(name, newPlayer);
        root = insert(root, newPlayer);
        
        System.out.println("成功添加玩家: " + name + " (分數: " + score + ")");
        return true;
    }
    
    private LeaderboardNode insert(LeaderboardNode node, Player player) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new LeaderboardNode(player);
        }
        
        int cmp = comparePlayer(player, node.player);
        if (cmp < 0) {
            node.left = insert(node.left, player);
        } else if (cmp > 0) {
            node.right = insert(node.right, player);
        } else {
            // 這種情況理論上不應該發生，因為每個玩家名稱唯一
            return node;
        }
        
        // 2. 更新節點資訊並重新平衡
        return rebalance(node);
    }
    
    /**
     * 2. 更新玩家分數
     * 時間複雜度: O(log n)
     */
    public boolean updateScore(String name, int newScore) {
        if (!playerMap.containsKey(name)) {
            System.out.println("玩家 " + name + " 不存在，無法更新分數");
            return false;
        }
        
        Player oldPlayer = playerMap.get(name);
        int oldScore = oldPlayer.score;
        
        // 從樹中刪除舊的玩家節點
        root = delete(root, oldPlayer);
        
        // 創建新的玩家節點（保持原來的時間戳）
        Player updatedPlayer = new Player(name, newScore, oldPlayer.timestamp);
        playerMap.put(name, updatedPlayer);
        
        // 插入更新後的玩家節點
        root = insert(root, updatedPlayer);
        
        System.out.println("成功更新玩家 " + name + " 分數: " + oldScore + " → " + newScore);
        return true;
    }
    
    private LeaderboardNode delete(LeaderboardNode node, Player player) {
        if (node == null) {
            return node;
        }
        
        int cmp = comparePlayer(player, node.player);
        if (cmp < 0) {
            node.left = delete(node.left, player);
        } else if (cmp > 0) {
            node.right = delete(node.right, player);
        } else {
            // 找到要刪除的節點
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // 找到右子樹的最小節點作為後繼
            LeaderboardNode successor = findMin(node.right);
            node.player = successor.player;
            node.right = delete(node.right, successor.player);
        }
        
        return rebalance(node);
    }
    
    private LeaderboardNode findMin(LeaderboardNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // 重新平衡節點
    private LeaderboardNode rebalance(LeaderboardNode node) {
        updateNode(node);
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
     * 3. 查詢玩家排名 (1-based)
     * 時間複雜度: O(log n)
     */
    public int getPlayerRank(String name) {
        if (!playerMap.containsKey(name)) {
            System.out.println("玩家 " + name + " 不存在");
            return -1;
        }
        
        Player player = playerMap.get(name);
        int rank = getRank(root, player);
        
        System.out.println("玩家 " + name + " 當前排名: 第 " + rank + " 名");
        return rank;
    }
    
    private int getRank(LeaderboardNode node, Player player) {
        if (node == null) {
            return 0;
        }
        
        int cmp = comparePlayer(player, node.player);
        if (cmp < 0) {
            // player 在左子樹中
            return getRank(node.left, player);
        } else if (cmp > 0) {
            // player 在右子樹中
            return 1 + getSize(node.left) + getRank(node.right, player);
        } else {
            // 找到玩家
            return getSize(node.left) + 1;
        }
    }
    
    /**
     * 4. 查詢前 K 名玩家
     * 時間複雜度: O(k)
     */
    public List<Player> getTopK(int k) {
        List<Player> result = new ArrayList<>();
        if (k <= 0) {
            return result;
        }
        
        System.out.println("查詢前 " + k + " 名玩家:");
        selectTopK(root, k, result);
        
        for (int i = 0; i < result.size(); i++) {
            System.out.println("第 " + (i + 1) + " 名: " + result.get(i));
        }
        
        return result;
    }
    
    private void selectTopK(LeaderboardNode node, int k, List<Player> result) {
        if (node == null || result.size() >= k) {
            return;
        }
        
        // 中序遍歷（由於我們的比較函數，這會按排名順序遍歷）
        selectTopK(node.left, k, result);
        
        if (result.size() < k) {
            result.add(node.player);
        }
        
        selectTopK(node.right, k, result);
    }
    
    /**
     * 查詢第 K 名玩家 (1-based)
     * 時間複雜度: O(log n)
     */
    public Player selectKth(int k) {
        if (k < 1 || k > getSize(root)) {
            System.out.println("排名 " + k + " 超出範圍 [1, " + getSize(root) + "]");
            return null;
        }
        
        Player player = select(root, k);
        System.out.println("第 " + k + " 名玩家: " + player);
        return player;
    }
    
    private Player select(LeaderboardNode node, int k) {
        if (node == null) {
            return null;
        }
        
        int leftSize = getSize(node.left);
        
        if (k <= leftSize) {
            return select(node.left, k);
        } else if (k == leftSize + 1) {
            return node.player;
        } else {
            return select(node.right, k - leftSize - 1);
        }
    }
    
    /**
     * 查詢分數範圍內的玩家
     */
    public List<Player> getPlayersInScoreRange(int minScore, int maxScore) {
        List<Player> result = new ArrayList<>();
        System.out.println("查詢分數範圍 [" + minScore + ", " + maxScore + "] 內的玩家:");
        
        // 創建虛擬玩家用於範圍查詢
        Player minPlayer = new Player("", minScore, Long.MAX_VALUE);
        Player maxPlayer = new Player("", maxScore, Long.MIN_VALUE);
        
        rangeQuery(root, minPlayer, maxPlayer, result);
        
        // 按排名排序
        result.sort((p1, p2) -> comparePlayer(p1, p2));
        
        for (Player player : result) {
            System.out.println("  " + player);
        }
        
        return result;
    }
    
    private void rangeQuery(LeaderboardNode node, Player minPlayer, Player maxPlayer, List<Player> result) {
        if (node == null) {
            return;
        }
        
        if (node.player.score >= minPlayer.score && node.player.score <= maxPlayer.score) {
            result.add(node.player);
        }
        
        if (comparePlayer(minPlayer, node.player) < 0) {
            rangeQuery(node.left, minPlayer, maxPlayer, result);
        }
        
        if (comparePlayer(maxPlayer, node.player) > 0) {
            rangeQuery(node.right, minPlayer, maxPlayer, result);
        }
    }
    
    /**
     * 獲取總玩家數
     */
    public int getTotalPlayers() {
        return getSize(root);
    }
    
    /**
     * 顯示完整排行榜
     */
    public void displayFullLeaderboard() {
        System.out.println("=== 完整排行榜 ===");
        System.out.println("總玩家數: " + getTotalPlayers());
        
        List<Player> allPlayers = new ArrayList<>();
        inOrderTraversal(root, allPlayers);
        
        for (int i = 0; i < allPlayers.size(); i++) {
            System.out.println("第 " + (i + 1) + " 名: " + allPlayers.get(i));
        }
        System.out.println();
    }
    
    private void inOrderTraversal(LeaderboardNode node, List<Player> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.player);
            inOrderTraversal(node.right, result);
        }
    }
    
    /**
     * 顯示樹的結構（用於調試）
     */
    public void displayTree() {
        System.out.println("排行榜樹結構:");
        if (root == null) {
            System.out.println("空樹");
        } else {
            displayTreeRec(root, "", true);
        }
        System.out.println();
    }
    
    private void displayTreeRec(LeaderboardNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                node.player + " (h=" + node.height + ", s=" + node.size + ")");
            
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
        System.out.println("=== AVL 樹遊戲排行榜系統 ===\n");
        
        AVLLeaderboardSystem leaderboard = new AVLLeaderboardSystem();
        
        // 測試 1: 添加玩家
        System.out.println("=== 測試 1: 添加玩家 ===");
        String[] playerNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry"};
        int[] playerScores = {950, 880, 920, 990, 850, 930, 870, 960};
        
        for (int i = 0; i < playerNames.length; i++) {
            leaderboard.addPlayer(playerNames[i], playerScores[i]);
            
            // 添加一點延遲確保時間戳不同
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println();
        leaderboard.displayFullLeaderboard();
        
        // 測試 2: 查詢玩家排名
        System.out.println("=== 測試 2: 查詢玩家排名 ===");
        for (String name : Arrays.asList("Alice", "Bob", "Diana", "NonExistent")) {
            leaderboard.getPlayerRank(name);
        }
        System.out.println();
        
        // 測試 3: 查詢前 K 名玩家
        System.out.println("=== 測試 3: 查詢前 K 名玩家 ===");
        leaderboard.getTopK(3);
        System.out.println();
        leaderboard.getTopK(5);
        System.out.println();
        
        // 測試 4: 查詢第 K 名玩家
        System.out.println("=== 測試 4: 查詢第 K 名玩家 ===");
        leaderboard.selectKth(1);
        leaderboard.selectKth(4);
        leaderboard.selectKth(8);
        leaderboard.selectKth(10); // 超出範圍
        System.out.println();
        
        // 測試 5: 更新玩家分數
        System.out.println("=== 測試 5: 更新玩家分數 ===");
        System.out.println("更新前 Bob 的排名:");
        leaderboard.getPlayerRank("Bob");
        
        leaderboard.updateScore("Bob", 980); // Bob 分數從 880 提升到 980
        
        System.out.println("更新後的排行榜:");
        leaderboard.displayFullLeaderboard();
        
        System.out.println("更新後 Bob 的排名:");
        leaderboard.getPlayerRank("Bob");
        System.out.println();
        
        // 測試 6: 分數範圍查詢
        System.out.println("=== 測試 6: 分數範圍查詢 ===");
        leaderboard.getPlayersInScoreRange(900, 970);
        System.out.println();
        
        // 測試 7: 處理同分情況
        System.out.println("=== 測試 7: 處理同分情況 ===");
        leaderboard.addPlayer("Ian", 950);    // 與 Alice 同分
        leaderboard.addPlayer("Jack", 950);   // 與 Alice, Ian 同分
        
        System.out.println("添加同分玩家後的排行榜:");
        leaderboard.displayFullLeaderboard();
        
        System.out.println("同分玩家的排名查詢:");
        leaderboard.getPlayerRank("Alice");
        leaderboard.getPlayerRank("Ian");
        leaderboard.getPlayerRank("Jack");
        System.out.println();
        
        // 測試 8: 大規模性能測試
        System.out.println("=== 測試 8: 性能測試 ===");
        AVLLeaderboardSystem perfTest = new AVLLeaderboardSystem();
        
        System.out.println("添加 1000 個隨機玩家...");
        Random random = new Random(42);
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            perfTest.addPlayer("Player" + i, random.nextInt(10000));
        }
        
        long addTime = System.currentTimeMillis() - startTime;
        System.out.println("添加完成，耗時: " + addTime + " ms");
        
        // 測試查詢性能
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            perfTest.getPlayerRank("Player" + (i * 10));
            perfTest.selectKth(i + 1);
        }
        long queryTime = System.currentTimeMillis() - startTime;
        System.out.println("100 次查詢操作耗時: " + queryTime + " ms");
        
        // 測試更新性能
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            perfTest.updateScore("Player" + i, random.nextInt(10000));
        }
        long updateTime = System.currentTimeMillis() - startTime;
        System.out.println("100 次更新操作耗時: " + updateTime + " ms");
        
        System.out.println("\n總玩家數: " + perfTest.getTotalPlayers());
        System.out.println("前 10 名:");
        perfTest.getTopK(10);
        
        System.out.println("\n=== 排行榜系統測試完成 ===");
    }
}