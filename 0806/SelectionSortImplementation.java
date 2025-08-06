
import java.util.Arrays;
import java.util.Random;

public class SelectionSortImplementation {
    
    // 排序統計資料類別
    public static class SortStatistics {
        public int comparisons;      // 比較次數
        public int swaps;           // 交換次數
        public long executionTime;  // 執行時間(奈秒)
        public String algorithmName; // 演算法名稱
        
        public SortStatistics(String algorithmName) {
            this.algorithmName = algorithmName;
            this.comparisons = 0;
            this.swaps = 0;
            this.executionTime = 0;
        }
        
        public void reset() {
            comparisons = 0;
            swaps = 0;
            executionTime = 0;
        }
        
        @Override
        public String toString() {
            return String.format("%s - 比較: %d次, 交換: %d次, 時間: %.3f毫秒",
                algorithmName, comparisons, swaps, executionTime / 1_000_000.0);
        }
    }
    
    // 1. 實作基本的選擇排序
    public static int[] selectionSort(int[] array) {
        return selectionSort(array, false, null);
    }
    
    // 2. 顯示每一輪的排序過程的選擇排序
    public static int[] selectionSortWithProcess(int[] array) {
        return selectionSort(array, true, null);
    }
    
    // 3. 計算統計資料的選擇排序
    public static int[] selectionSortWithStats(int[] array, SortStatistics stats) {
        return selectionSort(array, false, stats);
    }
    
    // 完整的選擇排序實作
    private static int[] selectionSort(int[] array, boolean showProcess, SortStatistics stats) {
        if (array == null || array.length <= 1) {
            return array == null ? new int[0] : array.clone();
        }
        
        int[] arr = array.clone(); // 避免修改原陣列
        int n = arr.length;
        
        if (stats != null) {
            stats.reset();
        }
        
        long startTime = System.nanoTime();
        
        if (showProcess) {
            System.out.println("=== 選擇排序過程 ===");
            System.out.println("初始陣列: " + Arrays.toString(arr));
            System.out.println();
        }
        
        // 選擇排序主要邏輯
        for (int i = 0; i < n - 1; i++) {
            // 找到剩餘元素中的最小值索引
            int minIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                if (stats != null) {
                    stats.comparisons++;
                }
                
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // 如果找到更小的元素，進行交換
            if (minIndex != i) {
                swap(arr, i, minIndex);
                if (stats != null) {
                    stats.swaps++;
                }
            }
            
            // 顯示每輪過程
            if (showProcess) {
                System.out.printf("第 %2d 輪: ", i + 1);
                printArrayWithHighlight(arr, i, minIndex);
                System.out.printf("       找到最小值 %d (位置 %d), ", arr[i], minIndex);
                if (minIndex != i) {
                    System.out.println("進行交換");
                } else {
                    System.out.println("無需交換");
                }
            }
        }
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        if (showProcess) {
            System.out.println("\n最終結果: " + Arrays.toString(arr));
            System.out.println("=".repeat(50));
        }
        
        return arr;
    }
    
    // 4. 氣泡排序實作（用於比較）
    public static int[] bubbleSort(int[] array, SortStatistics stats) {
        if (array == null || array.length <= 1) {
            return array == null ? new int[0] : array.clone();
        }
        
        int[] arr = array.clone();
        int n = arr.length;
        
        if (stats != null) {
            stats.reset();
        }
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (stats != null) {
                    stats.comparisons++;
                }
                
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                    if (stats != null) {
                        stats.swaps++;
                    }
                }
            }
            
            // 如果沒有交換發生，陣列已排序完成
            if (!swapped) {
                break;
            }
        }
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        return arr;
    }
    
    // 插入排序實作（額外比較）
    public static int[] insertionSort(int[] array, SortStatistics stats) {
        if (array == null || array.length <= 1) {
            return array == null ? new int[0] : array.clone();
        }
        
        int[] arr = array.clone();
        int n = arr.length;
        
        if (stats != null) {
            stats.reset();
        }
        
        long startTime = System.nanoTime();
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0) {
                if (stats != null) {
                    stats.comparisons++;
                }
                
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                    if (stats != null) {
                        stats.swaps++;
                    }
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        return arr;
    }
    
    // 交換兩個元素
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    // 列印陣列並高亮特定位置
    private static void printArrayWithHighlight(int[] array, int sortedEnd, int minIndex) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            if (i <= sortedEnd) {
                System.out.print("*" + array[i] + "*"); // 已排序部分
            } else if (i == minIndex) {
                System.out.print("(" + array[i] + ")"); // 找到的最小值
            } else {
                System.out.print(array[i]);
            }
            
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
    
    // 生成測試資料
    public static int[] generateRandomArray(int size, int maxValue) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue + 1);
        }
        return array;
    }
    
    // 生成特殊測試資料
    public static int[] generateSpecialArray(String type, int size) {
        int[] array = new int[size];
        
        switch (type.toLowerCase()) {
            case "sorted":
                // 已排序陣列
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                break;
                
            case "reverse":
                // 反向排序陣列
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
                
            case "duplicate":
                // 重複元素陣列
                for (int i = 0; i < size; i++) {
                    array[i] = 5; // 全部設為5
                }
                break;
                
            case "nearly_sorted":
                // 接近已排序的陣列
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                // 隨機交換幾個位置
                Random random = new Random();
                for (int i = 0; i < size / 10; i++) {
                    int pos1 = random.nextInt(size);
                    int pos2 = random.nextInt(size);
                    swap(array, pos1, pos2);
                }
                break;
                
            default:
                // 隨機陣列
                return generateRandomArray(size, size * 2);
        }
        
        return array;
    }
    
    // 效能比較測試
    public static void performanceComparison(int[] testArray) {
        System.out.println("=".repeat(70));
        System.out.println("                    演算法效能比較");
        System.out.println("=".repeat(70));
        System.out.println("測試陣列大小: " + testArray.length);
        System.out.println("測試陣列: " + Arrays.toString(testArray));
        System.out.println();
        
        // 統計物件
        SortStatistics selectionStats = new SortStatistics("選擇排序");
        SortStatistics bubbleStats = new SortStatistics("氣泡排序");
        SortStatistics insertionStats = new SortStatistics("插入排序");
        
        // 執行排序
        int[] selectionResult = selectionSortWithStats(testArray, selectionStats);
        int[] bubbleResult = bubbleSort(testArray, bubbleStats);
        int[] insertionResult = insertionSort(testArray, insertionStats);
        
        // 驗證結果正確性
        System.out.println("【排序結果驗證】");
        System.out.println("選擇排序結果: " + Arrays.toString(selectionResult));
        System.out.println("結果正確性: " + (isSorted(selectionResult) ? "✓ 正確" : "✗ 錯誤"));
        System.out.println();
        
        // 顯示統計結果
        System.out.println("【效能統計】");
        System.out.println(selectionStats);
        System.out.println(bubbleStats);
        System.out.println(insertionStats);
        System.out.println();
        
        // 效能分析
        System.out.println("【效能分析】");
        analyzePerformance(selectionStats, bubbleStats, insertionStats);
    }
    
    // 效能分析
    private static void analyzePerformance(SortStatistics selection, SortStatistics bubble, SortStatistics insertion) {
        System.out.println("比較次數分析:");
        System.out.printf("  選擇排序 vs 氣泡排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.comparisons, bubble.comparisons),
            selection.comparisons < bubble.comparisons ? "較少" : "較多");
        System.out.printf("  選擇排序 vs 插入排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.comparisons, insertion.comparisons),
            selection.comparisons < insertion.comparisons ? "較少" : "較多");
        
        System.out.println("\n交換次數分析:");
        System.out.printf("  選擇排序 vs 氣泡排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.swaps, bubble.swaps),
            selection.swaps < bubble.swaps ? "較少" : "較多");
        System.out.printf("  選擇排序 vs 插入排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.swaps, insertion.swaps),
            selection.swaps < insertion.swaps ? "較少" : "較多");
        
        System.out.println("\n執行時間分析:");
        System.out.printf("  選擇排序 vs 氣泡排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.executionTime, bubble.executionTime),
            selection.executionTime < bubble.executionTime ? "較快" : "較慢");
        System.out.printf("  選擇排序 vs 插入排序: %.2f%% (%s)\n",
            getPercentageDiff(selection.executionTime, insertion.executionTime),
            selection.executionTime < insertion.executionTime ? "較快" : "較慢");
        
        // 最佳演算法推薦
        System.out.println("\n【推薦分析】");
        recommendBestAlgorithm(selection, bubble, insertion);
    }
    
    // 計算百分比差異
    private static double getPercentageDiff(long value1, long value2) {
        if (value2 == 0) return value1 == 0 ? 0 : 100;
        return Math.abs((double)(value1 - value2) / value2 * 100);
    }
    
    // 推薦最佳演算法
    private static void recommendBestAlgorithm(SortStatistics... stats) {
        SortStatistics fastest = stats[0];
        SortStatistics fewestComparisons = stats[0];
        SortStatistics fewestSwaps = stats[0];
        
        for (SortStatistics stat : stats) {
            if (stat.executionTime < fastest.executionTime) {
                fastest = stat;
            }
            if (stat.comparisons < fewestComparisons.comparisons) {
                fewestComparisons = stat;
            }
            if (stat.swaps < fewestSwaps.swaps) {
                fewestSwaps = stat;
            }
        }
        
        System.out.println("最快執行: " + fastest.algorithmName);
        System.out.println("最少比較: " + fewestComparisons.algorithmName);
        System.out.println("最少交換: " + fewestSwaps.algorithmName);
    }
    
    // 檢查陣列是否已排序
    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    // 主程式
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("                選擇排序演算法實作");
        System.out.println("=".repeat(60));
        
        // 測試 1: 基本功能展示
        System.out.println("【測試 1: 基本選擇排序】");
        int[] testArray1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("原始陣列: " + Arrays.toString(testArray1));
        int[] sorted1 = selectionSort(testArray1);
        System.out.println("排序結果: " + Arrays.toString(sorted1));
        System.out.println();
        
        // 測試 2: 顯示排序過程
        System.out.println("【測試 2: 排序過程展示】");
        int[] testArray2 = {29, 10, 14, 37, 13};
        selectionSortWithProcess(testArray2);
        System.out.println();
        
        // 測試 3: 小規模效能比較
        System.out.println("【測試 3: 小規模陣列效能比較】");
        int[] smallArray = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        performanceComparison(smallArray);
        System.out.println();
        
        // 測試 4: 不同類型陣列的效能比較
        System.out.println("【測試 4: 不同類型陣列效能分析】");
        int[] sortedArray = generateSpecialArray("sorted", 15);
        int[] reverseArray = generateSpecialArray("reverse", 15);
        int[] randomArray = generateRandomArray(15, 50);
        
        System.out.println("4.1 已排序陣列:");
        performanceComparison(sortedArray);
        
        System.out.println("4.2 反向排序陣列:");
        performanceComparison(reverseArray);
        
        System.out.println("4.3 隨機陣列:");
        performanceComparison(randomArray);
        
        // 測試 5: 大規模陣列效能測試
        System.out.println("【測試 5: 大規模陣列效能測試】");
        int[] largeArray = generateRandomArray(1000, 1000);
        
        SortStatistics selectionLarge = new SortStatistics("選擇排序(大陣列)");
        SortStatistics bubbleLarge = new SortStatistics("氣泡排序(大陣列)");
        SortStatistics insertionLarge = new SortStatistics("插入排序(大陣列)");
        
        System.out.println("正在測試 1000 個元素的陣列...");
        selectionSortWithStats(largeArray, selectionLarge);
        bubbleSort(largeArray, bubbleLarge);
        insertionSort(largeArray, insertionLarge);
        
        System.out.println("\n大規模測試結果:");
        System.out.println(selectionLarge);
        System.out.println(bubbleLarge);
        System.out.println(insertionLarge);
        
        // 理論分析
        System.out.println("\n【理論時間複雜度分析】");
        System.out.println("選擇排序:");
        System.out.println("  最佳情況: O(n²) - 比較次數固定");
        System.out.println("  平均情況: O(n²) - 比較次數固定");
        System.out.println("  最壞情況: O(n²) - 比較次數固定");
        System.out.println("  空間複雜度: O(1) - 原地排序");
        System.out.println("  穩定性: 不穩定");
        
        System.out.println("\n氣泡排序:");
        System.out.println("  最佳情況: O(n) - 已排序時可提早結束");
        System.out.println("  平均情況: O(n²)");
        System.out.println("  最壞情況: O(n²)");
        System.out.println("  穩定性: 穩定");
        
        System.out.println("\n插入排序:");
        System.out.println("  最佳情況: O(n) - 已排序陣列");
        System.out.println("  平均情況: O(n²)");
        System.out.println("  最壞情況: O(n²) - 反向排序");
        System.out.println("  穩定性: 穩定");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                測試完成");
        System.out.println("=".repeat(60));
    }
}