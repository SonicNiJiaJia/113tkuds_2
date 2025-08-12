import java.util.*;

public class KthSmallestElement {
    
    public static int findKthSmallestMaxHeap(int[] arr, int k) {
        if (k > arr.length) {
            throw new IllegalArgumentException("K不能大於陣列長度");
        }
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        for (int num : arr) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        
        return maxHeap.peek();
    }
    
    public static int findKthSmallestMinHeap(int[] arr, int k) {
        if (k > arr.length) {
            throw new IllegalArgumentException("K不能大於陣列長度");
        }
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : arr) {
            minHeap.offer(num);
        }
        
        int result = 0;
        for (int i = 0; i < k; i++) {
            result = minHeap.poll();
        }
        
        return result;
    }
    
    public static void testPerformance(int[] arr, int k) {
        System.out.println("陣列：" + Arrays.toString(arr) + ", K=" + k);
        
        long startTime = System.nanoTime();
        int result1 = findKthSmallestMaxHeap(arr, k);
        long endTime = System.nanoTime();
        long time1 = endTime - startTime;
        
        startTime = System.nanoTime();
        int result2 = findKthSmallestMinHeap(arr, k);
        endTime = System.nanoTime();
        long time2 = endTime - startTime;
        
        System.out.println("Max Heap方法結果：" + result1 + " (時間：" + time1 + " ns)");
        System.out.println("Min Heap方法結果：" + result2 + " (時間：" + time2 + " ns)");
        
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        System.out.println("排序後陣列：" + Arrays.toString(sorted));
        System.out.println("驗證第" + k + "小元素：" + sorted[k-1]);
        System.out.println();
    }
    
    public static void compareComplexity() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("方法1 - Max Heap (大小K)：");
        System.out.println("  時間複雜度：O(n log k)");
        System.out.println("  空間複雜度：O(k)");
        System.out.println("  優點：空間效率高，適合K很小的情況");
        System.out.println();
        
        System.out.println("方法2 - Min Heap (完整陣列)：");
        System.out.println("  時間複雜度：O(n log n + k log n)");
        System.out.println("  空間複雜度：O(n)");
        System.out.println("  優點：實作簡單，適合需要多次查詢不同K值");
        System.out.println();
        
        System.out.println("建議：");
        System.out.println("- 當 K << n 時，使用Max Heap方法");
        System.out.println("- 當 K 接近 n 時，兩種方法效率相近");
        System.out.println("- 需要多次查詢時，考慮先排序一次");
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[][] testArrays = {
            {7, 10, 4, 3, 20, 15},
            {1},
            {3, 1, 4, 1, 5, 9, 2, 6}
        };
        
        int[] testKs = {3, 1, 4};
        
        System.out.println("=== 測試案例 ===");
        for (int i = 0; i < testArrays.length; i++) {
            testPerformance(testArrays[i], testKs[i]);
        }
        
        compareComplexity();
        
        System.out.println("=== 大型測試 ===");
        Random rand = new Random(42);
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(100000);
        }
        
        System.out.println("測試大型陣列 (n=10000)：");
        testPerformance(Arrays.copyOf(largeArray, 100), 10);
        testPerformance(Arrays.copyOf(largeArray, 100), 50);
        testPerformance(Arrays.copyOf(largeArray, 100), 90);
    }
}