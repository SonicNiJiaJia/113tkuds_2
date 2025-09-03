import java.util.Arrays;

class lt_16_3sumclosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        int minDifference = Math.abs(closestSum - target);
        
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                int currentDifference = Math.abs(currentSum - target);
                
                if (currentDifference < minDifference) {
                    minDifference = currentDifference;
                    closestSum = currentSum;
                }
                
                if (currentSum < target) {
                    left++;
                } else if (currentSum > target) {
                    right--;
                } else {
                    return currentSum;
                }
            }
        }
        
        return closestSum;
    }
}

/*
解題邏輯與思路：
1. 排序陣列：先將輸入陣列排序，這樣可以使用雙指針技巧來有效搜索
2. 初始化最接近的和：使用前三個元素的和作為初始的最接近和
3. 三層循環優化為雙指針：
   - 外層循環固定第一個數字(i)
   - 使用雙指針(left, right)在剩餘範圍內尋找最佳的第二、三個數字
4. 雙指針移動策略：
   - 如果當前三數之和小於目標值，移動左指針向右(增大和)
   - 如果當前三數之和大於目標值，移動右指針向左(減小和)
   - 如果等於目標值，直接返回(最佳解)
5. 追蹤最接近的和：在每次計算三數之和時，比較與目標值的差距，更新最接近的和
*/
