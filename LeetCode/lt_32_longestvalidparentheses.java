import java.util.*;

class lt_32_longestvalidparentheses {
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLength = Math.max(maxLength, i - stack.peek());
                }
            }
        }
        
        return maxLength;
    }
}

/*
解題邏輯與思路：
1. 堆疊記錄索引：使用堆疊儲存字元的索引位置，而不是字元本身
2. 初始化：在堆疊中先放入-1作為基準點，方便計算有效括號長度
3. 遇到左括號：將當前索引i推入堆疊，等待後續右括號配對
4. 遇到右括號的處理：
   - 先彈出堆疊頂端元素(可能是配對的左括號索引或之前的基準點)
   - 如果彈出後堆疊為空，表示沒有可配對的左括號，將當前索引作為新基準點推入
   - 如果堆疊不為空，計算當前有效長度：i - stack.peek()
5. 長度計算邏輯：當前索引減去堆疊頂端的索引，得到以當前位置結尾的最長有效括號長度
6. 維護最大值：持續更新記錄的最大有效括號長度
7. 時間複雜度O(n)，空間複雜度O(n)，其中n為字串長度
*/