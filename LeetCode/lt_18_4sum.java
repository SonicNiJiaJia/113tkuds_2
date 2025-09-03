import java.util.*;

class lt_18_4sum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                int left = j + 1;
                int right = nums.length - 1;
                
                while (left < right) {
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                    
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        
        return result;
    }
}

/*
解題邏輯與思路：
1. 排序陣列：將陣列排序以便使用雙指針技巧並方便跳過重複元素
2. 四重循環優化為雙層循環+雙指針：
   - 外層兩個循環固定前兩個數字(i, j)
   - 使用雙指針(left, right)在剩餘範圍內尋找後兩個數字
3. 去重策略：
   - 對於i和j，如果當前值與前一個值相同則跳過
   - 找到解後，移動left和right指針跳過所有相同的值
4. 溢出處理：使用long型別計算四數之和，避免整數溢出
5. 雙指針移動：
   - 如果四數之和小於目標值，移動左指針增大和
   - 如果四數之和大於目標值，移動右指針減小和
   - 如果等於目標值，記錄結果並移動兩個指針繼續尋找其他解
*/