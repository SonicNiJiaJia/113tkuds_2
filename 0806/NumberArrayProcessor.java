
import java.util.*;

public class NumberArrayProcessor {
    
    // 1. 移除陣列中的重複元素
    public static int[] removeDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        
        // 使用 LinkedHashSet 保持原始順序
        Set<Integer> uniqueElements = new LinkedHashSet<>();
        for (int num : array) {
            uniqueElements.add(num);
        }
        
        // 轉換回陣列
        return uniqueElements.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 移除重複元素（不使用集合的方法）
    public static int[] removeDuplicatesManually(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            boolean isDuplicate = false;
            // 檢查當前元素是否已存在於結果中
            for (int j = 0; j < result.size(); j++) {
                if (array[i] == result.get(j)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                result.add(array[i]);
            }
        }
        
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    
    // 2. 合併兩個已排序的陣列
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        if (array1 == null) array1 = new int[0];
        if (array2 == null) array2 = new int[0];
        
        int[] merged = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        
        // 比較兩個陣列的元素，將較小的先加入結果
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                merged[k++] = array1[i++];
            } else {
                merged[k++] = array2[j++];
            }
        }
        
        // 加入剩餘的元素
        while (i < array1.length) {
            merged[k++] = array1[i++];
        }
        
        while (j < array2.length) {
            merged[k++] = array2[j++];
        }
        
        return merged;
    }
    
    // 3. 找出陣列中出現頻率最高的元素
    public static class FrequencyResult {
        public final int element;
        public final int frequency;
        public final List<Integer> allMostFrequent;
        
        public FrequencyResult(int element, int frequency, List<Integer> allMostFrequent) {
            this.element = element;
            this.frequency = frequency;
            this.allMostFrequent = new ArrayList<>(allMostFrequent);
        }
    }
    
    public static FrequencyResult findMostFrequent(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("陣列不能為空");
        }
        
        // 計算每個元素的出現次數
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // 找出最高頻率
        int maxFrequency = Collections.max(frequencyMap.values());
        
        // 找出所有具有最高頻率的元素
        List<Integer> mostFrequent = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                mostFrequent.add(entry.getKey());
            }
        }
        
        // 返回第一個最高頻率元素（也可以選擇其他策略）
        return new FrequencyResult(mostFrequent.get(0), maxFrequency, mostFrequent);
    }
    
    // 取得完整的頻率統計
    public static Map<Integer, Integer> getFrequencyMap(int[] array) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        return frequencyMap;
    }
    
    // 4. 將陣列分割成兩個相等（或近似相等）的子陣列
    public static class SplitResult {
        public final int[] firstHalf;
        public final int[] secondHalf;
        
        public SplitResult(int[] firstHalf, int[] secondHalf) {
            this.firstHalf = firstHalf.clone();
            this.secondHalf = secondHalf.clone();
        }
    }
    
    // 按位置分割（簡單分割）
    public static SplitResult splitArrayByPosition(int[] array) {
        if (array == null || array.length == 0) {
            return new SplitResult(new int[0], new int[0]);
        }
        
        int midPoint = (array.length + 1) / 2; // 向上取整，確保第一半不會比第二半小
        
        int[] firstHalf = Arrays.copyOfRange(array, 0, midPoint);
        int[] secondHalf = Arrays.copyOfRange(array, midPoint, array.length);
        
        return new SplitResult(firstHalf, secondHalf);
    }
    
    // 按總和分割（盡量平均分配總和）
    public static SplitResult splitArrayBySum(int[] array) {
        if (array == null || array.length == 0) {
            return new SplitResult(new int[0], new int[0]);
        }
        
        if (array.length == 1) {
            return new SplitResult(array, new int[0]);
        }
        
        // 計算總和
        long totalSum = 0;
        for (int num : array) {
            totalSum += num;
        }
        long targetSum = totalSum / 2;
        
        // 動態規劃找出最接近一半總和的子集
        boolean[][] dp = new boolean[array.length + 1][(int)targetSum + 1];
        dp[0][0] = true;
        
        for (int i = 1; i <= array.length; i++) {
            for (int j = 0; j <= targetSum; j++) {
                dp[i][j] = dp[i-1][j];
                if (j >= array[i-1]) {
                    dp[i][j] = dp[i][j] || dp[i-1][j - array[i-1]];
                }
            }
        }
        
        // 找出最接近 targetSum 的實際值
        int bestSum = 0;
        for (int sum = (int)targetSum; sum >= 0; sum--) {
            if (dp[array.length][sum]) {
                bestSum = sum;
                break;
            }
        }
        
        // 回溯找出具體的元素組合
        List<Integer> firstGroup = new ArrayList<>();
        List<Integer> secondGroup = new ArrayList<>();
        
        int i = array.length;
        int sum = bestSum;
        while (i > 0 && sum > 0) {
            if (sum >= array[i-1] && dp[i-1][sum - array[i-1]]) {
                firstGroup.add(array[i-1]);
                sum -= array[i-1];
            } else {
                secondGroup.add(array[i-1]);
            }
            i--;
        }
        
        // 加入剩餘元素到第二組
        while (i > 0) {
            secondGroup.add(array[i-1]);
            i--;
        }
        
        int[] firstHalf = firstGroup.stream().mapToInt(Integer::intValue).toArray();
        int[] secondHalf = secondGroup.stream().mapToInt(Integer::intValue).toArray();
        
        return new SplitResult(firstHalf, secondHalf);
    }
    
    // 輔助方法：計算陣列總和
    public static long arraySum(int[] array) {
        long sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
    
    // 輔助方法：列印陣列
    public static void printArray(int[] array, String title) {
        System.out.printf("%s: %s\n", title, Arrays.toString(array));
    }
    
    // 輔助方法：列印頻率統計
    public static void printFrequencyMap(Map<Integer, Integer> frequencyMap) {
        System.out.println("頻率統計：");
        frequencyMap.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .forEach(entry -> 
                System.out.printf("  %d: %d 次\n", entry.getKey(), entry.getValue()));
    }
    
    // 主程式測試
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("              數字陣列處理器測試程式");
        System.out.println("=".repeat(60));
        
        // 測試資料
        int[] originalArray = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9};
        int[] sortedArray1 = {1, 3, 5, 7, 9};
        int[] sortedArray2 = {2, 4, 6, 8, 10, 12};
        int[] splitTestArray = {10, 20, 30, 40, 50, 60};
        
        printArray(originalArray, "原始陣列");
        System.out.println();
        
        // 1. 測試移除重複元素
        System.out.println("【移除重複元素測試】");
        int[] noDuplicates = removeDuplicates(originalArray);
        printArray(noDuplicates, "移除重複後");
        
        int[] noDuplicatesManual = removeDuplicatesManually(originalArray);
        printArray(noDuplicatesManual, "手動移除重複後");
        
        System.out.printf("原始長度：%d，去重後長度：%d\n\n", 
            originalArray.length, noDuplicates.length);
        
        // 2. 測試合併已排序陣列
        System.out.println("【合併已排序陣列測試】");
        printArray(sortedArray1, "已排序陣列1");
        printArray(sortedArray2, "已排序陣列2");
        
        int[] merged = mergeSortedArrays(sortedArray1, sortedArray2);
        printArray(merged, "合併後");
        System.out.println();
        
        // 3. 測試找出最高頻率元素
        System.out.println("【最高頻率元素測試】");
        try {
            FrequencyResult result = findMostFrequent(originalArray);
            System.out.printf("最高頻率元素：%d (出現 %d 次)\n", 
                result.element, result.frequency);
            System.out.printf("所有最高頻率元素：%s\n", result.allMostFrequent);
            
            System.out.println();
            printFrequencyMap(getFrequencyMap(originalArray));
        } catch (IllegalArgumentException e) {
            System.out.println("錯誤：" + e.getMessage());
        }
        System.out.println();
        
        // 4. 測試陣列分割
        System.out.println("【陣列分割測試】");
        printArray(splitTestArray, "待分割陣列");
        System.out.printf("陣列總和：%d\n", arraySum(splitTestArray));
        
        // 按位置分割
        System.out.println("\n按位置分割：");
        SplitResult positionSplit = splitArrayByPosition(splitTestArray);
        printArray(positionSplit.firstHalf, "第一半");
        printArray(positionSplit.secondHalf, "第二半");
        System.out.printf("第一半總和：%d，第二半總和：%d\n",
            arraySum(positionSplit.firstHalf), arraySum(positionSplit.secondHalf));
        
        // 按總和分割
        System.out.println("\n按總和分割（盡量平均）：");
        SplitResult sumSplit = splitArrayBySum(splitTestArray);
        printArray(sumSplit.firstHalf, "第一組");
        printArray(sumSplit.secondHalf, "第二組");
        System.out.printf("第一組總和：%d，第二組總和：%d\n",
            arraySum(sumSplit.firstHalf), arraySum(sumSplit.secondHalf));
        
        // 額外測試：複雜分割案例
        System.out.println("\n【複雜分割測試】");
        int[] complexArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        printArray(complexArray, "複雜測試陣列");
        System.out.printf("陣列總和：%d\n", arraySum(complexArray));
        
        SplitResult complexSplit = splitArrayBySum(complexArray);
        printArray(complexSplit.firstHalf, "第一組");
        printArray(complexSplit.secondHalf, "第二組");
        System.out.printf("第一組總和：%d，第二組總和：%d，差異：%d\n",
            arraySum(complexSplit.firstHalf), arraySum(complexSplit.secondHalf),
            Math.abs(arraySum(complexSplit.firstHalf) - arraySum(complexSplit.secondHalf)));
        
        // 邊界情況測試
        System.out.println("\n【邊界情況測試】");
        
        // 空陣列測試
        int[] emptyArray = {};
        printArray(removeDuplicates(emptyArray), "空陣列去重");
        printArray(mergeSortedArrays(emptyArray, new int[]{1, 2, 3}), "空陣列合併");
        
        // 單元素陣列測試
        int[] singleElement = {42};
        printArray(removeDuplicates(singleElement), "單元素陣列去重");
        SplitResult singleSplit = splitArrayByPosition(singleElement);
        printArray(singleSplit.firstHalf, "單元素分割-第一半");
        printArray(singleSplit.secondHalf, "單元素分割-第二半");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                測試完成");
        System.out.println("=".repeat(60));
    }
}