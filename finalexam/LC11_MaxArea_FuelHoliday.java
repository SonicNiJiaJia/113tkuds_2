import java.util.*;

public class LC11_MaxArea_FuelHoliday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] heights = new int[n];
        
        for (int i = 0; i < n; i++) {
            heights[i] = sc.nextInt();
        }
        
        int left = 0;
        int right = n - 1;
        int maxArea = 0;
        
        while (left < right) {
            int area = (right - left) * Math.min(heights[left], heights[right]);
            maxArea = Math.max(maxArea, area);
            
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        System.out.println(maxArea);
    }
}