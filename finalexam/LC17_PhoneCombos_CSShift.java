import java.util.*;

public class LC17_PhoneCombos_CSShift {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String digits = sc.nextLine();
        
        if (digits.length() == 0) {
            return;
        }
        
        String[] mapping = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> result = new ArrayList<>();
        
        backtrack(digits, 0, new StringBuilder(), mapping, result);
        
        for (String combo : result) {
            System.out.println(combo);
        }
    }
    
    private static void backtrack(String digits, int index, StringBuilder path, String[] mapping, List<String> result) {
        if (index == digits.length()) {
            result.add(path.toString());
            return;
        }
        
        int digit = digits.charAt(index) - '2';
        String letters = mapping[digit];
        
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            backtrack(digits, index + 1, path, mapping, result);
            path.deleteCharAt(path.length() - 1);
        }
    }
}