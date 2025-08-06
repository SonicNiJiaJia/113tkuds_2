import java.util.*;
import java.math.BigInteger;

public class RecursiveMathCalculator {
    
    // 記憶化快取，避免重複計算
    private static Map<String, BigInteger> combinationCache = new HashMap<>();
    private static Map<Integer, BigInteger> catalanCache = new HashMap<>();
    private static Map<Integer, BigInteger> hanoiCache = new HashMap<>();
    
    // 計算統計類別
    public static class CalculationStats {
        public int recursiveCalls;
        public int cacheHits;
        public long executionTime;
        
        public void reset() {
            recursiveCalls = 0;
            cacheHits = 0;
            executionTime = 0;
        }
        
        @Override
        public String toString() {
            return String.format("遞迴呼叫: %d次, 快取命中: %d次, 執行時間: %.3f毫秒",
                recursiveCalls, cacheHits, executionTime / 1_000_000.0);
        }
    }
    
    // 1. 計算組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
    public static BigInteger combination(int n, int k) {
        return combinationWithStats(n, k, null);
    }
    
    public static BigInteger combinationWithStats(int n, int k, CalculationStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況
        if (k < 0 || k > n) {
            return BigInteger.ZERO;
        }
        if (k == 0 || k == n) {
            return BigInteger.ONE;
        }
        
        // 利用對稱性優化：C(n, k) = C(n, n-k)
        if (k > n - k) {
            k = n - k;
        }
        
        // 檢查快取
        String key = n + "," + k;
        if (combinationCache.containsKey(key)) {
            if (stats != null) {
                stats.cacheHits++;
            }
            return combinationCache.get(key);
        }
        
        // 遞迴計算：C(n, k) = C(n-1, k-1) + C(n-1, k)
        BigInteger result = combinationWithStats(n - 1, k - 1, stats)
                           .add(combinationWithStats(n - 1, k, stats));
        
        // 儲存到快取
        combinationCache.put(key, result);
        
        return result;
    }
    
    // 非遞迴版本（用於驗證）
    public static BigInteger combinationIterative(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO;
        }
        if (k == 0 || k == n) {
            return BigInteger.ONE;
        }
        
        // 利用對稱性
        if (k > n - k) {
            k = n - k;
        }
        
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i))
                          .divide(BigInteger.valueOf(i + 1));
        }
        
        return result;
    }
    
    // 2. 計算卡塔蘭數 C(n) = Σ(C(i) × C(n-1-i))，其中 i 從 0 到 n-1
    public static BigInteger catalan(int n) {
        return catalanWithStats(n, null);
    }
    
    public static BigInteger catalanWithStats(int n, CalculationStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況
        if (n <= 1) {
            return BigInteger.ONE;
        }
        
        // 檢查快取
        if (catalanCache.containsKey(n)) {
            if (stats != null) {
                stats.cacheHits++;
            }
            return catalanCache.get(n);
        }
        
        // 遞迴計算：C(n) = Σ(C(i) × C(n-1-i))
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < n; i++) {
            BigInteger leftCatalan = catalanWithStats(i, stats);
            BigInteger rightCatalan = catalanWithStats(n - 1 - i, stats);
            result = result.add(leftCatalan.multiply(rightCatalan));
        }
        
        // 儲存到快取
        catalanCache.put(n, result);
        
        return result;
    }
    
    // 使用組合數公式計算卡塔蘭數：C(n) = C(2n, n) / (n + 1)
    public static BigInteger catalanByFormula(int n) {
        if (n <= 1) {
            return BigInteger.ONE;
        }
        
        BigInteger combination2n_n = combinationIterative(2 * n, n);
        return combination2n_n.divide(BigInteger.valueOf(n + 1));
    }
    
    // 3. 計算漢諾塔移動步數 hanoi(n) = 2 × hanoi(n-1) + 1
    public static BigInteger hanoi(int n) {
        return hanoiWithStats(n, null);
    }
    
    public static BigInteger hanoiWithStats(int n, CalculationStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況
        if (n <= 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }
        
        // 檢查快取
        if (hanoiCache.containsKey(n)) {
            if (stats != null) {
                stats.cacheHits++;
            }
            return hanoiCache.get(n);
        }
        
        // 遞迴計算：hanoi(n) = 2 × hanoi(n-1) + 1
        BigInteger result = hanoiWithStats(n - 1, stats)
                           .multiply(BigInteger.valueOf(2))
                           .add(BigInteger.ONE);
        
        // 儲存到快取
        hanoiCache.put(n, result);
        
        return result;
    }
    
    // 漢諾塔公式解：2^n - 1
    public static BigInteger hanoiByFormula(int n) {
        if (n <= 0) {
            return BigInteger.ZERO;
        }
        return BigInteger.valueOf(2).pow(n).subtract(BigInteger.ONE);
    }
    
    // 漢諾塔移動過程演示
    public static void hanoiProcess(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.printf("移動盤子 1 從 %c 到 %c\n", from, to);
            return;
        }
        
        // 將前 n-1 個盤子從 from 移到 aux
        hanoiProcess(n - 1, from, aux, to);
        
        // 將最大的盤子從 from 移到 to
        System.out.printf("移動盤子 %d 從 %c 到 %c\n", n, from, to);
        
        // 將 n-1 個盤子從 aux 移到 to
        hanoiProcess(n - 1, aux, to, from);
    }
    
    // 4. 判斷一個數字是否為回文數（如 12321）
    public static boolean isPalindrome(long number) {
        return isPalindrome(String.valueOf(Math.abs(number)));
    }
    
    public static boolean isPalindrome(String str) {
        return isPalindromeRecursive(str, 0, str.length() - 1);
    }
    
    private static boolean isPalindromeRecursive(String str, int left, int right) {
        // 基本情況：指標相遇或交叉
        if (left >= right) {
            return true;
        }
        
        // 檢查當前字符是否相等
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        
        // 遞迴檢查內部子字串
        return isPalindromeRecursive(str, left + 1, right - 1);
    }
    
    // 數字回文檢查（不轉換為字串）
    public static boolean isPalindromeNumber(long number) {
        if (number < 0) {
            return false;
        }
        
        return number == reverseNumber(number, 0);
    }
    
    private static long reverseNumber(long number, long reversed) {
        if (number == 0) {
            return reversed;
        }
        
        return reverseNumber(number / 10, reversed * 10 + number % 10);
    }
    
    // 清除所有快取
    public static void clearCache() {
        combinationCache.clear();
        catalanCache.clear();
        hanoiCache.clear();
    }
    
    // 顯示快取狀態
    public static void printCacheStats() {
        System.out.println("=== 快取統計 ===");
        System.out.printf("組合數快取: %d 項\n", combinationCache.size());
        System.out.printf("卡塔蘭數快取: %d 項\n", catalanCache.size());
        System.out.printf("漢諾塔快取: %d 項\n", hanoiCache.size());
        System.out.println();
    }
    
    // 效能測試
    public static void performanceTest(String functionName, Runnable test) {
        System.out.printf("【%s 效能測試】\n", functionName);
        
        long startTime = System.nanoTime();
        test.run();
        long endTime = System.nanoTime();
        
        System.out.printf("執行時間: %.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
        System.out.println();
    }
    
    // 主程式
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("              遞迴數學計算器測試程式");
        System.out.println("=".repeat(60));
        
        // 1. 組合數測試
        System.out.println("【測試 1: 組合數 C(n, k)】");
        System.out.println("公式：C(n, k) = C(n-1, k-1) + C(n-1, k)");
        System.out.println();
        
        // 測試小數值
        int[][] combinationTests = {{5, 2}, {10, 3}, {15, 7}, {20, 10}};
        for (int[] test : combinationTests) {
            int n = test[0], k = test[1];
            CalculationStats stats = new CalculationStats();
            
            long startTime = System.nanoTime();
            BigInteger recursive = combinationWithStats(n, k, stats);
            long endTime = System.nanoTime();
            stats.executionTime = endTime - startTime;
            
            BigInteger iterative = combinationIterative(n, k);
            
            System.out.printf("C(%d, %d):\n", n, k);
            System.out.printf("  遞迴結果: %s\n", recursive);
            System.out.printf("  迭代結果: %s\n", iterative);
            System.out.printf("  結果正確: %s\n", recursive.equals(iterative) ? "✓" : "✗");
            System.out.printf("  %s\n", stats);
            System.out.println();
        }
        
        // 2. 卡塔蘭數測試
        System.out.println("【測試 2: 卡塔蘭數 C(n)】");
        System.out.println("公式：C(n) = Σ(C(i) × C(n-1-i))，i 從 0 到 n-1");
        System.out.println("應用：二元樹計數、括號配對、路徑計數等");
        System.out.println();
        
        for (int n = 0; n <= 10; n++) {
            CalculationStats stats = new CalculationStats();
            
            long startTime = System.nanoTime();
            BigInteger recursive = catalanWithStats(n, stats);
            long endTime = System.nanoTime();
            stats.executionTime = endTime - startTime;
            
            BigInteger formula = catalanByFormula(n);
            
            System.out.printf("C(%d): %s", n, recursive);
            if (!recursive.equals(formula)) {
                System.out.print(" (公式驗證失敗)");
            }
            if (stats.recursiveCalls > 1) {
                System.out.printf(" [%d次遞迴, %d次快取命中]", 
                    stats.recursiveCalls, stats.cacheHits);
            }
            System.out.println();
        }
        
        System.out.println("\n卡塔蘭數的實際應用：");
        System.out.println("- C(3) = 5：3個節點的二元樹有5種形狀");
        System.out.println("- C(4) = 14：4對括號的正確配對有14種方式");
        System.out.println("- C(5) = 42：5×5網格對角線路徑有42種");
        System.out.println();
        
        // 3. 漢諾塔測試
        System.out.println("【測試 3: 漢諾塔移動步數】");
        System.out.println("公式：hanoi(n) = 2 × hanoi(n-1) + 1");
        System.out.println("閉式解：2^n - 1");
        System.out.println();
        
        for (int n = 1; n <= 15; n++) {
            CalculationStats stats = new CalculationStats();
            
            long startTime = System.nanoTime();
            BigInteger recursive = hanoiWithStats(n, stats);
            long endTime = System.nanoTime();
            stats.executionTime = endTime - startTime;
            
            BigInteger formula = hanoiByFormula(n);
            
            System.out.printf("Hanoi(%2d): %8s", n, recursive);
            if (!recursive.equals(formula)) {
                System.out.print(" (公式驗證失敗)");
            }
            if (stats.cacheHits > 0) {
                System.out.printf(" [快取命中: %d]", stats.cacheHits);
            }
            System.out.println();
        }
        
        // 漢諾塔移動過程演示
        System.out.println("\n【漢諾塔移動過程演示 (n=3)】");
        hanoiProcess(3, 'A', 'C', 'B');
        System.out.println();
        
        // 4. 回文數測試
        System.out.println("【測試 4: 回文數判斷】");
        System.out.println("遞迴檢查數字的每一位是否對稱");
        System.out.println();
        
        long[] palindromeTests = {
            12321, 1221, 7, 121, 1234, 9009, 
            -121, 10, 1001, 12345654321L
        };
        
        for (long number : palindromeTests) {
            boolean isStringPalindrome = isPalindrome(number);
            boolean isNumberPalindrome = isPalindromeNumber(number);
            
            System.out.printf("%12d: 字串方法=%s, 數字方法=%s", 
                number, isStringPalindrome ? "是" : "否", 
                isNumberPalindrome ? "是" : "否");
            
            if (isStringPalindrome != isNumberPalindrome) {
                System.out.print(" (結果不一致!)");
            }
            System.out.println();
        }
        
        // 效能比較測試
        System.out.println("\n【效能比較測試】");
        
        // 大數值組合數比較
        System.out.println("大數值組合數 C(30, 15):");
        clearCache();
        
        performanceTest("遞迴版本", () -> {
            CalculationStats stats = new CalculationStats();
            BigInteger result = combinationWithStats(30, 15, stats);
            System.out.printf("結果: %s\n", result);
            System.out.printf("統計: %s\n", stats);
        });
        
        performanceTest("迭代版本", () -> {
            BigInteger result = combinationIterative(30, 15);
            System.out.printf("結果: %s\n", result);
        });
        
        // 大數值卡塔蘭數
        System.out.println("大數值卡塔蘭數 C(15):");
        clearCache();
        
        performanceTest("遞迴版本", () -> {
            CalculationStats stats = new CalculationStats();
            BigInteger result = catalanWithStats(15, stats);
            System.out.printf("結果: %s\n", result);
            System.out.printf("統計: %s\n", stats);
        });
        
        performanceTest("公式版本", () -> {
            BigInteger result = catalanByFormula(15);
            System.out.printf("結果: %s\n", result);
        });
        
        // 快取效果展示
        System.out.println("【快取效果展示】");
        clearCache();
        System.out.println("第一次計算 C(25, 12):");
        CalculationStats firstStats = new CalculationStats();
        long start = System.nanoTime();
        combinationWithStats(25, 12, firstStats);
        long end = System.nanoTime();
        firstStats.executionTime = end - start;
        System.out.println(firstStats);
        
        System.out.println("第二次計算 C(25, 12) (有快取):");
        CalculationStats secondStats = new CalculationStats();
        start = System.nanoTime();
        combinationWithStats(25, 12, secondStats);
        end = System.nanoTime();
        secondStats.executionTime = end - start;
        System.out.println(secondStats);
        
        printCacheStats();
        
        // 數學應用說明
        System.out.println("【數學應用說明】");
        System.out.println("1. 組合數 C(n,k)：");
        System.out.println("   - 從n個物品中選k個的方法數");
        System.out.println("   - 二項式係數");
        System.out.println("   - 帕斯卡三角形");
        
        System.out.println("\n2. 卡塔蘭數：");
        System.out.println("   - 二元樹的形狀計數");
        System.out.println("   - 括號正確配對方式");
        System.out.println("   - 格路徑問題");
        System.out.println("   - 多邊形三角剖分");
        
        System.out.println("\n3. 漢諾塔：");
        System.out.println("   - 遞迴問題的經典例子");
        System.out.println("   - 指數級時間複雜度");
        System.out.println("   - 分治法的應用");
        
        System.out.println("\n4. 回文數：");
        System.out.println("   - 字串處理的基礎問題");
        System.out.println("   - 遞迴的自然應用");
        System.out.println("   - 對稱性檢測");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                測試完成");
        System.out.println("=".repeat(60));
    }
}