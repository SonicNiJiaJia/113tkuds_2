import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c) && map.get(c) >= left) {
                left = map.get(c) + 1;
            }
            map.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        System.out.println(maxLen);
    }
}