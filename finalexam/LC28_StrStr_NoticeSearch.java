import java.util.Scanner;

public class LC28_StrStr_NoticeSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String haystack = sc.nextLine();
        String needle = sc.nextLine();
        
        if (needle.length() == 0) {
            System.out.println(0);
            sc.close();
            return;
        }
        
        if (needle.length() > haystack.length()) {
            System.out.println(-1);
            sc.close();
            return;
        }
        
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            boolean match = true;
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                System.out.println(i);
                sc.close();
                return;
            }
        }
        
        System.out.println(-1);
        sc.close();
    }
}