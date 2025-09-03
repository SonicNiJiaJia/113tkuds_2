class lt_29_dividetwotntegers {
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        boolean negative = (dividend < 0) ^ (divisor < 0);
        
        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);
        
        long quotient = 0;
        
        while (absDividend >= absDivisor) {
            long temp = absDivisor;
            long multiple = 1;
            
            while (absDividend >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }
            
            absDividend -= temp;
            quotient += multiple;
        }
        
        if (negative) {
            quotient = -quotient;
        }
        
        return (int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, quotient));
    }
}

/*
解題邏輯與思路：
1. 溢出邊界處理：特殊情況Integer.MIN_VALUE / -1會造成溢出，直接回傳Integer.MAX_VALUE
2. 符號處理：使用XOR運算判斷結果的正負性，兩數符號不同時結果為負
3. 轉換為正數：將dividend和divisor都轉為long型別的絕對值，避免溢出問題
4. 位移加速除法：
   - 使用位移運算(左移)來快速找到最大的divisor倍數
   - temp記錄當前的divisor倍數，multiple記錄對應的倍數值
   - 持續左移直到temp的兩倍大於dividend為止
5. 減法模擬除法：
   - 從dividend中減去找到的最大倍數temp
   - 將對應的multiple加到quotient中
   - 重複此過程直到dividend小於divisor
6. 結果邊界限制：確保最終結果在32位整數範圍內
7. 時間複雜度O(log²n)，空間複雜度O(1)，其中n為dividend的值
*/