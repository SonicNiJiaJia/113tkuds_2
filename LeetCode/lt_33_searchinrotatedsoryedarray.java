class lt_33_searchinrotatedsoryedarray {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
}

/*
解題邏輯與思路：

這題是在旋轉排序陣列中搜尋目標值，要求時間複雜度O(log n)，因此必須使用二分搜尋法。

核心思路：
1. 雖然整個陣列經過旋轉，但在任意分割點，至少有一半是完全排序的
2. 透過比較nums[left]和nums[mid]來判斷哪一半是排序的
3. 根據排序的那一半來決定target可能在哪個區間

具體步驟：
1. 設定left=0, right=nums.length-1進行二分搜尋
2. 計算mid點，如果nums[mid]==target直接返回mid
3. 判斷左半部分是否排序(nums[left] <= nums[mid])：
   - 如果左半排序且target在左半範圍內，搜尋左半部分
   - 否則搜尋右半部分
4. 如果右半部分排序：
   - 如果target在右半範圍內，搜尋右半部分  
   - 否則搜尋左半部分
5. 重複直到找到target或搜尋空間為空

例如[4,5,6,7,0,1,2], target=0：
- mid=3, nums[3]=7, 左半[4,5,6,7]排序, 0不在此範圍, 搜尋右半
- 在右半[0,1,2]中繼續二分搜尋找到target=0在index 4

時間複雜度：O(log n) - 每次搜尋範圍減半
空間複雜度：O(1) - 只使用常數空間
*/