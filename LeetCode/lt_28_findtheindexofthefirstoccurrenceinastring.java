class lt_28_findtheindexofthefirstoccurrenceinastring {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        
        if (haystack.length() < needle.length()) {
            return -1;
        }
        
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            int j = 0;
            while (j < needle.length() && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i;
            }
        }
        
        return -1;
    }
}

/*
解題邏輯與思路：
1. 邊界條件處理：
   - 如果needle為空字串，根據慣例回傳0
   - 如果haystack長度小於needle長度，不可能包含，回傳-1
2. 暴力搜尋法：在haystack中逐一檢查每個可能的起始位置
3. 搜尋範圍限制：外層循環的範圍是0到haystack.length() - needle.length()，避免越界
4. 字元逐一比對：
   - 對每個起始位置i，使用內層循環比對needle的每個字元
   - 當haystack.charAt(i + j) == needle.charAt(j)時繼續比對下一個字元
5. 完全匹配檢查：當j == needle.length()時表示完全匹配，回傳當前起始位置i
6. 未找到處理：遍歷完所有可能位置後仍未找到匹配，回傳-1
7. 時間複雜度O(n*m)，空間複雜度O(1)，其中n為haystack長度，m為needle長度
*/