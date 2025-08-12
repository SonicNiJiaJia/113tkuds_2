import java.util.*;

public class SlidingWindowMedian {
    
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;
    
    public SlidingWindowMedian() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);
        minHeap = new PriorityQueue<>();
    }
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new double[0];
        }
        
        double[] result = new double[nums.length - k + 1];
        
        for (int i = 0; i < nums.length; i++) {
            addNumber(nums[i]);
            
            if (i >= k) {
                removeNumber(nums[i - k]);
            }
            
            if (i >= k - 1) {
                result[i - k + 1] = findMedian(k);
            }
        }
        
        return result;
    }
    
    private void addNumber(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        rebalance();
    }
    
    private void removeNumber(int num) {
        if (num <= maxHeap.peek()) {
            maxHeap.remove(num);
        } else {
            minHeap.remove(num);
        }
        rebalance();
    }
    
    private void rebalance() {
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    private double findMedian(int k) {
        if (k % 2 == 1) {
            return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
        } else {
            return ((double) maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }
    
    public static void testCase(int[] nums, int k) {
        System.out.println("陣列：" + Arrays.toString(nums) + ", K=" + k);
        
        SlidingWindowMedian swm = new SlidingWindowMedian();
        double[] result = swm.medianSlidingWindow(nums, k);
        
        System.out.print("輸出：[");
        for (int i = 0; i < result.length; i++) {
            if (result[i] == (int) result[i]) {
                System.out.print((int) result[i]);
            } else {
                System.out.print(result[i]);
            }
            if (i < result.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        System.out.println("詳細解釋：");
        for (int i = 0; i <= nums.length - k; i++) {
            int[] window = Arrays.copyOfRange(nums, i, i + k);
            Arrays.sort(window);
            double median;
            if (k % 2 == 1) {
                median = window[k / 2];
            } else {
                median = ((double) window[k / 2 - 1] + window[k / 2]) / 2.0;
            }
            
            System.out.print(Arrays.toString(Arrays.copyOfRange(nums, i, i + k)));
            System.out.print(" (排序：" + Arrays.toString(window) + ")");
            System.out.print(" → 中位數 ");
            if (median == (int) median) {
                System.out.println((int) median);
            } else {
                System.out.println(median);
            }
        }
        System.out.println();
    }
    
    public static void performanceTest() {
        System.out.println("=== 效能測試 ===");
        
        Random rand = new Random(42);
        int n = 10000;
        int k = 100;
        int[] largeArray = new int[n];
        
        for (int i = 0; i < n; i++) {
            largeArray[i] = rand.nextInt(10000) - 5000;
        }
        
        SlidingWindowMedian swm = new SlidingWindowMedian();
        
        long startTime = System.nanoTime();
        double[] result = swm.medianSlidingWindow(largeArray, k);
        long endTime = System.nanoTime();
        
        System.out.println("陣列大小：" + n + ", 視窗大小：" + k);
        System.out.println("結果數量：" + result.length);
        System.out.println("執行時間：" + (endTime - startTime) / 1000000 + " ms");
        
        System.out.println("前5個中位數：");
        for (int i = 0; i < Math.min(5, result.length); i++) {
            if (result[i] == (int) result[i]) {
                System.out.print((int) result[i] + " ");
            } else {
                System.out.print(result[i] + " ");
            }
        }
        System.out.println();
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("假設陣列長度為 N，視窗大小為 K");
        System.out.println();
        System.out.println("時間複雜度：O(N * K)");
        System.out.println("- 每個元素需要添加和移除操作");
        System.out.println("- 添加：O(log K)");
        System.out.println("- 移除：O(K) (因為需要搜尋元素)");
        System.out.println("- 總共：O(N * K)");
        System.out.println();
        System.out.println("空間複雜度：O(K)");
        System.out.println("- 兩個 heap 總共儲存 K 個元素");
        System.out.println();
        System.out.println("優化建議：");
        System.out.println("- 使用支援懶惰刪除的資料結構");
        System.out.println("- 可以結合 TreeSet 或自訂平衡樹");
        System.out.println("- 對於固定 K，可以考慮其他方法");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 測試案例 ===");
        
        testCase(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        testCase(new int[]{1, 2, 3, 4}, 2);
        
        System.out.println("=== 其他測試案例 ===");
        testCase(new int[]{1, 4, 2, 3}, 4);
        testCase(new int[]{2, 3, 4}, 2);
        testCase(new int[]{1, 2, 3, 4, 5}, 1);
        
        performanceTest();
        System.out.println();
        complexityAnalysis();
    }
}