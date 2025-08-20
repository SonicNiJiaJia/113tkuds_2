import java.util.Scanner;

public class M04_TieredTaxSimple {
    private static final int[] THRESHOLDS = {0, 120000, 500000, 1000000, Integer.MAX_VALUE};
    private static final double[] RATES = {0.05, 0.12, 0.20, 0.30};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        long totalTax = 0;
        
        for (int i = 0; i < n; i++) {
            int income = sc.nextInt();
            int tax = calculateTax(income);
            System.out.println("Tax: " + tax);
            totalTax += tax;
        }
        
        int average = (int) Math.round((double) totalTax / n);
        System.out.println("Average: " + average);
        
        sc.close();
    }
    
    private static int calculateTax(int income) {
        double tax = 0;
        
        for (int i = 0; i < RATES.length; i++) {
            int lowerBound = THRESHOLDS[i];
            int upperBound = THRESHOLDS[i + 1];
            
            if (income <= lowerBound) {
                break;
            }
            
            int taxableAmount = Math.min(income, upperBound) - lowerBound;
            tax += taxableAmount * RATES[i];
        }
        
        return (int) Math.round(tax);
    }
}

/*
 * Time Complexity: O(n)
 * 說明：處理n筆收入，每筆收入的稅額計算為固定級距數量(4級)的O(1)操作
 * 總時間複雜度為O(n)，空間複雜度為O(1)
 */
