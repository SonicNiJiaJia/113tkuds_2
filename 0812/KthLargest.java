import java.util.*;

public class KthLargest {
    
    // 方法1：使用 Min Heap 找第 K 大元素
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        System.out.println("處理過程:");
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            minHeap.offer(num);
            System.out.print("加入 " + num + ": " + minHeap);
            
            // 保持 heap 大小為 k
            if (minHeap.size() > k) {
                int removed = minHeap.poll();
                System.out.print(" -> 移除 " + removed + ": " + minHeap);
            }
            System.out.println();
        }
        
        return minHeap.peek();
    }
    
    // 方法2：建立完整 Max Heap 後取出 K 次
    public static int findKthLargestByMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // 建立 Max Heap
        for (int num : nums) {
            maxHeap.offer(num);
        }
        
        System.out.println("Max Heap: " + maxHeap);
        
        // 取出前 K-1 個最大值
        for (int i = 0; i < k - 1; i++) {
            int removed = maxHeap.poll();
            System.out.println("取出第 " + (i + 1) + " 大: " + removed + 
                             ", 剩餘: " + maxHeap);
        }
        
        return maxHeap.peek();
    }
    
    // 方法3：快速選擇演算法（額外展示）
    public static int findKthLargestByQuickSelect(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }
    
    private static int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];
        
        int pivotIndex = partition(nums, left, right);
        
        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }
    
    private static int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;
        
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }
    
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // 測試和比較不同方法
    public static void testKthLargest(int[] nums, int k) {
        System.out.println("=== 找第 " + k + " 大元素 ===");
        System.out.println("陣列: " + Arrays.toString(nums));
        
        // 驗證用的排序結果
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        System.out.println("排序後: " + Arrays.toString(sorted));
        System.out.println("答案應該是: " + sorted[sorted.length - k]);
        
        System.out.println("\n方法1 - Min Heap (推薦):");
        int result1 = findKthLargest(nums.clone(), k);
        System.out.println("結果: " + result1);
        
        System.out.println("\n方法2 - Max Heap:");
        int result2 = findKthLargestByMaxHeap(nums.clone(), k);
        System.out.println("結果: " + result2);
        
        System.out.println("\n方法3 - 快速選擇:");
        int result3 = findKthLargestByQuickSelect(nums.clone(), k);
        System.out.println("結果: " + result3);
        
        System.out.println("\n時間複雜度比較:");
        System.out.println("Min Heap: O(n log k), 空間 O(k)");
        System.out.println("Max Heap: O(n log n), 空間 O(n)");
        System.out.println("快速選擇: 平均 O(n), 最壞 O(n²), 空間 O(1)");
        System.out.println("=" + "=".repeat(40) + "\n");
    }
    
    public static void main(String[] args) {
        testKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2);
        testKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4);
        testKthLargest(new int[]{1}, 1);
    }
}
