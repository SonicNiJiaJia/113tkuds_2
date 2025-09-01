class lt_09_PalindromeNumber {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        int reversed = 0;
        while (x > reversed) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        
        return x == reversed || x == reversed / 10;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        int x1 = 121;
        boolean result1 = solution.isPalindrome(x1);
        System.out.println("Input: x = " + x1);
        System.out.println("Output: " + result1);
        System.out.println();
        
        int x2 = -121;
        boolean result2 = solution.isPalindrome(x2);
        System.out.println("Input: x = " + x2);
        System.out.println("Output: " + result2);
        System.out.println();
        
        int x3 = 10;
        boolean result3 = solution.isPalindrome(x3);
        System.out.println("Input: x = " + x3);
        System.out.println("Output: " + result3);
        System.out.println();
        
        int x4 = 12321;
        boolean result4 = solution.isPalindrome(x4);
        System.out.println("Input: x = " + x4);
        System.out.println("Output: " + result4);
    }
}