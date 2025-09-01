class lt_04_MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;
        
        while (left <= right) {
            int partitionX = (left + right) / 2;
            int partitionY = (m + n + 1) / 2 - partitionX;
            
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];
            
            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];
            
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2.0;
                } else {
                    return Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                right = partitionX - 1;
            } else {
                left = partitionX + 1;
            }
        }
        
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        int[] nums1_1 = {1, 3};
        int[] nums2_1 = {2};
        double result1 = solution.findMedianSortedArrays(nums1_1, nums2_1);
        System.out.println("Input: nums1 = [1,3], nums2 = [2]");
        System.out.println("Output: " + result1);
        System.out.println();
        
        int[] nums1_2 = {1, 2};
        int[] nums2_2 = {3, 4};
        double result2 = solution.findMedianSortedArrays(nums1_2, nums2_2);
        System.out.println("Input: nums1 = [1,2], nums2 = [3,4]");
        System.out.println("Output: " + result2);
        System.out.println();
        
        int[] nums1_3 = {};
        int[] nums2_3 = {1};
        double result3 = solution.findMedianSortedArrays(nums1_3, nums2_3);
        System.out.println("Input: nums1 = [], nums2 = [1]");
        System.out.println("Output: " + result3);
    }
}