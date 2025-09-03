import java.util.*;

class lt_22_generateparentheses {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String current, int open, int close, int n) {
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }
        
        if (open < n) {
            backtrack(result, current + "(", open + 1, close, n);
        }
        
        if (close < open) {
            backtrack(result, current + ")", open, close + 1, n);
        }
    }
}

/*
解題邏輯與思路：
1. 使用回溯法(Backtracking)生成所有有效組合：透過遞迴探索所有可能的括號組合
2. 追蹤括號狀態：使用open和close分別記錄當前已使用的左括號和右括號數量
3. 有效性約束條件：
   - 左括號數量不能超過n：只有當open < n時才能添加左括號
   - 右括號數量不能超過左括號：只有當close < open時才能添加右括號
4. 終止條件：當字串長度達到2*n時，表示已生成一個完整的有效括號組合
5. 遞迴分支：在每個位置嘗試添加左括號或右括號，根據約束條件決定是否繼續遞迴
6. 時間複雜度O(4^n/√n)，空間複雜度O(4^n/√n)，這是第n個卡塔蘭數的漸近複雜度
*/