import java.util.*;

public class MergeKSortedArrays {
    
    static class Element {
        int value;
        int arrayIndex;
        int elementIndex;
        
        Element(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }
    
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }
        
        PriorityQueue<Element> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.value, b.value));
        
        int totalElements = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                minHeap.offer(new Element(arrays[i][0], i, 0));
                totalElements += arrays[i].length;
            }
        }
        
        int[] result = new int[totalElements];
        int resultIndex = 0;
        
        while (!minHeap.isEmpty()) {
            Element current = minHeap.poll();
            result[resultIndex++] = current.value;
            
            if (current.elementIndex + 1 < arrays[current.arrayIndex].length) {
                int nextValue = arrays[current.arrayIndex][current.elementIndex + 1];
                minHeap.offer(new Element(nextValue, current.arrayIndex, current.elementIndex + 1));
            }
        }
        
        return result;
    }
    
    public static void testMerge(int[][] arrays) {
        System.out.print("輸入：[");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(Arrays.toString(arrays[i]));
            if (i < arrays.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        int[] result = mergeKSortedArrays(arrays);
        System.out.println("輸出：" + Arrays.toString(result));
        
        boolean isSorted = true;
        for (int i = 1; i < result.length; i++) {
            if (result[i] < result[i-1]) {
                isSorted = false;
                break;
            }
        }
        System.out.println("驗證排序：" + (isSorted ? "正確" : "錯誤"));
        System.out.println();
    }
    
    public static void performanceTest() {
        System.out.println("=== 效能測試 ===");
        
        Random rand = new Random(42);
        int k = 5;
        int[][] largeArrays = new int[k][];
        
        for (int i = 0; i < k; i++) {
            int size = 1000 + rand.nextInt(1000);
            largeArrays[i] = new int[size];
            largeArrays[i][0] = rand.nextInt(100);
            for (int j = 1; j < size; j++) {
                largeArrays[i][j] = largeArrays[i][j-1] + rand.nextInt(10) + 1;
            }
        }
        
        long startTime = System.nanoTime();
        int[] result = mergeKSortedArrays(largeArrays);
        long endTime = System.nanoTime();
        
        System.out.println("合併 " + k + " 個陣列，總共 " + result.length + " 個元素");
        System.out.println("執行時間：" + (endTime - startTime) / 1000000 + " ms");
        
        boolean isSorted = true;
        for (int i = 1; i < result.length && isSorted; i++) {
            if (result[i] < result[i-1]) {
                isSorted = false;
            }
        }
        System.out.println("結果驗證：" + (isSorted ? "正確" : "錯誤"));
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("假設有 K 個陣列，總共 N 個元素");
        System.out.println();
        System.out.println("時間複雜度：O(N log K)");
        System.out.println("- 每個元素都會被加入和移除 heap 一次");
        System.out.println("- heap 的大小最多為 K");
        System.out.println("- 每次 heap 操作需要 O(log K) 時間");
        System.out.println();
        System.out.println("空間複雜度：O(K + N)");
        System.out.println("- heap 佔用 O(K) 空間");
        System.out.println("- 結果陣列佔用 O(N) 空間");
        System.out.println();
        System.out.println("優勢：");
        System.out.println("- 比暴力合併的 O(NK) 更有效率");
        System.out.println("- 適合處理大量有序陣列");
        System.out.println("- 可以處理不同長度的陣列");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 測試案例 ===");
        
        int[][][] testCases = {
            {{1,4,5}, {1,3,4}, {2,6}},
            {{1,2,3}, {4,5,6}, {7,8,9}},
            {{1}, {0}},
            {{1,3,5,7}, {2,4,6,8}, {0,9,10,11}},
            {{5}, {1,3}, {2,4,6}}
        };
        
        for (int[][] testCase : testCases) {
            testMerge(testCase);
        }
        
        System.out.println("=== 邊界情況測試 ===");
        testMerge(new int[][]{{}, {1,2}, {3}});
        testMerge(new int[][]{{1}});
        testMerge(new int[][]{});
        
        performanceTest();
        System.out.println();
        complexityAnalysis();
    }
}