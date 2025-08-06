
import java.util.Arrays;

public class BinarySearchDemo {
    /**
     * 自製二分搜尋演算法
     * 前提條件：陣列必須已經排序
     * 時間複雜度：O(log n)
     * 空間複雜度：O(1)
     */
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int comparisons = 0;
        
        System.out.println("二分搜尋過程：");
        
        while (left <= right) {
            int mid = left + (right - left) / 2;  // 防止整數溢位的寫法
            comparisons++;
            
            System.out.printf("第 %d 次比較：範圍[%d, %d]，中點索引=%d，中點值=%d，目標=%d\n", 
                            comparisons, left, right, mid, array[mid], target);
            
            if (array[mid] == target) {
                System.out.printf("找到目標值！總共比較了 %d 次\n", comparisons);
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
                System.out.println("目標值較大，搜尋右半部");
            } else {
                right = mid - 1;
                System.out.println("目標值較小，搜尋左半部");
            }
        }
        
        System.out.printf("找不到目標值，總共比較了 %d 次\n", comparisons);
        return -1;
    }
    
    public static void main(String[] args) {
        int[] unsortedNumbers = {64, 25, 12, 22, 11, 90, 88, 76, 50, 42};
        
        // 先排序陣列（二分搜尋的前提條件）
        int[] sortedNumbers = unsortedNumbers.clone();
        Arrays.sort(sortedNumbers);
        
        int target = 50;
        
        System.out.println("原始陣列：" + Arrays.toString(unsortedNumbers));
        System.out.println("排序後陣列：" + Arrays.toString(sortedNumbers));
        System.out.println("搜尋目標：" + target);
        System.out.println();
        
        // 自製二分搜尋
        System.out.println("=== 自製二分搜尋 ===");
        int result = binarySearch(sortedNumbers, target);
        
        if (result != -1) {
            System.out.printf("在索引位置 %d 找到目標值 %d\n", result, target);
        } else {
            System.out.printf("找不到目標值 %d\n", target);
        }
        
        // 使用 Java 內建的二分搜尋
        System.out.println("\n=== Java 內建二分搜尋 ===");
        int builtInResult = Arrays.binarySearch(sortedNumbers, target);
        System.out.printf("Java 內建方法結果：%d\n", builtInResult);
    }
}
