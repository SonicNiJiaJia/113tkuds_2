class lt_12_integertoroman {
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        
        return result.toString();
    }
}

/*
解題邏輯與思路：

1. 預處理映射表：
   - 將所有可能的羅馬數字符號按照數值大小降序排列
   - 包含基本符號(M, D, C, L, X, V, I)和減法形式(CM, CD, XC, XL, IX, IV)
   - 這樣確保優先使用較大的值

2. 貪心算法：
   - 從最大的數值開始，盡可能多地使用當前符號
   - 每次選擇不超過剩餘數字的最大羅馬數字值
   - 重複此過程直到數字變為0

3. 減法形式處理：
   - 通過預先在映射表中包含所有減法形式(900, 400, 90, 40, 9, 4)
   - 避免了複雜的條件判斷邏輯
   - 自然地按照優先級處理這些特殊情況

4. 算法流程：
   - 遍歷values陣列，對每個值檢查是否能從num中減去
   - 如果可以，就將對應的符號加入結果，並從num中減去該值
   - 重複直到該值不能再減去，然後處理下一個值

5. 時間複雜度：O(1) - 因為羅馬數字系統是固定的，最多13個符號
   空間複雜度：O(1) - 使用固定大小的陣列

6. 核心思想：
   - 將問題轉化為找零錢問題：用最少的"面額"組合出目標數字
   - 由於羅馬數字的特殊規則，預處理所有可能的組合simplifies邏輯
*/