// AdvancedStringRecursion.java

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdvancedStringRecursion {

    /**
     * 遞迴產生字串的所有排列組合。
     *
     * @param str   輸入字串
     * @return      所有排列組合的列表
     */
    public List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.length() == 0) {
            result.add("");
            return result;
        }
        permutationsHelper("", str, result);
        return result;
    }

    private void permutationsHelper(String currentPermutation, String remaining, List<String> result) {
        if (remaining.length() == 0) {
            result.add(currentPermutation);
            return;
        }

        for (int i = 0; i < remaining.length(); i++) {
            char charToAppend = remaining.charAt(i);
            String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
            permutationsHelper(currentPermutation + charToAppend, newRemaining, result);
        }
    }

    /**
     * 遞迴實作字串匹配演算法 (簡單的子字串匹配)。
     *
     * @param text      主字串
     * @param pattern   要匹配的模式字串
     * @return          如果找到匹配則為 true，否則為 false
     */
    public boolean stringMatchRecursive(String text, String pattern) {
        if (pattern == null || pattern.length() == 0) {
            return true; // 空模式總是匹配
        }
        if (text == null || text.length() == 0) {
            return false; // 空文本無法匹配非空模式
        }
        return stringMatchHelper(text, pattern, 0, 0);
    }

    private boolean stringMatchHelper(String text, String pattern, int textIndex, int patternIndex) {
        // 如果模式已經完全匹配
        if (patternIndex == pattern.length()) {
            return true;
        }
        // 如果文本已經搜尋完畢但模式尚未匹配
        if (textIndex == text.length()) {
            return false;
        }

        // 如果當前字符匹配，則繼續匹配下一個字符
        if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
            return stringMatchHelper(text, pattern, textIndex + 1, patternIndex + 1);
        } else {
            // 如果不匹配，則從文本的下一個字符開始重新匹配模式
            return stringMatchHelper(text, pattern, textIndex + 1, 0); // 這裡我們假設是從頭開始匹配模式
        }
    }

    /**
     * 遞迴移除字串中的重複字符。
     * 保持原始字符的首次出現順序。
     *
     * @param str 輸入字串
     * @return    移除重複字符後的字串
     */
    public String removeDuplicatesRecursive(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return removeDuplicatesHelper(str, 0, new StringBuilder(), new HashSet<>());
    }

    private String removeDuplicatesHelper(String str, int index, StringBuilder resultBuilder, Set<Character> seenChars) {
        if (index == str.length()) {
            return resultBuilder.toString();
        }

        char currentChar = str.charAt(index);
        if (!seenChars.contains(currentChar)) {
            resultBuilder.append(currentChar);
            seenChars.add(currentChar);
        }
        return removeDuplicatesHelper(str, index + 1, resultBuilder, seenChars);
    }

    /**
     * 遞迴計算字串的所有子字串組合 (子序列)。
     *
     * @param str 輸入字串
     * @return    所有子序列的列表
     */
    public List<String> generateSubsequences(String str) {
        List<String> result = new ArrayList<>();
        if (str == null) {
            return result;
        }
        subsequencesHelper("", str, result);
        return result;
    }

    private void subsequencesHelper(String currentSubsequence, String remaining, List<String> result) {
        if (remaining.length() == 0) {
            result.add(currentSubsequence);
            return;
        }

        // 選擇當前字符
        subsequencesHelper(currentSubsequence + remaining.charAt(0), remaining.substring(1), result);

        // 不選擇當前字符
        subsequencesHelper(currentSubsequence, remaining.substring(1), result);
    }

    public static void main(String[] args) {
        AdvancedStringRecursion processor = new AdvancedStringRecursion();

        System.out.println("--- 遞迴產生字串的所有排列組合 ---");
        String permString = "abc";
        List<String> permutations = processor.generatePermutations(permString);
        System.out.println("字串 \"" + permString + "\" 的所有排列組合: " + permutations); // 預期: [abc, acb, bac, bca, cab, cba]
        System.out.println("---");

        System.out.println("--- 遞迴實作字串匹配演算法 ---");
        String text = "hello world";
        String pattern1 = "world";
        String pattern2 = "xyz";
        System.out.println("字串 \"" + text + "\" 是否包含 \"" + pattern1 + "\": " + processor.stringMatchRecursive(text, pattern1)); // 預期: true
        System.out.println("字串 \"" + text + "\" 是否包含 \"" + pattern2 + "\": " + processor.stringMatchRecursive(text, pattern2)); // 預期: false
        System.out.println("---");

        System.out.println("--- 遞迴移除字串中的重複字符 ---");
        String dupString = "banana";
        String uniqueString = processor.removeDuplicatesRecursive(dupString);
        System.out.println("字串 \"" + dupString + "\" 移除重複字符後: " + uniqueString); // 預期: ban
        String dupString2 = "aabbcc";
        System.out.println("字串 \"" + dupString2 + "\" 移除重複字符後: " + processor.removeDuplicatesRecursive(dupString2)); // 預期: abc
        System.out.println("---");

        System.out.println("--- 遞迴計算字串的所有子字串組合 ---");
        String subString = "ab";
        List<String> subsequences = processor.generateSubsequences(subString);
        System.out.println("字串 \"" + subString + "\" 的所有子字串組合: " + subsequences); // 預期: [, b, a, ab] (順序可能不同)
        System.out.println("---");

        System.out.println("--- 練習 2.4：遞迴與迭代比較 ---");
        System.out.println("## 遞迴的優點:");
        System.out.println("1. **程式碼簡潔性:** 對於某些問題（如樹狀結構遍歷、階乘計算等），遞迴解法通常比迭代解法更簡潔、更接近問題的數學定義。");
        System.out.println("2. **符合自然思維:** 許多數學問題或分治法問題的本質就是遞迴的，直接用遞迴來實現更符合直覺。");
        System.out.println("3. **解決複雜問題:** 對於像遍歷所有排列組合或所有子序列這類問題，遞迴能很自然地表達其「選擇或不選擇」的決策過程。");
        System.out.println("\n## 遞迴的缺點:");
        System.out.println("1. **記憶體消耗:** 每次遞迴呼叫都會在記憶體堆疊上建立一個新的堆疊幀 (stack frame)，包含局部變數、參數和返回位址。當遞迴深度過大時，可能導致堆疊溢位 (StackOverflowError)。");
        System.out.println("2. **效能開銷:** 函數呼叫本身具有一定的開銷，包括參數傳遞、堆疊操作等。在大量遞迴呼叫的情況下，這會導致比迭代解法更高的執行時間。");
        System.out.println("3. **除錯困難:** 遞迴的執行流程有時難以追蹤，特別是當遞迴深度較大時，除錯可能會更具挑戰性。");
        System.out.println("\n## 迭代的優點:");
        System.out.println("1. **效能優勢:** 通常情況下，迭代解法比遞迴解法擁有更好的效能，因為它避免了頻繁的函數呼叫開銷和堆疊操作。");
        System.out.println("2. **記憶體效率:** 迭代通常使用固定的記憶體量，避免了遞迴可能導致的堆疊溢位問題。");
        System.out.println("3. **控制流清晰:** 迭代使用迴圈明確地控制執行流程，有時更容易理解和除錯。");
        System.out.println("\n## 迭代的缺點:");
        System.out.println("1. **程式碼複雜性:** 對於某些遞迴本質的問題，迭代解法可能需要引入額外的資料結構（如堆疊、佇列）來模擬遞迴的行為，導致程式碼更複雜。");
        System.out.println("2. **不易表達某些問題:** 某些問題（如樹或圖的深度優先遍歷）用遞迴實現更自然，迭代實現可能需要更多的技巧。");
        System.out.println("\n## 何時使用遞迴，何時使用迭代？");
        System.out.println("- **推薦遞迴:** 當問題本身的定義是遞迴的（如階乘、費波那契數列），或者問題可以自然地分解為更小的相同子問題時（如樹的遍歷、分治演算法），且遞迴深度預計不會非常大時，遞迴是個好選擇。它能讓程式碼更優雅和易讀。");
        System.out.println("- **推薦迭代:** 當效能和記憶體效率是關鍵考量時，或者當遞迴深度可能非常大導致堆疊溢位風險時，應優先考慮迭代。所有遞迴問題理論上都可以轉化為迭代問題，儘管有時需要手動管理堆疊。");
        System.out.println("---");
    }
}