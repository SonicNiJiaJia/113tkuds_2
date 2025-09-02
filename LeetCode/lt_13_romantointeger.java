class lt_13_romantointeger {
    public int romanToInt(String s) {
        int result = 0;
        int prevValue = 0;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue = getValue(s.charAt(i));
            
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            
            prevValue = currentValue;
        }
        
        return result;
    }
    
    private int getValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}

/*
解題邏輯與思路：

1. 從右到左遍歷：
   - 羅馬數字通常是從大到小排列，從右到左處理更容易判斷減法情況
   - 維護前一個字符的數值用於比較

2. 減法規則判斷：
   - 如果當前字符的數值小於前一個字符的數值，說明這是減法情況
   - 例如：IV中，I(1) < V(5)，所以I應該被減去
   - 否則就是正常的加法

3. 算法流程：
   - 從字符串末尾開始遍歷每個字符
   - 獲取當前字符對應的數值
   - 與前一個字符的數值比較：
     * 如果更小：從結果中減去當前值（減法情況）
     * 如果更大或相等：加到結果中（正常情況）
   - 更新前一個值為當前值

4. 實例分析（MCMXCIV = 1994）：
   - V(5): 5（第一個字符，直接加）
   - I(1): 1 < 5，所以 5 - 1 = 4
   - C(100): 100 > 1，所以 4 + 100 = 104  
   - X(10): 10 < 100，所以 104 - 10 = 94
   - M(1000): 1000 > 10，所以 94 + 1000 = 1094
   - C(100): 100 < 1000，所以 1094 - 100 = 994
   - M(1000): 1000 > 100，所以 994 + 1000 = 1994

5. 時間複雜度：O(n) - 需要遍歷整個字符串
   空間複雜度：O(1) - 只使用常數個變數

6. 核心思想：
   - 利用羅馬數字的構造規律：減法形式中較小的數字總是在較大數字前面
   - 通過比較相鄰字符的大小關係來確定是加法還是減法操作
*/