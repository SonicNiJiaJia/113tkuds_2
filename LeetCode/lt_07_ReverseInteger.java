public class lt_07_ReverseInteger {
    public int reverse(int x) {
        int result = 0;
        
        while (x != 0) {
            int digit = x % 10;
            
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            
            result = result * 10 + digit;
            x /= 10;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        int x1 = 123;
        int result1 = solution.reverse(x1);
        System.out.println("Input: x = " + x1);
        System.out.println("Output: " + result1);
        System.out.println();
        
        int x2 = -123;
        int result2 = solution.reverse(x2);
        System.out.println("Input: x = " + x2);
        System.out.println("Output: " + result2);
        System.out.println();
        
        int x3 = 120;
        int result3 = solution.reverse(x3);
        System.out.println("Input: x = " + x3);
        System.out.println("Output: " + result3);
        System.out.println();
        
        int x4 = 1534236469;
        int result4 = solution.reverse(x4);
        System.out.println("Input: x = " + x4);
        System.out.println("Output: " + result4);
    }
    
}
