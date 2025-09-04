import java.util.Scanner;

public class LC27_RemoveElement_Recycle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int val = sc.nextInt();
        
        if (n == 0) {
            System.out.println(0);
            return;
        }
        
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        
        int write = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != val) {
                nums[write++] = nums[i];
            }
        }
        
        System.out.println(write);
        for (int i = 0; i < write; i++) {
            System.out.print(nums[i]);
            if (i < write - 1) System.out.print(" ");
        }
        if (write > 0) System.out.println();
        
        sc.close();
    }
}