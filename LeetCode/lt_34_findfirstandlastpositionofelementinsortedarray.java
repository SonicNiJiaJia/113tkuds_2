class lt_34_findfirstandlastpositionofelementinsortedarray {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        return result;
    }
    
    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}

/*
解題邏輯與思路：

這題要在排序陣列中找到目標值的第一個和最後一個位置，要求時間複雜度O(log n)，因此需要使用二分搜尋法。

核心思路：
1. 將問題分解為兩個子問題：找第一個出現位置和找最後一個出現位置
2. 各自使用修改過的二分搜尋來解決
3. 關鍵是當找到target時不立即返回，而是繼續搜尋邊界

findFirst方法邏輯：
1. 進行標準二分搜尋
2. 當nums[mid] == target時，記錄當前位置但繼續向左搜尋(right = mid - 1)
3. 這樣能確保找到最左邊(第一個)的target位置

findLast方法邏輯：
1. 進行標準二分搜尋  
2. 當nums[mid] == target時，記錄當前位置但繼續向右搜尋(left = mid + 1)
3. 這樣能確保找到最右邊(最後一個)的target位置

例如nums = [5,7,7,8,8,10], target = 8：
findFirst: 找到8後繼續向左搜尋，最終找到index 3
findLast: 找到8後繼續向右搜尋，最終找到index 4
結果：[3,4]

如果target不存在，兩個方法都返回-1，結果為[-1,-1]

時間複雜度：O(log n) - 兩次二分搜尋
空間複雜度：O(1) - 只使用常數空間
*/