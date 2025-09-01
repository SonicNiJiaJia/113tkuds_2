import java.util.ArrayList;
import java.util.List;
public class lt_06_ZigzagConversion {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        
        int currentRow = 0;
        boolean goingDown = false;
        
        for (char c : s.toCharArray()) {
            rows.get(currentRow).append(c);
            
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            currentRow += goingDown ? 1 : -1;
        }
        
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        String s1 = "PAYPALISHIRING";
        int numRows1 = 3;
        String result1 = solution.convert(s1, numRows1);
        System.out.println("Input: s = \"" + s1 + "\", numRows = " + numRows1);
        System.out.println("Output: \"" + result1 + "\"");
        System.out.println();
        
        String s2 = "PAYPALISHIRING";
        int numRows2 = 4;
        String result2 = solution.convert(s2, numRows2);
        System.out.println("Input: s = \"" + s2 + "\", numRows = " + numRows2);
        System.out.println("Output: \"" + result2 + "\"");
        System.out.println();
        
        String s3 = "A";
        int numRows3 = 1;
        String result3 = solution.convert(s3, numRows3);
        System.out.println("Input: s = \"" + s3 + "\", numRows = " + numRows3);
        System.out.println("Output: \"" + result3 + "\"");
    }
}
