public class MathRecursionDemo {
    
    /**
     * 遞迴計算最大公因數（歐幾里德演算法）
     * gcd(a, b) = gcd(b, a % b)
     * 停止條件：b = 0 時，gcd = a
     */
    public static int gcd(int a, int b) {
        System.out.printf("gcd(%d, %d)\n", a, b);
        
        // 停止條件
        if (b == 0) {
            System.out.printf("gcd(%d, 0) = %d\n", a, a);
            return a;
        }
        
        // 遞迴關係
        return gcd(b, a % b);
    }
    
    /**
     * 遞迴計算數字各位數總和
     * 思考方式：最後一位數字 + 剩餘部分的數字總和
     */
    public static int digitSum(int n) {
        System.out.printf("digitSum(%d)\n", n);
        
        // 停止條件：單位數
        if (n < 10) {
            return n;
        }
        
        // 遞迴關係：最後一位 + 剩餘部分
        int lastDigit = n % 10;
        int restSum = digitSum(n / 10);
        
        System.out.printf("digitSum(%d) = %d + digitSum(%d) = %d + %d = %d\n", 
                        n, lastDigit, n / 10, lastDigit, restSum, lastDigit + restSum);
        
        return lastDigit + restSum;
    }
    
    /**
     * 遞迴計算次方
     * pow(base, exp) = base * pow(base, exp-1)
     */
    public static long power(int base, int exp) {
        // 停止條件
        if (exp == 0) {
            return 1;
        }
        if (exp == 1) {
            return base;
        }
        
        // 遞迴關係
        return base * power(base, exp - 1);
    }
    
    /**
     * 優化的次方計算（快速冪）
     * 利用 a^n = (a^(n/2))^2 的性質
     */
    public static long powerFast(int base, int exp) {
        if (exp == 0) return 1;
        if (exp == 1) return base;
        
        long half = powerFast(base, exp / 2);
        long result = half * half;
        
        // 如果指數是奇數，再乘一次底數
        if (exp % 2 == 1) {
            result *= base;
        }
        
        return result;
    }
    
    /**
     * 遞迴判斷質數
     */
    public static boolean isPrime(int n, int divisor) {
        if (n <= 1) return false;
        if (divisor * divisor > n) return true;
        if (n % divisor == 0) return false;
        
        return isPrime(n, divisor + 1);
    }
    
    public static void main(String[] args) {
        // 最大公因數
        System.out.println("=== 最大公因數 ===");
        int result1 = gcd(48, 18);
        System.out.println("gcd(48, 18) = " + result1);
        
        System.out.println("\n=== 數字各位數總和 ===");
        int result2 = digitSum(12345);
        System.out.println("digitSum(12345) = " + result2);
        
        // 次方計算
        System.out.println("\n=== 次方計算 ===");
        System.out.println("2^10 = " + power(2, 10));
        System.out.println("2^10 (快速) = " + powerFast(2, 10));
        
        // 質數判斷
        System.out.println("\n=== 質數判斷 ===");
        int[] testNumbers = {2, 17, 25, 29, 30};
        for (int num : testNumbers) {
            boolean isPrimeResult = isPrime(num, 2);
            System.out.printf("%d 是質數：%s\n", num, isPrimeResult);
        }
    }
}
