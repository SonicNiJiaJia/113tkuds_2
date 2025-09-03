import java.util.*;

class lt_17_lettercombinationofaphonenumber {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] mapping = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
        };
        
        backtrack(result, digits, "", 0, mapping);
        return result;
    }
    
    private void backtrack(List<String> result, String digits, String current, int index, String[] mapping) {
        if (index == digits.length()) {
            result.add(current);
            return;
        }
        
        String letters = mapping[digits.charAt(index) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            backtrack(result, digits, current + letters.charAt(i), index + 1, mapping);
        }
    }
}

/*
解題邏輯與思路：
1. 建立數字到字母的映射：使用陣列儲存每個數字鍵對應的字母組合，索引對應數字值
2. 邊界條件處理：如果輸入為空字串，直接回傳空的結果列表
3. 回溯法(Backtracking)生成所有組合：
   - 遞迴函數處理當前位置的數字，嘗試該數字對應的每個字母
   - 將當前選擇的字母加入到目前組合中，繼續處理下一個位置
   - 當處理完所有數字位置時，將完整組合加入結果中
   - 遞迴返回時自動撤銷選擇(透過參數傳遞而非修改全域變數)
4. 字元轉數字：使用 digits.charAt(index) - '0' 將字元數字轉為整數作為映射陣列的索引
*/