import java.util.*;

class lt_40_combinationsumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, combination, result);
        return result;
    }
    
    private void backtrack(int[] candidates, int remaining, int start, 
                          List<Integer> combination, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            
            if (candidates[i] > remaining) {
                break;
            }
            
            combination.add(candidates[i]);
            backtrack(candidates, remaining - candidates[i], i + 1, combination, result);
            combination.remove(combination.size() - 1);
        }
    }
}

/*
解題邏輯與思路：

這題是Combination Sum的變體，要求每個數字只能使用一次，且不能有重複的組合。關鍵是如何處理陣列中的重複數字。

核心思路：
1. 先對candidates陣列進行排序，將相同數字聚集在一起
2. 使用回溯法探索所有可能組合
3. 透過跳過同層重複數字來避免重複組合
4. 每個數字只能使用一次，所以遞歸時start參數為i+1

關鍵技巧 - 避免重複組合：
排序後，相同數字會相鄰排列。在同一層遞歸中，如果當前數字與前一個數字相同，就跳過它。
條件：i > start && candidates[i] == candidates[i-1]
- i > start確保只在同層比較，不影響垂直遞歸
- candidates[i] == candidates[i-1]檢測重複數字

為什麼這樣能避免重複：
考慮[1,1,2]，target=3：
- 第一層選第一個1，第二層選第二個1，第三層選2 → [1,1,2]
- 第一層選第二個1會產生相同結果，所以跳過

優化 - 提前終止：
由於陣列已排序，當candidates[i] > remaining時，後續數字都會更大，可以直接break。

回溯過程：
1. 終止條件：remaining == 0時找到有效組合
2. 剪枝1：跳過同層重複數字避免重複組合  
3. 剪枝2：數字大於remaining時提前終止
4. 選擇：加入candidates[i]，遞歸處理remaining - candidates[i]
5. 回溯：移除最後選擇，嘗試下一個數字

例如candidates=[10,1,2,7,6,1,5]，排序後=[1,1,2,5,6,7,10]，target=8：
- 選第一個1，remaining=7，繼續選第二個1，remaining=6，選6得到[1,1,6]
- 回溯到第一個1，跳過第二個1（同層重複），選2，remaining=5，選5得到[1,2,5]  
- 選第一個1，remaining=7，選7得到[1,7]
- 跳過第二個1，選2，remaining=6，選6得到[2,6]

時間複雜度：O(2^N) - 每個數字選或不選，實際會因剪枝而更好
空間複雜度：O(target) - 遞歸深度最大約為target（選擇最小數字時）
*/