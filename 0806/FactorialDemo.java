public class FactorialDemo {
    
    /**
     * 遞迴計算階乘
     * n! = n × (n-1) × (n-2) × ... × 1
     * 遞迴關係：n! = n × (n-1)!
     * 停止條件：0! = 1, 1! = 1
     */
    public static long factorial(int n) {
        // 停止條件
        if (n <= 1) {
            System.out.println(n + "! = 1");
            return 1;
        }
        
        // 遞迴關係
        System.out.println(n + "! = " + n + " × " + (n-1) + "!");
        long result = n * factorial(n - 1);
        System.out.println(n + "! = " + result);
        
        return result;
    }
    
    /**
     * 迭代版本的階乘（比較用）
     */
    public static long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("=== 遞迴版本階乘 ===");
        System.out.println("計算 5!:");
        long result1 = factorial(5);
        System.out.println("最終結果：" + result1);
        
        System.out.println("\n=== 迭代版本階乘 ===");
        long result2 = factorialIterative(5);
        System.out.println("5! = " + result2);
        
        System.out.println("\n=== 更多測試 ===");
        for (int i = 0; i <= 6; i++) {
            System.out.printf("%d! = %d\n", i, factorial(i));
        }
    }
}
