import java.util.*;

class lt_30_substringwithconcatenationofallwords {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) {
            return result;
        }
        
        int wordLen = words[0].length();
        int totalLen = wordLen * words.length;
        
        if (s.length() < totalLen) {
            return result;
        }
        
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int right = i;
            int count = 0;
            Map<String, Integer> windowCount = new HashMap<>();
            
            while (right + wordLen <= s.length()) {
                String word = s.substring(right, right + wordLen);
                right += wordLen;
                
                if (wordCount.containsKey(word)) {
                    windowCount.put(word, windowCount.getOrDefault(word, 0) + 1);
                    count++;
                    
                    while (windowCount.get(word) > wordCount.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowCount.put(leftWord, windowCount.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }
                    
                    if (count == words.length) {
                        result.add(left);
                    }
                } else {
                    windowCount.clear();
                    left = right;
                    count = 0;
                }
            }
        }
        
        return result;
    }
}

/*
解題邏輯與思路：
1. 建立單詞頻率表：使用HashMap記錄words陣列中每個單詞的出現次數
2. 滑動窗口優化：不是檢查每個位置，而是以wordLen為間隔進行檢查，減少重複計算
3. 三層結構：
   - 外層循環：從0到wordLen-1，涵蓋所有可能的起始對齊位置
   - 滑動窗口：使用left和right指針維護當前窗口
   - 單詞匹配：每次移動wordLen長度檢查一個完整單詞
4. 窗口狀態維護：
   - windowCount記錄當前窗口中各單詞的出現次數
   - count記錄當前窗口中有效單詞的總數
5. 窗口調整策略：
   - 如果當前單詞在目標中，加入窗口並檢查是否超出頻率限制
   - 如果超出頻率，收縮左邊界直到頻率合法
   - 如果當前單詞不在目標中，重置窗口
6. 時間複雜度O(n*m)，空間複雜度O(m)，其中n為字串長度，m為單詞長度
*/