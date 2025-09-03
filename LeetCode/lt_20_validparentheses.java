import java.util.*;

class lt_20_validparentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
}

/*
解題邏輯與思路：
1. 使用堆疊(Stack)資料結構：堆疊的後進先出特性完美符合括號配對的需求
2. 遍歷字串中的每個字元：
   - 遇到左括號('(', '{', '[')時，將其推入堆疊
   - 遇到右括號(')', '}', ']')時，檢查配對規則
3. 右括號配對檢查：
   - 如果堆疊為空，表示沒有對應的左括號，回傳false
   - 彈出堆疊頂端元素，檢查是否與當前右括號配對
   - 如果類型不匹配，回傳false
4. 最終檢查：遍歷完成後，堆疊必須為空才表示所有括號都正確配對
5. 時間複雜度O(n)，空間複雜度O(n)，其中n為字串長度
*/