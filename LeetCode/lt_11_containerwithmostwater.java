class lt_11_containerwithmostwater {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxWater = 0;
        
        while (left < right) {
            int currentWater = Math.min(height[left], height[right]) * (right - left);
            maxWater = Math.max(maxWater, currentWater);
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxWater;
    }
}

/*
解題邏輯與思路：

1. 雙指針法(Two Pointers)：
   - 使用左指針(left)從陣列開始，右指針(right)從陣列結尾
   - 這樣可以從最大可能的寬度開始計算

2. 面積計算：
   - 容器的面積 = min(height[left], height[right]) × (right - left)
   - 高度取決於較短的那條線（木桶原理）
   - 寬度是兩個指針之間的距離

3. 指針移動策略：
   - 每次移動較短的那一邊的指針
   - 原因：如果移動較高的指針，新的面積必定不會更大
   - 因為寬度減少了，而高度仍受限於較短的那條線

4. 時間複雜度：O(n) - 每個元素最多被訪問一次
   空間複雜度：O(1) - 只使用了常數個變數

5. 核心思想：
   - 貪心策略：總是嘗試保留較高的線，移動較低的線
   - 這樣才有機會找到更大的面積
*/