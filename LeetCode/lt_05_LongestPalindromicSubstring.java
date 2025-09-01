class lt_05_LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0;
        int maxLen = 1;
        
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            
            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLen);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        String s1 = "babad";
        String result1 = solution.longestPalindrome(s1);
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: \"" + result1 + "\"");
        System.out.println();
        
        String s2 = "cbbd";
        String result2 = solution.longestPalindrome(s2);
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("Output: \"" + result2 + "\"");
        System.out.println();
        
        String s3 = "a";
        String result3 = solution.longestPalindrome(s3);
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("Output: \"" + result3 + "\"");
    }
}