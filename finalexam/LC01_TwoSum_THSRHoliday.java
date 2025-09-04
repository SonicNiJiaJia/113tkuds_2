import java.util.*;

public class LC01_TwoSum_THSRHoliday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] seats = new int[n];
        
        for (int i = 0; i < n; i++) {
            seats[i] = sc.nextInt();
        }
        
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            int needed = target - seats[i];
            if (map.containsKey(seats[i])) {
                System.out.println(map.get(seats[i]) + " " + i);
                return;
            }
            map.put(needed, i);
        }
        
        System.out.println("-1 -1");
    }
}