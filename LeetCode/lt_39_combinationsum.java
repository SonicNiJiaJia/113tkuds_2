import java.util.*;

class lt_39_combinationsum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
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
            if (candidates[i] <= remaining) {
                combination.add(candidates[i]);
                backtrack(candidates, remaining - candidates[i], i, combination, result);
                combination.remove(combination.size() - 1);
            }
        }
    }
}

/*
解題邏輯與思路：

這題要找出所有能組成目標和的數字組合，每個數字可以重複使用無限次。這是經典的回溯算法問題。

核心思路：
1. 使用回溯法探索所有可能的組合
2. 每次選擇一個數字加入當前組合，遞歸處理剩餘目標值
3. 如果剩餘目標值為0，找到一個有效組合
4. 回溯時移除最後加入的數字，嘗試其他選擇

關鍵設計：
1. remaining參數：記錄還需要湊成的目標值
2. start參數：控制搜索起始位置，避免重複組合
3. combination：記錄當前組合路徑
4. result：收集所有有效組合

避免重複的策略：
使用start參數確保只能選擇當前位置及之後的數字，這樣避免了像[2,3]和[3,2]這樣的重複組合。
同一個數字可以重複使用，所以遞歸時start參數傳i而不是i+1。

回溯過程：
1. 終止條件：remaining == 0時找到有效組合，加入result
2. 剪枝條件：candidates[i] > remaining時跳過，因為會超出目標值
3. 選擇：將candidates[i]加入combination，更新remaining
4. 遞歸：以新的remaining和相同的start位置i繼續搜索
5. 回溯：移除最後加入的數字，嘗試下一個候選數字

例如candidates=[2,3,6,7], target=7：
- 選2：remaining=5，可以繼續選2(remaining=3)，再選2(remaining=1)，無法繼續
- 回溯選3：remaining=2，無法選擇任何數字
- 回溯到remaining=5，選3：remaining=2，再選2就無法繼續
- 最終找到[2,2,3]
- 選7：remaining=0，找到[7]

時間複雜度：O(N^(T/M))，其中N為candidates長度，T為target，M為最小候選數字
空間複雜度：O(T/M) - 遞歸深度最多為target除以最小數字
*/