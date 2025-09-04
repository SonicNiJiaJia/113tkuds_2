class lt_31_nextpermutation {
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        
        reverse(nums, i + 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}

/*
解題邏輯與思路：
1. 尋找突破點：從右往左找到第一個nums[i] < nums[i+1]的位置i，這是需要被替換的位置
2. 處理最大排列：如果找不到突破點(i < 0)，表示當前是最大字典序排列，需要重排為最小排列
3. 尋找交換目標：從右往左找到第一個nums[j] > nums[i]的位置j，這是要與nums[i]交換的元素
4. 執行交換：交換nums[i]和nums[j]，確保在i位置放置一個較大的數字
5. 反轉尾部：將i+1到陣列末尾的部分反轉，使其變為最小的字典序排列
6. 演算法核心：透過找到需要增大的最右位置，然後最小幅度地增大該位置的值，再讓後面的部分變為最小排列
7. 時間複雜度O(n)，空間複雜度O(1)，其中n為陣列長度
*/