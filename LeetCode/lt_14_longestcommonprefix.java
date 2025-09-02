class lt_14_longestcommonprefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        String prefix = strs[0];
        
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        
        return prefix;
    }
}

/*
解題邏輯與思路：

1. 初始化策略：
   - 將第一個字符串作為初始的公共前綴候選
   - 逐個與其他字符串比較並縮短前綴

2. 逐步縮減法：
   - 對每個字符串，檢查是否以當前prefix開頭
   - 如果不是，就將prefix末尾字符移除，直到匹配為止
   - 如果prefix變為空字符串，說明沒有公共前綴

3. 算法流程：
   - 邊界處理：數組為空或null時返回空字符串
   - 以第一個字符串初始化prefix
   - 遍歷剩餘字符串：
     * 使用startsWith()檢查當前字符串是否以prefix開頭
     * 如果不是，縮短prefix直到匹配
     * 如果prefix變空，直接返回空字符串

4. 實例分析（["flower","flow","flight"]）：
   - 初始prefix = "flower"
   - 檢查"flow"：不以"flower"開頭，縮短為"flowe", "flow", "flo", "fl"
   - "flow"以"fl"開頭，更新prefix = "fl"
   - 檢查"flight"：以"fl"開頭，保持prefix = "fl"
   - 返回"fl"

5. 時間複雜度：O(S) - S是所有字符串字符總數
   空間複雜度：O(1) - 只使用了prefix變數（不計算輸入）

6. 核心思想：
   - 貪心策略：從最長可能的前綴開始，逐步縮短直到找到公共前綴
   - 利用startsWith()方法簡化字符串匹配邏輯
   - 提前終止：一旦prefix為空就可以直接返回結果
*/