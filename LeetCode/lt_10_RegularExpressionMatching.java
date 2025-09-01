class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        dp[0][0] = true;
        
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);
                
                if (pc == '*') {
                    char prevChar = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2];
                    if (prevChar == '.' || prevChar == sc) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else if (pc == '.' || pc == sc) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        
        return dp[m][n];
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        String s1 = "aa", p1 = "a";
        boolean result1 = solution.isMatch(s1, p1);
        System.out.println("Input: s = \"" + s1 + "\", p = \"" + p1 + "\"");
        System.out.println("Output: " + result1);
        System.out.println();
        
        String s2 = "aa", p2 = "a*";
        boolean result2 = solution.isMatch(s2, p2);
        System.out.println("Input: s = \"" + s2 + "\", p = \"" + p2 + "\"");
        System.out.println("Output: " + result2);
        System.out.println();
        
        String s3 = "ab", p3 = ".*";
        boolean result3 = solution.isMatch(s3, p3);
        System.out.println("Input: s = \"" + s3 + "\", p = \"" + p3 + "\"");
        System.out.println("Output: " + result3);
        System.out.println();
        
        String s4 = "mississippi", p4 = "mis*is*p*.";
        boolean result4 = solution.isMatch(s4, p4);
        System.out.println("Input: s = \"" + s4 + "\", p = \"" + p4 + "\"");
        System.out.println("Output: " + result4);
    }
}