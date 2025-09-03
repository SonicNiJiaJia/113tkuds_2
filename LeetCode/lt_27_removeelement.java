class lt_27_removeelement {
    public int removeElement(int[] nums, int val) {
        int i = 0;
        
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        
        return i;
    }
}

/*
解題邏輯與思路：
1. 雙指針技巧：使用慢指針i和快指針j，i指向下一個非val元素應放置的位置，j用來遍歷陣列
2. 初始設定：慢指針i和快指針j都從0開始
3. 核心邏輯：
   - 快指針j遍歷整個陣列
   - 當nums[j] != val時，將該元素保留
   - 將nums[j]複製到nums[i]位置，並將慢指針i前移一位
4. 原地修改：透過覆寫陣列前面的元素來保留非val的元素，不需要額外空間
5. 跳過目標值：當nums[j] == val時，只移動快指針j，不進行複製操作
6. 回傳結果：慢指針i的最終值就是非val元素的個數
7. 時間複雜度O(n)，空間複雜度O(1)，其中n為陣列長度
*/