import java.util.Scanner;

public class M06_PalindromeClean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        
        String cleaned = cleanString(s);
        boolean isPalindrome = isPalindrome(cleaned, 0, cleaned.length() - 1);
        
        System.out.println(isPalindrome ? "Yes" : "No");
        
        sc.close();
    }
    
    private static String cleanString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
    
    private static boolean isPalindrome(String s, int left, int right) {
        if (left >= right) {
            return true;
        }
        
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        
        return isPalindrome(s, left + 1, right - 1);
    }
}