
import java.util.Arrays;

public class ArrayErrorHandlingDemo {
    /**
     * 安全的陣列存取方法
     */
    public static int safeGet(int[] array, int index, int defaultValue) {
        if (array == null) {
            System.out.println("警告：陣列為 null，返回預設值");
            return defaultValue;
        }
        
        if (index < 0 || index >= array.length) {
            System.out.printf("警告：索引 %d 超出範圍 [0, %d]，返回預設值\n", 
                            index, array.length - 1);
            return defaultValue;
        }
        
        return array[index];
    }
    
    /**
     * 安全的陣列設定方法
     */
    public static boolean safeSet(int[] array, int index, int value) {
        if (array == null) {
            System.out.println("錯誤：無法在 null 陣列上設定值");
            return false;
        }
        
        if (index < 0 || index >= array.length) {
            System.out.printf("錯誤：無法在索引 %d 設定值，有效範圍是 [0, %d]\n", 
                            index, array.length - 1);
            return false;
        }
        
        array[index] = value;
        System.out.printf("成功：array[%d] = %d\n", index, value);
        return true;
    }
    
    /**
     * 展示常見的陣列錯誤
     */
    public static void demonstrateCommonErrors() {
        int[] numbers = {10, 20, 30, 40, 50};
        
        System.out.println("陣列內容：" + Arrays.toString(numbers));
        System.out.printf("陣列長度：%d\n", numbers.length);
        System.out.printf("有效索引範圍：0 到 %d\n", numbers.length - 1);
        System.out.println();
        
        // 錯誤情況展示
        System.out.println("=== 常見錯誤處理 ===");
        
        try {
            int value = numbers[numbers.length];  // 錯誤！索引超出範圍
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕獲錯誤：陣列索引超出範圍");
        }
        
        try {
            int value = numbers[-1];  // 錯誤！負數索引
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕獲錯誤：負數索引");
        }
    }
    
    public static void main(String[] args) {
        demonstrateCommonErrors();
        
        System.out.println("\n=== 安全存取示範 ===");
        int[] numbers = {10, 20, 30, 40, 50};
        
        // 測試安全存取
        System.out.println("正常存取：" + safeGet(numbers, 2, -1));
        System.out.println("越界存取：" + safeGet(numbers, 10, -1));
        System.out.println("負數索引：" + safeGet(numbers, -1, -1));
        System.out.println("null 陣列：" + safeGet(null, 0, -1));
        
        System.out.println("\n安全設定測試：");
        safeSet(numbers, 2, 999);   // 正常
        safeSet(numbers, 10, 888);  // 越界
        safeSet(null, 0, 777);      // null 陣列
        
        System.out.println("最終陣列：" + Arrays.toString(numbers));
    }
}
