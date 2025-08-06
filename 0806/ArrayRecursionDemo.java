import java.util.Arrays;

public class ArrayRecursionDemo {
    
    /**
     * 遞迴計算陣列總和
     * 思考方式：總和 = 第一個元素 + 剩餘元素的總和
     * 時間複雜度：O(n)
     * 空間複雜度：O(n) - 遞迴呼叫堆疊
     */
    public static int arraySum(int[] arr, int index) {
        // 停止條件：超出陣列範圍
        if (index >= arr.length) {
            return 0;
        }
        
        System.out.printf("arraySum(arr, %d) = %d + arraySum(arr, %d)\n", 
                        index, arr[index], index + 1);
        
        // 遞迴關係：當前元素 + 剩餘元素總和
        return arr[index] + arraySum(arr, index + 1);
    }
    
    /**
     * 遞迴尋找陣列最大值
     * 思考方式：比較第一個元素和剩餘元素的最大值
     */
    public static int findMax(int[] arr, int index) {
        // 停止條件：到達最後一個元素
        if (index == arr.length - 1) {
            return arr[index];
        }
        
        // 遞迴關係：當前元素與剩餘最大值比較
        int maxOfRest = findMax(arr, index + 1);
        int currentMax = Math.max(arr[index], maxOfRest);
        
        System.out.printf("比較 arr[%d]=%d 與剩餘最大值 %d，結果：%d\n", 
                        index, arr[index], maxOfRest, currentMax);
        
        return currentMax;
    }
    
    /**
     * 遞迴檢查陣列是否已排序
     */
    public static boolean isSorted(int[] arr, int index) {
        // 停止條件：檢查到最後一個元素
        if (index >= arr.length - 1) {
            return true;
        }
        
        // 如果當前元素大於下一個元素，則未排序
        if (arr[index] > arr[index + 1]) {
            return false;
        }
        
        // 遞迴檢查剩餘部分
        return isSorted(arr, index + 1);
    }
    
    /**
     * 遞迴列印陣列
     */
    public static void printArray(int[] arr, int index) {
        if (index >= arr.length) {
            System.out.println();
            return;
        }
        
        System.out.print(arr[index] + " ");
        printArray(arr, index + 1);
    }
    
    /**
     * 遞迴反向列印陣列
     */
    public static void printArrayReverse(int[] arr, int index) {
        if (index >= arr.length) {
            return;
        }
        
        printArrayReverse(arr, index + 1);
        System.out.print(arr[index] + " ");
    }
    
    public static void main(String[] args) {
        int[] numbers = {3, 7, 2, 9, 1, 5};
        int[] sortedNumbers = {1, 3, 5, 7, 9};
        
        System.out.println("測試陣列：" + Arrays.toString(numbers));
        System.out.println();
        
        // 陣列總和
        System.out.println("=== 陣列總和 ===");
        int sum = arraySum(numbers, 0);
        System.out.println("總和：" + sum);
        
        System.out.println("\n=== 尋找最大值 ===");
        int max = findMax(numbers, 0);
        System.out.println("最大值：" + max);
        
        System.out.println("\n=== 檢查排序狀態 ===");
        System.out.println("numbers 是否已排序：" + isSorted(numbers, 0));
        System.out.println("sortedNumbers 是否已排序：" + isSorted(sortedNumbers, 0));
        
        System.out.println("\n=== 陣列列印 ===");
        System.out.print("正向列印：");
        printArray(numbers, 0);
        System.out.print("反向列印：");
        printArrayReverse(numbers, 0);
        System.out.println();
    }
}
