import java.util.*;

public class AdvancedArrayRecursion {
    
    // 遞迴統計類別
    public static class RecursionStats {
        public int recursiveCalls;
        public int comparisons;
        public int swaps;
        public int arrayAccess;
        public long executionTime;
        public String algorithmName;
        
        public RecursionStats(String algorithmName) {
            this.algorithmName = algorithmName;
            reset();
        }
        
        public void reset() {
            recursiveCalls = 0;
            comparisons = 0;
            swaps = 0;
            arrayAccess = 0;
            executionTime = 0;
        }
        
        @Override
        public String toString() {
            return String.format("%s - 遞迴: %d次, 比較: %d次, 交換: %d次, 存取: %d次, 時間: %.3f毫秒",
                algorithmName, recursiveCalls, comparisons, swaps, arrayAccess, 
                executionTime / 1_000_000.0);
        }
    }
    
    // 1. 遞迴實作快速排序演算法
    public static int[] quickSort(int[] array) {
        return quickSort(array, null);
    }
    
    public static int[] quickSort(int[] array, RecursionStats stats) {
        if (array == null || array.length <= 1) {
            return array == null ? new int[0] : array.clone();
        }
        
        int[] arr = array.clone();
        long startTime = System.nanoTime();
        
        if (stats != null) {
            stats.reset();
        }
        
        quickSortRecursive(arr, 0, arr.length - 1, stats);
        
        if (stats != null) {
            stats.executionTime = System.nanoTime() - startTime;
        }
        
        return arr;
    }
    
    private static void quickSortRecursive(int[] arr, int low, int high, RecursionStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況：子陣列大小 <= 1
        if (low < high) {
            // 分割陣列並取得基準點位置
            int pivotIndex = partition(arr, low, high, stats);
            
            // 遞迴排序左半部分
            quickSortRecursive(arr, low, pivotIndex - 1, stats);
            
            // 遞迴排序右半部分
            quickSortRecursive(arr, pivotIndex + 1, high, stats);
        }
    }
    
    // 分割函數（Lomuto 分割方案）
    private static int partition(int[] arr, int low, int high, RecursionStats stats) {
        // 選擇最後一個元素作為基準
        int pivot = arr[high];
        if (stats != null) {
            stats.arrayAccess++;
        }
        
        int i = low - 1; // 較小元素的索引
        
        for (int j = low; j < high; j++) {
            if (stats != null) {
                stats.arrayAccess++;
                stats.comparisons++;
            }
            
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j, stats);
            }
        }
        
        // 將基準放到正確位置
        swap(arr, i + 1, high, stats);
        return i + 1;
    }
    
    // 快速排序（隨機基準版本）
    public static int[] quickSortRandomPivot(int[] array, RecursionStats stats) {
        if (array == null || array.length <= 1) {
            return array == null ? new int[0] : array.clone();
        }
        
        int[] arr = array.clone();
        long startTime = System.nanoTime();
        
        if (stats != null) {
            stats.reset();
        }
        
        quickSortRandomRecursive(arr, 0, arr.length - 1, stats);
        
        if (stats != null) {
            stats.executionTime = System.nanoTime() - startTime;
        }
        
        return arr;
    }
    
    private static void quickSortRandomRecursive(int[] arr, int low, int high, RecursionStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        if (low < high) {
            // 隨機選擇基準並與最後一個元素交換
            int randomIndex = low + new Random().nextInt(high - low + 1);
            swap(arr, randomIndex, high, stats);
            
            int pivotIndex = partition(arr, low, high, stats);
            quickSortRandomRecursive(arr, low, pivotIndex - 1, stats);
            quickSortRandomRecursive(arr, pivotIndex + 1, high, stats);
        }
    }
    
    // 2. 遞迴合併兩個已排序的陣列
    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        return mergeArrays(arr1, arr2, null);
    }
    
    public static int[] mergeArrays(int[] arr1, int[] arr2, RecursionStats stats) {
        if (arr1 == null) arr1 = new int[0];
        if (arr2 == null) arr2 = new int[0];
        
        long startTime = System.nanoTime();
        
        if (stats != null) {
            stats.reset();
        }
        
        int[] result = new int[arr1.length + arr2.length];
        mergeRecursive(arr1, 0, arr2, 0, result, 0, stats);
        
        if (stats != null) {
            stats.executionTime = System.nanoTime() - startTime;
        }
        
        return result;
    }
    
    private static void mergeRecursive(int[] arr1, int i1, int[] arr2, int i2, 
                                      int[] result, int resultIndex, RecursionStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況：其中一個陣列已處理完
        if (i1 >= arr1.length) {
            // 複製 arr2 的剩餘元素
            for (int i = i2; i < arr2.length; i++) {
                result[resultIndex++] = arr2[i];
                if (stats != null) {
                    stats.arrayAccess += 2; // 讀取和寫入
                }
            }
            return;
        }
        
        if (i2 >= arr2.length) {
            // 複製 arr1 的剩餘元素
            for (int i = i1; i < arr1.length; i++) {
                result[resultIndex++] = arr1[i];
                if (stats != null) {
                    stats.arrayAccess += 2; // 讀取和寫入
                }
            }
            return;
        }
        
        // 比較當前元素並選擇較小者
        if (stats != null) {
            stats.comparisons++;
            stats.arrayAccess += 2; // 讀取兩個元素
        }
        
        if (arr1[i1] <= arr2[i2]) {
            result[resultIndex] = arr1[i1];
            if (stats != null) {
                stats.arrayAccess++; // 寫入
            }
            mergeRecursive(arr1, i1 + 1, arr2, i2, result, resultIndex + 1, stats);
        } else {
            result[resultIndex] = arr2[i2];
            if (stats != null) {
                stats.arrayAccess++; // 寫入
            }
            mergeRecursive(arr1, i1, arr2, i2 + 1, result, resultIndex + 1, stats);
        }
    }
    
    // 3. 遞迴尋找陣列中的第 k 小元素（QuickSelect演算法）
    public static int findKthSmallest(int[] array, int k) {
        return findKthSmallest(array, k, null);
    }
    
    public static int findKthSmallest(int[] array, int k, RecursionStats stats) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            throw new IllegalArgumentException("無效的輸入參數");
        }
        
        int[] arr = array.clone(); // 避免修改原陣列
        long startTime = System.nanoTime();
        
        if (stats != null) {
            stats.reset();
        }
        
        int result = quickSelect(arr, 0, arr.length - 1, k - 1, stats); // k-1 因為索引從0開始
        
        if (stats != null) {
            stats.executionTime = System.nanoTime() - startTime;
        }
        
        return result;
    }
    
    private static int quickSelect(int[] arr, int low, int high, int k, RecursionStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況：只有一個元素
        if (low == high) {
            if (stats != null) {
                stats.arrayAccess++;
            }
            return arr[low];
        }
        
        // 分割陣列
        int pivotIndex = partition(arr, low, high, stats);
        
        // 根據基準位置決定下一步
        if (k == pivotIndex) {
            // 找到第k小元素
            if (stats != null) {
                stats.arrayAccess++;
            }
            return arr[pivotIndex];
        } else if (k < pivotIndex) {
            // 第k小元素在左半部分
            return quickSelect(arr, low, pivotIndex - 1, k, stats);
        } else {
            // 第k小元素在右半部分
            return quickSelect(arr, pivotIndex + 1, high, k, stats);
        }
    }
    
    // 找出所有第k小的元素（處理重複值）
    public static List<Integer> findAllKthSmallest(int[] array, int k, RecursionStats stats) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            return new ArrayList<>();
        }
        
        int kthValue = findKthSmallest(array, k, stats);
        List<Integer> result = new ArrayList<>();
        
        for (int num : array) {
            if (num == kthValue) {
                result.add(num);
            }
        }
        
        return result;
    }
    
    // 4. 遞迴檢查陣列是否存在子序列總和等於目標值
    public static boolean hasSubsetSum(int[] array, int targetSum) {
        return hasSubsetSum(array, targetSum, null);
    }
    
    public static boolean hasSubsetSum(int[] array, int targetSum, RecursionStats stats) {
        if (array == null) {
            return targetSum == 0;
        }
        
        long startTime = System.nanoTime();
        
        if (stats != null) {
            stats.reset();
        }
        
        boolean result = subsetSumRecursive(array, array.length - 1, targetSum, stats);
        
        if (stats != null) {
            stats.executionTime = System.nanoTime() - startTime;
        }
        
        return result;
    }
    
    private static boolean subsetSumRecursive(int[] arr, int index, int targetSum, RecursionStats stats) {
        if (stats != null) {
            stats.recursiveCalls++;
        }
        
        // 基本情況1：目標和為0，表示找到了解
        if (targetSum == 0) {
            return true;
        }
        
        // 基本情況2：沒有更多元素可選
        if (index < 0) {
            return false;
        }
        
        // 基本情況3：目標和為負數（假設陣列中都是正數）
        if (targetSum < 0) {
            return false;
        }
        
        if (stats != null) {
            stats.arrayAccess++;
        }
        
        // 選擇：包含當前元素或不包含當前元素
        return subsetSumRecursive(arr, index - 1, targetSum - arr[index], stats) ||  // 包含
               subsetSumRecursive(arr, index - 1, targetSum, stats);                 // 不包含
    }
    
    // 找出所有和等於目標值的子序列
    public static List<List<Integer>> findAllSubsetsWithSum(int[] array, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (array == null) {
            return result;
        }
        
        List<Integer> currentSubset = new ArrayList<>();
        findAllSubsetsRecursive(array, 0, targetSum, currentSubset, result);
        return result;
    }
    
    private static void findAllSubsetsRecursive(int[] arr, int index, int remainingSum,
                                              List<Integer> currentSubset, List<List<Integer>> result) {
        // 基本情況：找到符合條件的子序列
        if (remainingSum == 0) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }
        
        // 基本情況：沒有更多元素或剩餘和為負
        if (index >= arr.length || remainingSum < 0) {
            return;
        }
        
        // 包含當前元素
        currentSubset.add(arr[index]);
        findAllSubsetsRecursive(arr, index + 1, remainingSum - arr[index], currentSubset, result);
        currentSubset.remove(currentSubset.size() - 1); // 回溯
        
        // 不包含當前元素
        findAllSubsetsRecursive(arr, index + 1, remainingSum, currentSubset, result);
    }
    
    // 輔助方法：交換陣列中兩個元素
    private static void swap(int[] arr, int i, int j, RecursionStats stats) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            
            if (stats != null) {
                stats.swaps++;
                stats.arrayAccess += 4; // 兩次讀取，兩次寫入
            }
        }
    }
    
    // 產生測試資料
    public static int[] generateTestArray(int size, int maxValue) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue + 1);
        }
        return array;
    }
    
    // 產生特殊測試陣列
    public static int[] generateSpecialArray(String type, int size) {
        int[] array = new int[size];
        Random random = new Random();
        
        switch (type.toLowerCase()) {
            case "sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                break;
                
            case "reverse":
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
                
            case "duplicate":
                Arrays.fill(array, 5);
                break;
                
            case "nearly_sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                // 隨機打亂少數元素
                for (int i = 0; i < size / 10; i++) {
                    int pos1 = random.nextInt(size);
                    int pos2 = random.nextInt(size);
                    int temp = array[pos1];
                    array[pos1] = array[pos2];
                    array[pos2] = temp;
                }
                break;
                
            default:
                return generateTestArray(size, size * 2);
        }
        
        return array;
    }
    
    // 驗證陣列是否已排序
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    // 主程式
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("              進階遞迴陣列操作測試程式");
        System.out.println("=".repeat(70));
        
        // 1. 快速排序測試
        System.out.println("【測試 1: 遞迴快速排序】");
        int[] testArray1 = {64, 34, 25, 12, 22, 11, 90, 88, 76, 50};
        System.out.println("原始陣列: " + Arrays.toString(testArray1));
        
        RecursionStats quickSortStats = new RecursionStats("快速排序");
        int[] sorted1 = quickSort(testArray1, quickSortStats);
        
        System.out.println("排序結果: " + Arrays.toString(sorted1));
        System.out.println("結果正確: " + (isSorted(sorted1) ? "✓" : "✗"));
        System.out.println(quickSortStats);
        System.out.println();
        
        // 不同類型陣列的快速排序比較
        System.out.println("【不同陣列類型的快速排序效能比較】");
        String[] arrayTypes = {"random", "sorted", "reverse", "duplicate"};
        String[] typeNames = {"隨機", "已排序", "反向", "重複"};
        
        for (int i = 0; i < arrayTypes.length; i++) {
            int[] testArray = generateSpecialArray(arrayTypes[i], 20);
            RecursionStats normalStats = new RecursionStats("標準快排(" + typeNames[i] + ")");
            RecursionStats randomStats = new RecursionStats("隨機快排(" + typeNames[i] + ")");
            
            quickSort(testArray, normalStats);
            quickSortRandomPivot(testArray, randomStats);
            
            System.out.println(normalStats);
            System.out.println(randomStats);
            System.out.println();
        }
        
        // 2. 遞迴合併陣列測試
        System.out.println("【測試 2: 遞迴合併已排序陣列】");
        int[] sortedArray1 = {1, 3, 5, 7, 9, 11};
        int[] sortedArray2 = {2, 4, 6, 8, 10, 12, 14};
        
        System.out.println("陣列1: " + Arrays.toString(sortedArray1));
        System.out.println("陣列2: " + Arrays.toString(sortedArray2));
        
        RecursionStats mergeStats = new RecursionStats("遞迴合併");
        int[] merged = mergeArrays(sortedArray1, sortedArray2, mergeStats);
        
        System.out.println("合併結果: " + Arrays.toString(merged));
        System.out.println("結果正確: " + (isSorted(merged) ? "✓" : "✗"));
        System.out.println(mergeStats);
        System.out.println();
        
        // 3. 第k小元素測試
        System.out.println("【測試 3: 尋找第k小元素】");
        int[] testArray3 = {7, 2, 1, 6, 8, 5, 3, 4};
        System.out.println("測試陣列: " + Arrays.toString(testArray3));
        
        int[] sortedForVerification = testArray3.clone();
        Arrays.sort(sortedForVerification);
        System.out.println("排序後參考: " + Arrays.toString(sortedForVerification));
        System.out.println();
        
        for (int k = 1; k <= Math.min(8, testArray3.length); k++) {
            RecursionStats kthStats = new RecursionStats("第" + k + "小");
            int kthElement = findKthSmallest(testArray3, k, kthStats);
            
            System.out.printf("第 %d 小元素: %d", k, kthElement);
            System.out.printf(" (驗證: %d) ", sortedForVerification[k-1]);
            System.out.printf("%s - ", kthElement == sortedForVerification[k-1] ? "✓" : "✗");
            System.out.printf("遞迴: %d次, 比較: %d次\n", kthStats.recursiveCalls, kthStats.comparisons);
        }
        System.out.println();
        
        // 4. 子序列總和測試
        System.out.println("【測試 4: 子序列總和問題】");
        int[] testArray4 = {3, 34, 4, 12, 5, 2};
        int[] targetSums = {9, 11, 30, 50, 15};
        
        System.out.println("測試陣列: " + Arrays.toString(testArray4));
        System.out.println("陣列總和: " + Arrays.stream(testArray4).sum());
        System.out.println();
        
        for (int target : targetSums) {
            RecursionStats subsetStats = new RecursionStats("子序列總和=" + target);
            boolean hasSubset = hasSubsetSum(testArray4, target, subsetStats);
            List<List<Integer>> allSubsets = findAllSubsetsWithSum(testArray4, target);
            
            System.out.printf("目標總和 %d: %s\n", target, hasSubset ? "存在" : "不存在");
            if (hasSubset && allSubsets.size() <= 5) { // 只顯示前5個解
                System.out.println("  可能的子序列:");
                for (List<Integer> subset : allSubsets) {
                    System.out.println("    " + subset);
                }
            } else if (hasSubset) {
                System.out.printf("  找到 %d 種可能的子序列\n", allSubsets.size());
            }
            System.out.printf("  %s\n", subsetStats);
            System.out.println();
        }
        
        // 效能比較測試
        System.out.println("【效能比較測試】");
        int[] largeArray = generateTestArray(100, 100);
        
        // 快速排序 vs 內建排序
        System.out.println("大陣列快速排序效能測試 (100個元素):");
        
        RecursionStats quickStats = new RecursionStats("遞迴快速排序");
        int[] quickSorted = quickSort(largeArray, quickStats);
        
        long startTime = System.nanoTime();
        int[] javaSorted = largeArray.clone();
        Arrays.sort(javaSorted);
        long javaTime = System.nanoTime() - startTime;
        
        System.out.println(quickStats);
        System.out.printf("Java內建排序 - 時間: %.3f毫秒\n", javaTime / 1_000_000.0);
        System.out.println("結果一致: " + Arrays.equals(quickSorted, javaSorted));
        System.out.println();
        
        // 第k小元素 vs 排序後取值
        System.out.println("第k小元素效能比較:");
        int k = 25;
        
        RecursionStats selectStats = new RecursionStats("QuickSelect");
        int kthBySelect = findKthSmallest(largeArray, k, selectStats);
        
        startTime = System.nanoTime();
        int[] sortedArray = largeArray.clone();
        Arrays.sort(sortedArray);
        int kthBySort = sortedArray[k-1];
        long sortTime = System.nanoTime() - startTime;
        
        System.out.println(selectStats);
        System.out.printf("排序後取值 - 時間: %.3f毫秒\n", sortTime / 1_000_000.0);
        System.out.println("結果一致: " + (kthBySelect == kthBySort));
        System.out.printf("效能提升: %.1f倍\n", (double)sortTime / selectStats.executionTime);
        System.out.println();
        
        // 演算法複雜度分析
        System.out.println("【演算法時間複雜度分析】");
        System.out.println("1. 快速排序:");
        System.out.println("   - 平均情況: O(n log n)");
        System.out.println("   - 最壞情況: O(n²) - 基準選擇不當");
        System.out.println("   - 最佳情況: O(n log n)");
        System.out.println("   - 空間複雜度: O(log n) - 遞迴堆疊");
        
        System.out.println("\n2. 遞迴合併:");
        System.out.println("   - 時間複雜度: O(n + m)");
        System.out.println("   - 空間複雜度: O(log(n + m)) - 遞迴堆疊");
        
        System.out.println("\n3. QuickSelect (第k小):");
        System.out.println("   - 平均情況: O(n)");
        System.out.println("   - 最壞情況: O(n²)");
        System.out.println("   - 空間複雜度: O(log n)");
        
        System.out.println("\n4. 子序列總和:");
        System.out.println("   - 時間複雜度: O(2^n) - 指數時間");
        System.out.println("   - 空間複雜度: O(n) - 遞迴深度");
        System.out.println("   - 可用動態規劃優化至 O(n × sum)");
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                測試完成");
        System.out.println("=".repeat(70));
    }
}