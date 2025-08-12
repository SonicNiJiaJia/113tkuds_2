import java.util.*;

public class StockMaximizer {
    
    static class Transaction {
        int buyDay;
        int sellDay;
        int profit;
        
        Transaction(int buyDay, int sellDay, int profit) {
            this.buyDay = buyDay;
            this.sellDay = sellDay;
            this.profit = profit;
        }
        
        @Override
        public String toString() {
            return String.format("買入第%d天，賣出第%d天，利潤%d", buyDay, sellDay, profit);
        }
    }
    
    public static int maxProfitWithHeap(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k == 0) {
            return 0;
        }
        
        int n = prices.length;
        
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        PriorityQueue<Integer> profitHeap = new PriorityQueue<>((a, b) -> b - a);
        List<Transaction> allTransactions = new ArrayList<>();
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (prices[j] > prices[i]) {
                    int profit = prices[j] - prices[i];
                    allTransactions.add(new Transaction(i, j, profit));
                }
            }
        }
        
        List<Transaction> validTransactions = selectNonOverlappingTransactions(allTransactions, k);
        
        return validTransactions.stream().mapToInt(t -> t.profit).sum();
    }
    
    private static List<Transaction> selectNonOverlappingTransactions(List<Transaction> transactions, int k) {
        transactions.sort((a, b) -> b.profit - a.profit);
        
        List<Transaction> selected = new ArrayList<>();
        boolean[] used = new boolean[10000];
        
        for (Transaction t : transactions) {
            if (selected.size() >= k) break;
            
            boolean canUse = true;
            for (int day = t.buyDay; day <= t.sellDay; day++) {
                if (used[day]) {
                    canUse = false;
                    break;
                }
            }
            
            if (canUse) {
                selected.add(t);
                for (int day = t.buyDay; day <= t.sellDay; day++) {
                    used[day] = true;
                }
            }
        }
        
        return selected;
    }
    
    public static int maxProfitDP(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k == 0) {
            return 0;
        }
        
        int n = prices.length;
        
        if (k >= n / 2) {
            return maxProfitUnlimited(prices);
        }
        
        int[][] buy = new int[k + 1][n];
        int[][] sell = new int[k + 1][n];
        
        for (int i = 1; i <= k; i++) {
            buy[i][0] = -prices[0];
            for (int j = 1; j < n; j++) {
                buy[i][j] = Math.max(buy[i][j - 1], sell[i - 1][j - 1] - prices[j]);
                sell[i][j] = Math.max(sell[i][j - 1], buy[i][j - 1] + prices[j]);
            }
        }
        
        return sell[k][n - 1];
    }
    
    private static int maxProfitUnlimited(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }
    
    public static int maxProfitGreedy(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k == 0) {
            return 0;
        }
        
        if (k >= prices.length / 2) {
            return maxProfitUnlimited(prices);
        }
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        int i = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            int buy = prices[i];
            
            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            int sell = prices[i];
            
            if (sell > buy) {
                maxHeap.offer(sell - buy);
            }
        }
        
        int totalProfit = 0;
        for (int j = 0; j < k && !maxHeap.isEmpty(); j++) {
            totalProfit += maxHeap.poll();
        }
        
        return totalProfit;
    }
    
    public static void testCase(int[] prices, int k) {
        System.out.println("價格：" + Arrays.toString(prices) + ", K=" + k);
        
        int result1 = maxProfitWithHeap(prices, k);
        int result2 = maxProfitDP(prices, k);
        int result3 = maxProfitGreedy(prices, k);
        
        System.out.println("Heap方法結果：" + result1);
        System.out.println("DP方法結果：" + result2);
        System.out.println("貪心方法結果：" + result3);
        
        System.out.println("詳細分析：");
        analyzeTrades(prices, k);
        System.out.println();
    }
    
    private static void analyzeTrades(int[] prices, int k) {
        List<Transaction> allTransactions = new ArrayList<>();
        
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[j] > prices[i]) {
                    int profit = prices[j] - prices[i];
                    allTransactions.add(new Transaction(i, j, profit));
                }
            }
        }
        
        allTransactions.sort((a, b) -> b.profit - a.profit);
        
        System.out.println("所有可能的獲利交易（按利潤排序）：");
        for (int i = 0; i < Math.min(5, allTransactions.size()); i++) {
            Transaction t = allTransactions.get(i);
            System.out.printf("  第%d天買入(價格%d)，第%d天賣出(價格%d)，利潤%d\n", 
                t.buyDay, prices[t.buyDay], t.sellDay, prices[t.sellDay], t.profit);
        }
        
        List<Transaction> selected = selectNonOverlappingTransactions(allTransactions, k);
        System.out.println("選擇的" + Math.min(k, selected.size()) + "個交易：");
        for (Transaction t : selected) {
            System.out.printf("  第%d天買入(價格%d)，第%d天賣出(價格%d)，利潤%d\n", 
                t.buyDay, prices[t.buyDay], t.sellDay, prices[t.sellDay], t.profit);
        }
    }
    
    public static void performanceTest() {
        System.out.println("=== 效能測試 ===");
        
        Random rand = new Random(42);
        int n = 100;
        int[] prices = new int[n];
        prices[0] = 100;
        
        for (int i = 1; i < n; i++) {
            prices[i] = Math.max(1, prices[i-1] + rand.nextInt(21) - 10);
        }
        
        int k = 10;
        
        long start = System.nanoTime();
        int result1 = maxProfitGreedy(prices, k);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = maxProfitDP(prices, k);
        long time2 = System.nanoTime() - start;
        
        System.out.println("陣列大小：" + n + ", K=" + k);
        System.out.println("貪心方法：結果=" + result1 + ", 時間=" + time1/1000000 + "ms");
        System.out.println("DP方法：結果=" + result2 + ", 時間=" + time2/1000000 + "ms");
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("假設價格陣列長度為 N");
        System.out.println();
        System.out.println("方法1 - Heap + 貪心：");
        System.out.println("  時間複雜度：O(N + K log N)");
        System.out.println("  空間複雜度：O(N)");
        System.out.println("  適用：K較小的情況");
        System.out.println();
        System.out.println("方法2 - 動態規劃：");
        System.out.println("  時間複雜度：O(NK)");
        System.out.println("  空間複雜度：O(NK) 或優化為 O(K)");
        System.out.println("  適用：一般情況，穩定可靠");
        System.out.println();
        System.out.println("特殊情況：當 K >= N/2 時");
        System.out.println("  可以進行無限次交易，使用貪心算法");
        System.out.println("  時間複雜度：O(N)");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 測試案例 ===");
        
        testCase(new int[]{2, 4, 1}, 2);
        testCase(new int[]{3, 2, 6, 5, 0, 3}, 2);
        testCase(new int[]{1, 2, 3, 4, 5}, 2);
        
        System.out.println("=== 其他測試案例 ===");
        testCase(new int[]{7, 1, 5, 3, 6, 4}, 2);
        testCase(new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0}, 2);
        
        performanceTest();
        System.out.println();
        complexityAnalysis();
    }
}