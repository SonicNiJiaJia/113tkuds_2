
public class TwoDimensionalArrayDemo {
    public static void main(String[] args) {
        // 方法一：先宣告大小，後賦值
        int[][] matrix1 = new int[3][4];  // 3 行 4 列的矩陣
        
        // 方法二：直接初始化
        int[][] matrix2 = {
            {1, 2, 3, 4},      // 第 0 行
            {5, 6, 7, 8},      // 第 1 行
            {9, 10, 11, 12}    // 第 2 行
        };
        
        // 方法三：不規則陣列（鋸齒陣列）
        int[][] jaggedArray = {
            {1, 2},
            {3, 4, 5, 6},
            {7}
        };
        
        System.out.println("=== 矩陣資訊 ===");
        System.out.printf("matrix2 的行數：%d\n", matrix2.length);
        System.out.printf("matrix2 第一行的列數：%d\n", matrix2[0].length);
        
        // 填充 matrix1
        int value = 1;
        for (int row = 0; row < matrix1.length; row++) {
            for (int col = 0; col < matrix1[row].length; col++) {
                matrix1[row][col] = value++;
            }
        }
        
        System.out.println("\n=== 矩陣內容 ===");
        System.out.println("matrix1:");
        printMatrix(matrix1);
        
        System.out.println("\nmatrix2:");
        printMatrix(matrix2);
        
        System.out.println("\n鋸齒陣列:");
        printJaggedArray(jaggedArray);
    }
    
    /**
     * 列印規則矩陣
     */
    public static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%4d ", matrix[row][col]);
            }
            System.out.println();
        }
    }
    
    /**
     * 列印不規則陣列
     */
    public static void printJaggedArray(int[][] jaggedArray) {
        for (int row = 0; row < jaggedArray.length; row++) {
            System.out.printf("第 %d 行：", row);
            for (int col = 0; col < jaggedArray[row].length; col++) {
                System.out.printf("%d ", jaggedArray[row][col]);
            }
            System.out.printf("（長度：%d）\n", jaggedArray[row].length);
        }
    }
}
