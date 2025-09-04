import java.util.*;

public class LC04_Median_QuakeFeeds {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        double[] nums1 = new double[n];
        double[] nums2 = new double[m];
        
        for (int i = 0; i < n; i++) {
            nums1[i] = sc.nextDouble();
        }
        for (int i = 0; i < m; i++) {
            nums2[i] = sc.nextDouble();
        }
        
        if (n > m) {
            double[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tempLen = n;
            n = m;
            m = tempLen;
        }
        
        int left = 0;
        int right = n;
        int halfLen = (n + m + 1) / 2;
        
        while (left <= right) {
            int i = (left + right) / 2;
            int j = halfLen - i;
            
            double maxLeftX = (i == 0) ? Double.NEGATIVE_INFINITY : nums1[i - 1];
            double minRightX = (i == n) ? Double.POSITIVE_INFINITY : nums1[i];
            
            double maxLeftY = (j == 0) ? Double.NEGATIVE_INFINITY : nums2[j - 1];
            double minRightY = (j == m) ? Double.POSITIVE_INFINITY : nums2[j];
            
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ((n + m) % 2 == 1) {
                    System.out.printf("%.1f\n", Math.max(maxLeftX, maxLeftY));
                } else {
                    System.out.printf("%.1f\n", (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0);
                }
                return;
            } else if (maxLeftX > minRightY) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }
    }
}