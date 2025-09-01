class lt_08_StringtoInteger {
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int i = 0;
        int n = s.length();
        
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }
        
        if (i == n) return 0;
        
        int sign = 1;
        if (s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (s.charAt(i) == '+') {
            i++;
        }
        
        int result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            i++;
        }
        
        return result * sign;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        String s1 = "42";
        int result1 = solution.myAtoi(s1);
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: " + result1);
        System.out.println();
        
        String s2 = "   -042";
        int result2 = solution.myAtoi(s2);
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("Output: " + result2);
        System.out.println();
        
        String s3 = "1337c0d3";
        int result3 = solution.myAtoi(s3);
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("Output: " + result3);
        System.out.println();
        
        String s4 = "0-1";
        int result4 = solution.myAtoi(s4);
        System.out.println("Input: \"" + s4 + "\"");
        System.out.println("Output: " + result4);
        System.out.println();
        
        String s5 = "words and 987";
        int result5 = solution.myAtoi(s5);
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("Output: " + result5);
    }
}