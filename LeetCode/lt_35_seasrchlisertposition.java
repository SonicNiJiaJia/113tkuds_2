class lt_35_seasrchlisertposition {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
}

/*
解題邏輯與思路：

這題要在排序陣列中找到目標值的位置，如果不存在則返回應該插入的位置，要求時間複雜度O(log n)，因此使用二分搜尋法。

核心思路：
1. 使用標準二分搜尋尋找target
2. 如果找到target，直接返回其索引
3. 如果沒找到，二分搜尋結束時left指標會指向target應該插入的位置

關鍵觀察：
當二分搜尋結束(left > right)時，left指標的位置就是target應該插入的位置，因為：
- left左邊的所有元素都小於target
- left右邊的所有元素都大於等於target
- 因此target應該插入在left位置

具體步驟：
1. 初始化left=0, right=nums.length-1
2. 當left <= right時進行二分搜尋：
   - 如果nums[mid] == target，找到目標，返回mid
   - 如果nums[mid] < target，target在右半部分，left = mid + 1
   - 如果nums[mid] > target，target在左半部分，right = mid - 1
3. 迴圈結束時，返回left作為插入位置

例如nums = [1,3,5,6], target = 2：
- mid=1, nums[1]=3 > 2, right=0
- mid=0, nums[0]=1 < 2, left=1
- left > right迴圈結束，返回left=1
- 在位置1插入2得到[1,2,3,5,6]，符合排序順序

時間複雜度：O(log n) - 標準二分搜尋
空間複雜度：O(1) - 只使用常數空間
*/