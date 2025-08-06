public class StringRecursionDemo {
    
    /**
     * 遞迴檢查回文
     * 回文：正讀反讀都一樣的字串
     * 思考方式：比較首尾字符，如果相同就檢查中間部分
     */
    public static boolean isPalindrome(String str, int start, int end) {
        // 停止條件：檢查完所有字符
        if (start >= end) {
            return true;
        }
        
        System.out.printf("比較 str[%d]='%c' 與 str[%d]='%c'\n", 
                        start, str.charAt(start), end, str.charAt(end));
        
        // 如果首尾字符不同，不是回文
        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }
        
        // 遞迴檢查中間部分
        return isPalindrome(str, start + 1, end - 1);
    }
    
    /**
     * 遞迴反轉字串
     * 思考方式：最後一個字符 + 反轉前面部分的字串
     */
    public static String reverseString(String str) {
        // 停止條件：空字串或單字符
        if (str.length() <= 1) {
            return str;
        }
        
        // 遞迴關係：最後一個字符 + 反轉前面的部分
        char lastChar = str.charAt(str.length() - 1);
        String restReversed = reverseString(str.substring(0, str.length() - 1));
        
        System.out.printf("反轉 '%s' = '%c' + 反轉('%s') = '%c' + '%s'\n", 
                        str, lastChar, str.substring(0, str.length() - 1), 
                        lastChar, restReversed);
        
        return lastChar + restReversed;
    }
    
    /**
     * 遞迴計算字串長度
     */
    public static int stringLength(String str, int index) {
        if (index >= str.length()) {
            return 0;
        }
        return 1 + stringLength(str, index + 1);
    }
    
    /**
     * 遞迴計算特定字符出現次數
     */
    public static int countChar(String str, char target, int index) {
        if (index >= str.length()) {
            return 0;
        }
        
        int currentCount = (str.charAt(index) == target) ? 1 : 0;
        return currentCount + countChar(str, target, index + 1);
    }
    
    public static void main(String[] args) {
        String word1 = "racecar";
        String word2 = "hello";
        String word3 = "programming";
        
        // 回文檢測
        System.out.println("=== 回文檢測 ===");
        System.out.printf("'%s' 是回文嗎？\n", word1);
        boolean result1 = isPalindrome(word1, 0, word1.length() - 1);
        System.out.println("結果：" + result1);
        
        System.out.printf("\n'%s' 是回文嗎？\n", word2);
        boolean result2 = isPalindrome(word2, 0, word2.length() - 1);
        System.out.println("結果：" + result2);
        
        // 字串反轉
        System.out.println("\n=== 字串反轉 ===");
        System.out.printf("反轉 '%s'：\n", word2);
        String reversed = reverseString(word2);
        System.out.println("最終結果：" + reversed);
        
        // 其他操作
        System.out.println("\n=== 其他字串操作 ===");
        System.out.printf("'%s' 的長度：%d\n", word3, stringLength(word3, 0));
        System.out.printf("'%s' 中字符 'r' 出現次數：%d\n", word3, countChar(word3, 'r', 0));
        System.out.printf("'%s' 中字符 'm' 出現次數：%d\n", word3, countChar(word3, 'm', 0));
    }
}
