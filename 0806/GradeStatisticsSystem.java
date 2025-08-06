
import java.util.Arrays;
import java.util.Scanner;

public class GradeStatisticsSystem {
    private double[] grades;
    private int[] gradeCount = new int[5]; // A, B, C, D, F 的計數
    private double average;
    private double highest;
    private double lowest;
    private int aboveAverageCount;
    
    public GradeStatisticsSystem(double[] grades) {
        this.grades = grades.clone(); // 防止外部修改
        calculateStatistics();
    }
    
    // 計算所有統計資料
    private void calculateStatistics() {
        calculateBasicStats();
        countGradeDistribution();
        countAboveAverage();
    }
    
    // 計算基本統計：平均值、最高分、最低分
    private void calculateBasicStats() {
        if (grades.length == 0) {
            throw new IllegalArgumentException("成績陣列不能為空");
        }
        
        double sum = 0;
        highest = grades[0];
        lowest = grades[0];
        
        for (double grade : grades) {
            sum += grade;
            if (grade > highest) {
                highest = grade;
            }
            if (grade < lowest) {
                lowest = grade;
            }
        }
        
        average = sum / grades.length;
    }
    
    // 統計各等第人數
    private void countGradeDistribution() {
        Arrays.fill(gradeCount, 0); // 重置計數
        
        for (double grade : grades) {
            if (grade >= 90) {
                gradeCount[0]++; // A
            } else if (grade >= 80) {
                gradeCount[1]++; // B
            } else if (grade >= 70) {
                gradeCount[2]++; // C
            } else if (grade >= 60) {
                gradeCount[3]++; // D
            } else {
                gradeCount[4]++; // F
            }
        }
    }
    
    // 計算高於平均分的學生人數
    private void countAboveAverage() {
        aboveAverageCount = 0;
        for (double grade : grades) {
            if (grade > average) {
                aboveAverageCount++;
            }
        }
    }
    
    // 取得平均值
    public double getAverage() {
        return average;
    }
    
    // 取得最高分
    public double getHighest() {
        return highest;
    }
    
    // 取得最低分
    public double getLowest() {
        return lowest;
    }
    
    // 取得各等第人數
    public int[] getGradeDistribution() {
        return gradeCount.clone();
    }
    
    // 取得高於平均分的學生人數
    public int getAboveAverageCount() {
        return aboveAverageCount;
    }
    
    // 取得等第字串
    private String getGradeLetter(int index) {
        String[] letters = {"A", "B", "C", "D", "F"};
        return letters[index];
    }
    
    // 列印完整成績報表
    public void printReport() {
        System.out.println("=".repeat(50));
        System.out.println("            成績統計報表");
        System.out.println("=".repeat(50));
        
        // 基本統計
        System.out.println("\n【基本統計資料】");
        System.out.printf("學生總人數：%d 人\n", grades.length);
        System.out.printf("平均分數：%.2f 分\n", average);
        System.out.printf("最高分數：%.2f 分\n", highest);
        System.out.printf("最低分數：%.2f 分\n", lowest);
        System.out.printf("分數範圍：%.2f 分\n", highest - lowest);
        
        // 等第分布
        System.out.println("\n【等第分布統計】");
        String[] gradeNames = {"A (90-100)", "B (80-89)", "C (70-79)", "D (60-69)", "F (0-59)"};
        for (int i = 0; i < gradeCount.length; i++) {
            double percentage = (double) gradeCount[i] / grades.length * 100;
            System.out.printf("%-12s：%2d 人 (%.1f%%)\n", 
                gradeNames[i], gradeCount[i], percentage);
        }
        
        // 進階分析
        System.out.println("\n【進階分析】");
        System.out.printf("高於平均分學生：%d 人 (%.1f%%)\n", 
            aboveAverageCount, (double) aboveAverageCount / grades.length * 100);
        System.out.printf("低於平均分學生：%d 人 (%.1f%%)\n", 
            grades.length - aboveAverageCount, 
            (double) (grades.length - aboveAverageCount) / grades.length * 100);
        
        // 詳細成績列表
        System.out.println("\n【詳細成績列表】");
        System.out.println("學號\t成績\t等第\t與平均差距");
        System.out.println("-".repeat(40));
        
        for (int i = 0; i < grades.length; i++) {
            String gradeLetter = getGradeLetterForScore(grades[i]);
            double difference = grades[i] - average;
            String diffStr = String.format("%+.1f", difference);
            
            System.out.printf("%02d\t%.1f\t%s\t%s\n", 
                i + 1, grades[i], gradeLetter, diffStr);
        }
        
        System.out.println("=".repeat(50));
    }
    
    // 根據分數取得等第
    private String getGradeLetterForScore(double score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
    
    // 主程式測試
    public static void main(String[] args) {
        // 測試資料
        double[] testGrades = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};
        
        System.out.println("成績統計系統測試");
        System.out.println("測試資料：" + Arrays.toString(testGrades));
        System.out.println();
        
        // 建立統計系統並執行
        GradeStatisticsSystem system = new GradeStatisticsSystem(testGrades);
        system.printReport();
        
        // 互動式輸入測試
        System.out.println("\n是否要輸入新的成績資料？(y/n)");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();
        
        if (choice.equals("y") || choice.equals("yes")) {
            inputCustomGrades(scanner);
        }
        
        scanner.close();
    }
    
    // 互動式輸入成績
    private static void inputCustomGrades(Scanner scanner) {
        System.out.print("請輸入學生人數：");
        int studentCount = scanner.nextInt();
        
        double[] customGrades = new double[studentCount];
        System.out.println("請依序輸入每位學生的成績：");
        
        for (int i = 0; i < studentCount; i++) {
            System.out.printf("第 %d 位學生成績：", i + 1);
            customGrades[i] = scanner.nextDouble();
            
            // 簡單驗證
            if (customGrades[i] < 0 || customGrades[i] > 100) {
                System.out.println("警告：成績應在 0-100 之間");
            }
        }
        
        // 產生自訂資料的報表
        System.out.println("\n自訂資料統計結果：");
        GradeStatisticsSystem customSystem = new GradeStatisticsSystem(customGrades);
        customSystem.printReport();
    }
}