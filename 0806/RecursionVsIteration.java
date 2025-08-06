// RecursionVsIteration.java

import java.util.Stack;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class RecursionVsIteration {

    // --- 計算二項式係數 (C(n, k)) ---

    /**
     * 遞迴版本：計算二項式係數 C(n, k)
     * 使用帕斯卡恆等式 C(n, k) = C(n-1, k-1) + C(n-1, k)
     *
     * @param n 總數
     * @param k 選擇數
     * @return 二項式係數
     */
    public long binomialCoefficientRecursive(int n, int k) {
        if (k < 0 || k > n) {
            return 0; // 不合法的情況
        }
        if (k == 0 || k == n) {
            return 1; // 基本情況 C(n, 0) = 1, C(n, n) = 1
        }
        if (k > n / 2) { // 利用對稱性 C(n, k) = C(n, n-k)
            k = n - k;
        }
        return binomialCoefficientRecursive(n - 1, k - 1) + binomialCoefficientRecursive(n - 1, k);
    }

    /**
     * 迭代版本：計算二項式係數 C(n, k)
     * 使用動態規劃 (DP) 建立楊輝三角或直接使用公式 C(n, k) = n! / (k! * (n-k)!)
     * 這裡我們使用優化的迭代乘法方法，因為階乘可能會溢出 long
     * C(n, k) = (n * (n-1) * ... * (n-k+1)) / (k * (k-1) * ... * 1)
     *
     * @param n 總數
     * @param k 選擇數
     * @return 二項式係數
     */
    public long binomialCoefficientIterative(int n, int k) {
        if (k < 0 || k > n) {
            return 0;
        }
        if (k == 0 || k == n) {
            return 1;
        }
        if (k > n / 2) {
            k = n - k; // 利用對稱性
        }

        long res = 1;
        for (int i = 0; i < k; i++) {
            res = res * (n - i) / (i + 1);
        }
        return res;
    }

    // --- 尋找陣列中所有元素的乘積 ---

    /**
     * 遞迴版本：計算陣列中所有元素的乘積
     *
     * @param arr   輸入陣列
     * @param index 當前處理的索引
     * @return      陣列元素的乘積
     */
    public long productOfArrayRecursive(int[] arr, int index) {
        if (index == arr.length) {
            return 1; // 基本情況：到達陣列末尾，乘積單位元素
        }
        return arr[index] * productOfArrayRecursive(arr, index + 1);
    }

    /**
     * 迭代版本：計算陣列中所有元素的乘積
     *
     * @param arr 輸入陣列
     * @return      陣列元素的乘積
     */
    public long productOfArrayIterative(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1; // 空陣列乘積為 1
        }
        long product = 1;
        for (int num : arr) {
            product *= num;
        }
        return product;
    }

    // --- 計算字串中元音字母的數量 ---

    /**
     * 遞迴版本：計算字串中元音字母的數量
     *
     * @param str   輸入字串
     * @param index 當前處理的索引
     * @return      元音字母的數量
     */
    public int countVowelsRecursive(String str, int index) {
        if (str == null || index >= str.length()) {
            return 0; // 基本情況：字串為空或到達末尾
        }

        char ch = Character.toLowerCase(str.charAt(index));
        int count = 0;
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
            count = 1;
        }
        return count + countVowelsRecursive(str, index + 1);
    }

    /**
     * 迭代版本：計算字串中元音字母的數量
     *
     * @param str 輸入字串
     * @return      元音字母的數量
     */
    public int countVowelsIterative(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        int count = 0;
        String lowerStr = str.toLowerCase();
        for (char ch : lowerStr.toCharArray()) {
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }

    // --- 檢查括號是否配對正確 ---

    /**
     * 遞迴版本：檢查括號是否配對正確
     * (這個問題純遞迴實現較複雜且效率不高，通常會結合棧或迭代)
     * 這裡為了展示遞迴，我們使用一個不太常見的遞迴方法，它效率相對較低。
     * 對於這種問題，迭代+棧是更優解。
     *
     * @param s 輸入字串
     * @return  括號是否配對正確
     */
    public boolean areParenthesesBalancedRecursive(String s) {
        // 輔助函式，使用一個 Map 來模擬堆疊操作
        // 因為遞迴本身有堆疊，但這裡需要一個額外的堆疊來追蹤開啟的括號
        // 或者更常見的遞迴方式是每次移除已配對的括號對，直到字串為空或無法移除
        // 我們將實現一個基於「移除」的遞迴方法
        return checkBalanceRecursiveHelper(s);
    }

    private boolean checkBalanceRecursiveHelper(String s) {
        if (s.isEmpty()) {
            return true; // 空字串表示配對成功
        }

        // 嘗試移除第一個找到的有效括號對
        String newS = s.replace("()", "")
                         .replace("[]", "")
                         .replace("{}", "");

        // 如果沒有任何括號對被移除，且字串不為空，說明有不匹配的括號
        if (newS.length() == s.length() && !newS.isEmpty()) {
            return false;
        }

        // 如果字串變短了，繼續遞迴檢查新的字串
        return checkBalanceRecursiveHelper(newS);
    }


    /**
     * 迭代版本：檢查括號是否配對正確 (使用棧)
     *
     * @param s 輸入字串
     * @return  括號是否配對正確
     */
    public boolean areParenthesesBalancedIterative(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> bracketMap = new HashMap<>();
        bracketMap.put(')', '(');
        bracketMap.put(']', '[');
        bracketMap.put('}', '{');

        for (char ch : s.toCharArray()) {
            if (bracketMap.containsValue(ch)) { // 如果是開括號
                stack.push(ch);
            } else if (bracketMap.containsKey(ch)) { // 如果是閉括號
                if (stack.isEmpty() || stack.pop() != bracketMap.get(ch)) {
                    return false; // 堆疊為空或不匹配
                }
            }
            // 忽略非括號字符
        }
        return stack.isEmpty(); // 最後堆疊為空表示所有括號都配對成功
    }


    public static void main(String[] args) {
        RecursionVsIteration tester = new RecursionVsIteration();

        // --- 效能測試設定 ---
        int iterations = 100000; // 每個測試執行的次數，用於平均時間
        long startTime, endTime;

        System.out.println("--- 效能比較 ---");

        // --- 測試二項式係數 ---
        System.out.println("\n## 計算二項式係數 C(n, k)");
        int n_bc = 30; // 較大的 n 會顯著影響遞迴效能
        int k_bc = 10;

        // 遞迴版本
        startTime = System.nanoTime();
        long bcRecursiveResult = 0;
        for (int i = 0; i < iterations; i++) {
            bcRecursiveResult = tester.binomialCoefficientRecursive(n_bc, k_bc);
        }
        endTime = System.nanoTime();
        System.out.printf("遞迴版本 C(%d, %d) = %d, 平均耗時: %.3f 微秒%n", n_bc, k_bc, bcRecursiveResult, (endTime - startTime) / (double) iterations / 1000.0);

        // 迭代版本
        startTime = System.nanoTime();
        long bcIterativeResult = 0;
        for (int i = 0; i < iterations; i++) {
            bcIterativeResult = tester.binomialCoefficientIterative(n_bc, k_bc);
        }
        endTime = System.nanoTime();
        System.out.printf("迭代版本 C(%d, %d) = %d, 平均耗時: %.3f 微秒%n", n_bc, k_bc, bcIterativeResult, (endTime - startTime) / (double) iterations / 1000.0);
        System.out.println("遞迴版本對於較大的 n, k 會非常慢，因為重複計算很多子問題。");
        System.out.println("---");

        // --- 測試尋找陣列中所有元素的乘積 ---
        System.out.println("\n## 尋找陣列中所有元素的乘積");
        int[] arr_prod = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // 陣列大小會影響
        // 為了避免 long 溢出，我們用一個稍小的陣列進行測試，或確保結果在 long 範圍內
        int[] arr_prod_small = {1, 2, 3, 4, 5};

        // 遞迴版本
        startTime = System.nanoTime();
        long prodRecursiveResult = 0;
        for (int i = 0; i < iterations; i++) {
            prodRecursiveResult = tester.productOfArrayRecursive(arr_prod_small, 0);
        }
        endTime = System.nanoTime();
        System.out.printf("遞迴版本 乘積(%s) = %d, 平均耗時: %.3f 微秒%n", Arrays.toString(arr_prod_small), prodRecursiveResult, (endTime - startTime) / (double) iterations / 1000.0);

        // 迭代版本
        startTime = System.nanoTime();
        long prodIterativeResult = 0;
        for (int i = 0; i < iterations; i++) {
            prodIterativeResult = tester.productOfArrayIterative(arr_prod_small);
        }
        endTime = System.nanoTime();
        System.out.printf("迭代版本 乘積(%s) = %d, 平均耗時: %.3f 微秒%n", Arrays.toString(arr_prod_small), prodIterativeResult, (endTime - startTime) / (double) iterations / 1000.0);
        System.out.println("對於簡單的遍歷，迭代通常略快，因為沒有函數呼叫堆疊的開銷。");
        System.out.println("---");

        // --- 測試計算字串中元音字母的數量 ---
        System.out.println("\n## 計算字串中元音字母的數量");
        String str_vowels = "ProgrammingIsFunAndChallenging!"; // 字串長度會影響

        // 遞迴版本
        startTime = System.nanoTime();
        int vowelsRecursiveResult = 0;
        for (int i = 0; i < iterations; i++) {
            vowelsRecursiveResult = tester.countVowelsRecursive(str_vowels, 0);
        }
        endTime = System.nanoTime();
        System.out.printf("遞迴版本 元音字母數量(\"%s\") = %d, 平均耗時: %.3f 微秒%n", str_vowels, vowelsRecursiveResult, (endTime - startTime) / (double) iterations / 1000.0);

        // 迭代版本
        startTime = System.nanoTime();
        int vowelsIterativeResult = 0;
        for (int i = 0; i < iterations; i++) {
            vowelsIterativeResult = tester.countVowelsIterative(str_vowels);
        }
        endTime = System.nanoTime();
        System.out.printf("迭代版本 元音字母數量(\"%s\") = %d, 平均耗時: %.3f 微秒%n", str_vowels, vowelsIterativeResult, (endTime - startTime) / (double) iterations / 1000.0);
        System.out.println("與陣列乘積類似，迭代通常表現更好。");
        System.out.println("---");

        // --- 測試檢查括號是否配對正確 ---
        System.out.println("\n## 檢查括號是否配對正確");
        String balancedStr = "{[()]}";
        String unbalancedStr = "([)]";
        String longBalancedStr = "{[()]()}[{()}]"; // 更長的字串
        String longUnbalancedStr = "((()))}}";

        // 遞迴版本 (請注意：這個遞迴實作對於效能可能非常差，並且可能不如預期的直觀)
        startTime = System.nanoTime();
        boolean bracketRecursiveResult = false;
        for (int i = 0; i < iterations; i++) {
            bracketRecursiveResult = tester.areParenthesesBalancedRecursive(longBalancedStr);
        }
        endTime = System.nanoTime();
        System.out.printf("遞迴版本 配對檢查(\"%s\") = %b, 平均耗時: %.3f 微秒%n", longBalancedStr, bracketRecursiveResult, (endTime - startTime) / (double) iterations / 1000.0);

        startTime = System.nanoTime();
        bracketRecursiveResult = false;
        for (int i = 0; i < iterations; i++) {
            bracketRecursiveResult = tester.areParenthesesBalancedRecursive(longUnbalancedStr);
        }
        endTime = System.nanoTime();
        System.out.printf("遞迴版本 配對檢查(\"%s\") = %b, 平均耗時: %.3f 微秒%n", longUnbalancedStr, bracketRecursiveResult, (endTime - startTime) / (double) iterations / 1000.0);
        System.out.println("此遞迴版本透過字串替換來判斷，對於複雜或長字串，效能會非常差。");

        // 迭代版本
        startTime = System.nanoTime();
        boolean bracketIterativeResult = false;
        for (int i = 0; i < iterations; i++) {
            bracketIterativeResult = tester.areParenthesesBalancedIterative(longBalancedStr);
        }
        endTime = System.nanoTime();
        System.out.printf("迭代版本 配對檢查(\"%s\") = %b, 平均耗時: %.3f 微秒%n", longBalancedStr, bracketIterativeResult, (endTime - startTime) / (double) iterations / 1000.0);

        startTime = System.nanoTime();
        bracketIterativeResult = false;
        for (int i = 0; i < iterations; i++) {
            bracketIterativeResult = tester.areParenthesesBalancedIterative(longUnbalancedStr);
        }
        endTime = System.nanoTime();
        System.out.printf("迭代版本 配對檢查(\"%s\") = %b, 平均耗時: %.3f 微秒%n", longUnbalancedStr, bracketIterativeResult, (endTime - startTime) / (double) iterations / 1000.0);
        System.out.println("迭代版本 (使用棧) 是解決括號匹配問題的標準且高效的方法。");
        System.out.println("---");

        System.out.println("\n### 效能比較總結：");
        System.out.println("1. **二項式係數:** 遞迴版本因其重複計算相同的子問題而導致**指數級時間複雜度**，因此對於較大的 `n` 和 `k`，其效能遠遠不如迭代版本（迭代版本使用數學公式或動態規劃思想，時間複雜度為多項式級別）。這是遞迴低效的典型例子，可以使用**記憶化 (Memoization)** 來優化遞迴，使其效能接近迭代的動態規劃版本。");
        System.out.println("2. **陣列乘積與元音字母計數:** 對於這些簡單的遍歷問題，迭代版本通常會**略快於遞迴版本**。這是因為每次遞迴呼叫都會產生函數呼叫的開銷（建立新的堆疊幀，保存上下文等），而迭代則避免了這些開銷。對於這類問題，遞迴僅提供不同的程式碼結構，而非效能優勢。");
        System.out.println("3. **括號配對檢查:** 這個問題的遞迴版本實現方式對效能影響很大。此處提供的遞迴實現通過不斷替換配對括號來縮短字串，但字串替換操作本身開銷較大，導致其效能**遠差於迭代版本**。迭代版本（使用棧）是解決此類問題的標準且高效的方法，其時間複雜度為 O(n)，因為它只需單次遍歷字串。這再次強調了遞迴如果沒有妥善設計（例如，避免重複計算、選擇合適的遞迴策略），可能會導致極低的效能。");
        System.out.println("\n**總結:** 在大多數需要遍歷或迭代的問題中，**迭代版本通常在效能和記憶體效率上優於純粹的遞迴版本**，尤其當遞迴深度較大時。遞迴的優勢更多體現在程式碼的簡潔性和對某些演算法（如分治法、樹或圖的遍歷）的自然表達上，但這通常需要權衡效能。當遞迴重複計算大量子問題時，應考慮使用記憶化或轉換為迭代的動態規劃。");
    }
}