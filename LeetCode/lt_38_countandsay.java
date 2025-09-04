class lt_38_countandsay {
    public String countAndSay(int n) {
        String result = "1";
        
        for (int i = 2; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            int count = 1;
            char currentChar = result.charAt(0);
            
            for (int j = 1; j < result.length(); j++) {
                if (result.charAt(j) == currentChar) {
                    count++;
                } else {
                    sb.append(count).append(currentChar);
                    currentChar = result.charAt(j);
                    count = 1;
                }
            }
            
            sb.append(count).append(currentChar);
            result = sb.toString();
        }
        
        return result;
    }
}

/*
解題邏輯與思路：

這題是實作Count-and-Say序列，需要對字串進行遊程編碼(Run-Length Encoding)。

核心思路：
1. 從基礎情況"1"開始，迭代生成每一層的結果
2. 對當前字串進行遊程編碼：統計連續相同字符的數量，然後輸出"數量+字符"的格式
3. 重複這個過程n-1次得到第n個序列

遊程編碼演算法：
1. 遍歷字串，記錄當前字符和連續出現次數
2. 當遇到不同字符時，將之前的"次數+字符"加入結果
3. 更新當前字符，重置計數器繼續處理
4. 處理完整個字串後，別忘記加入最後一組字符

迭代過程示例：
- n=1: "1" (基礎情況)
- n=2: "1" → 一個1 → "11"  
- n=3: "11" → 兩個1 → "21"
- n=4: "21" → 一個2一個1 → "1211"
- n=5: "1211" → 一個1一個2兩個1 → "111221"

實作細節：
1. 外層迴圈從2到n，處理n-1次遊程編碼
2. 內層迴圈遍歷當前字串，統計連續字符
3. 使用StringBuilder提高字串拼接效率
4. 注意處理最後一組連續字符(迴圈結束後)

時間複雜度分析：
每一輪都要處理前一輪的結果字串，字串長度會指數增長
設第k輪字串長度為L(k)，則時間複雜度約O(總字串長度)

空間複雜度：O(最終字串長度) - StringBuilder和結果字串的空間

時間複雜度：O(2^n) - 字串長度近似指數增長
空間複雜度：O(2^n) - 儲存最終結果
*/