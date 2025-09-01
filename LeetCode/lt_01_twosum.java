import java.util.HashMap;
import java.util.Map;

public class lt_01_twosum {
    
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (numMap.containsKey(complement)) {
                return new int[]{numMap.get(complement), i};
            }
            
            numMap.put(nums[i], i);
        }
        
        throw new IllegalArgumentException("No two sum solution");
    }
    
    public static void main(String[] args) {
        lt_01_twosum solution = new lt_01_twosum();
        
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = solution.twoSum(nums1, target1);
        System.out.println("Test 1 - Input: [2,7,11,15], target: 9");
        System.out.println("Output: [" + result1[0] + "," + result1[1] + "]");
        System.out.println("Verification: " + nums1[result1[0]] + " + " + nums1[result1[1]] + " = " + (nums1[result1[0]] + nums1[result1[1]]));
        System.out.println();
        
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = solution.twoSum(nums2, target2);
        System.out.println("Test 2 - Input: [3,2,4], target: 6");
        System.out.println("Output: [" + result2[0] + "," + result2[1] + "]");
        System.out.println("Verification: " + nums2[result2[0]] + " + " + nums2[result2[1]] + " = " + (nums2[result2[0]] + nums2[result2[1]]));
        System.out.println();
        
        int[] nums3 = {3, 3};
        int target3 = 6;
        int[] result3 = solution.twoSum(nums3, target3);
        System.out.println("Test 3 - Input: [3,3], target: 6");
        System.out.println("Output: [" + result3[0] + "," + result3[1] + "]");
        System.out.println("Verification: " + nums3[result3[0]] + " + " + nums3[result3[1]] + " = " + (nums3[result3[0]] + nums3[result3[1]]));
    }
}