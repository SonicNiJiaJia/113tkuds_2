import java.util.Scanner;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long b = sc.nextLong();
        
        long gcd = gcd(a, b);
        long lcm = a / gcd * b;
        
        System.out.println("GCD: " + gcd);
        System.out.println("LCM: " + lcm);
        
        sc.close();
    }
    
    private static long gcd(long x, long y) {
        if (y == 0) {
            return x;
        }
        return gcd(y, x % y);
    }
}

/*
 * Time Complexity: O(log(min(a, b)))
 * 說明：歐幾里得演算法每次遞迴都會使較大數變為原來較小數，較小數變為餘數
 * 餘數必小於原較小數的一半，因此遞迴深度為對數級別
 */