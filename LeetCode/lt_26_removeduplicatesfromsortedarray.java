class lt_26_removeduplicatesfromsortedarray {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int i = 0;
        
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        
        return i + 1;
    }
}

/*
解題邏輯與思路：
1. 雙指針技巧：使用慢指針i和快指針j，i指向下一個唯一元素應放置的位置，j用來遍歷陣列
2. 初始設定：慢指針i從0開始，快指針j從1開始，因為第一個元素必定是唯一的
3. 核心邏輯：
   - 快指針j遍歷整個陣列
   - 當nums[j] != nums[i]時，發現新的唯一元素
   - 將慢指針i前移一位，並將nums[j]放到nums[i]位置
4. 原地修改：透過覆寫陣列前面的元素來保持唯一元素，不需要額外空間
5. 回傳結果：慢指針i的最終位置+1就是唯一元素的個數
6. 時間複雜度O(n)，空間複雜度O(1)，其中n為陣列長度
*/
