class lt_15_3sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
}

/*
解題邏輯與思路：

1. 排序預處理：
   - 首先對陣列排序，這樣可以使用雙指針技巧
   - 排序後相同元素會聚集在一起，便於去重

2. 固定第一個元素：
   - 外層迴圈遍歷每個可能作為第一個元素的位置
   - 對於剩下的兩個元素，使用雙指針在剩餘陣列中尋找

3. 雙指針尋找配對：
   - left指針從當前元素後一位開始
   - right指針從陣列末尾開始
   - 根據三數之和與0的關係移動指針

4. 去重策略：
   - 第一個元素去重：跳過與前一個相同的元素
   - 找到解後去重：跳過left和right指針上相同的元素
   - 確保不會產生重複的三元組

5. 指針移動邏輯：
   - sum == 0：找到解，記錄後移動兩個指針
   - sum < 0：需要更大的數，移動left指針向右
   - sum > 0：需要更小的數，移動right指針向左

6. 實例分析（[-1,0,1,2,-1,-4] 排序後 [-4,-1,-1,0,1,2]）：
   - i=0, nums[i]=-4: left=1(-1), right=5(2), sum=-3<0, left++
   - i=0, nums[i]=-4: left=2(-1), right=5(2), sum=-3<0, left++
   - i=0, nums[i]=-4: left=3(0), right=5(2), sum=-2<0, left++
   - i=0, nums[i]=-4: left=4(1), right=5(2), sum=-1<0, left++ (left>=right結束)
   - i=1, nums[i]=-1: 找到[-1,-1,2]和[-1,0,1]
   - i=2: 跳過重複的-1

7. 時間複雜度：O(n²) - 外層迴圈O(n)，內層雙指針O(n)
   空間複雜度：O(1) - 不計算輸出陣列的話只使用常數空間

8. 核心思想：
   - 將3Sum問題轉化為2Sum問題：固定一個數，在剩餘陣列中找兩數之和
   - 利用排序後的有序性質使用雙指針高效搜尋
   - 通過跳過重複元素來避免產生重複解
*/