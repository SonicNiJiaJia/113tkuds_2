
public class MatrixCalculator {
    
    // 矩陣加法
    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
        // 檢查矩陣維度是否相同
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("矩陣維度必須相同才能進行加法運算");
        }
        
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        
        return result;
    }
    
    // 矩陣乘法
    public static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
        // 檢查是否可以相乘：第一個矩陣的列數必須等於第二個矩陣的行數
        if (matrix1[0].length != matrix2.length) {
            throw new IllegalArgumentException(
                String.format("無法進行矩陣乘法：%dx%d 無法乘以 %dx%d", 
                matrix1.length, matrix1[0].length, matrix2.length, matrix2[0].length));
        }
        
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        int[][] result = new int[rows1][cols2];
        
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        
        return result;
    }
    
    // 矩陣轉置
    public static int[][] transposeMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("矩陣不能為空");
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        
        return result;
    }
    
    // 尋找矩陣最大值
    public static int findMax(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("矩陣不能為空");
        }
        
        int max = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        
        return max;
    }
    
    // 尋找矩陣最小值
    public static int findMin(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("矩陣不能為空");
        }
        
        int min = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        
        return min;
    }
    
    // 尋找最大值和最小值的位置
    public static class MinMaxResult {
        public final int maxValue;
        public final int minValue;
        public final int maxRow;
        public final int maxCol;
        public final int minRow;
        public final int minCol;
        
        public MinMaxResult(int maxValue, int minValue, int maxRow, int maxCol, int minRow, int minCol) {
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
            this.minRow = minRow;
            this.minCol = minCol;
        }
    }
    
    // 尋找最大值和最小值及其位置
    public static MinMaxResult findMinMaxWithPosition(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new IllegalArgumentException("矩陣不能為空");
        }
        
        int max = matrix[0][0];
        int min = matrix[0][0];
        int maxRow = 0, maxCol = 0, minRow = 0, minCol = 0;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxRow = i;
                    maxCol = j;
                }
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    minRow = i;
                    minCol = j;
                }
            }
        }
        
        return new MinMaxResult(max, min, maxRow, maxCol, minRow, minCol);
    }
    
    // 列印矩陣
    public static void printMatrix(int[][] matrix, String title) {
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        
        if (matrix == null || matrix.length == 0) {
            System.out.println("空矩陣");
            return;
        }
        
        // 找出最大數字的寬度以便對齊
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                maxWidth = Math.max(maxWidth, String.valueOf(value).length());
            }
        }
        
        for (int[] row : matrix) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                System.out.printf("%" + maxWidth + "d", row[j]);
                if (j < row.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }
    
    // 取得矩陣維度資訊
    public static String getMatrixDimension(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return "0x0";
        }
        return matrix.length + "x" + matrix[0].length;
    }
    
    // 檢查矩陣是否為方陣
    public static boolean isSquareMatrix(int[][] matrix) {
        return matrix != null && matrix.length > 0 && matrix.length == matrix[0].length;
    }
    
    // 創建單位矩陣
    public static int[][] createIdentityMatrix(int size) {
        int[][] identity = new int[size][size];
        for (int i = 0; i < size; i++) {
            identity[i][i] = 1;
        }
        return identity;
    }
    
    // 主程式測試
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("                矩陣計算器測試程式");
        System.out.println("=".repeat(60));
        
        // 測試矩陣
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        int[][] matrixB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };
        
        int[][] matrixC = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        
        int[][] matrixD = {
            {7, 8, 9},
            {10, 11, 12}
        };
        
        // 顯示原始矩陣
        printMatrix(matrixA, "矩陣 A (" + getMatrixDimension(matrixA) + ")");
        printMatrix(matrixB, "矩陣 B (" + getMatrixDimension(matrixB) + ")");
        printMatrix(matrixC, "矩陣 C (" + getMatrixDimension(matrixC) + ")");
        printMatrix(matrixD, "矩陣 D (" + getMatrixDimension(matrixD) + ")");
        
        // 測試矩陣加法
        try {
            System.out.println("【矩陣加法測試】");
            int[][] sumAB = addMatrix(matrixA, matrixB);
            printMatrix(sumAB, "A + B = ");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣加法錯誤：" + e.getMessage());
        }
        
        // 測試矩陣乘法
        try {
            System.out.println("【矩陣乘法測試】");
            int[][] productAB = multiplyMatrix(matrixA, matrixB);
            printMatrix(productAB, "A × B = ");
            
            int[][] productCD = multiplyMatrix(matrixC, matrixD);
            printMatrix(productCD, "C × D = ");
        } catch (IllegalArgumentException e) {
            System.out.println("矩陣乘法錯誤：" + e.getMessage());
        }
        
        // 測試矩陣轉置
        System.out.println("【矩陣轉置測試】");
        int[][] transposeA = transposeMatrix(matrixA);
        printMatrix(transposeA, "A 的轉置矩陣 A^T = ");
        
        int[][] transposeC = transposeMatrix(matrixC);
        printMatrix(transposeC, "C 的轉置矩陣 C^T = ");
        
        // 測試尋找最大值和最小值
        System.out.println("【最大值最小值測試】");
        
        MinMaxResult resultA = findMinMaxWithPosition(matrixA);
        System.out.printf("矩陣 A：\n");
        System.out.printf("  最大值：%d (位置：[%d, %d])\n", 
            resultA.maxValue, resultA.maxRow, resultA.maxCol);
        System.out.printf("  最小值：%d (位置：[%d, %d])\n", 
            resultA.minValue, resultA.minRow, resultA.minCol);
        
        MinMaxResult resultB = findMinMaxWithPosition(matrixB);
        System.out.printf("矩陣 B：\n");
        System.out.printf("  最大值：%d (位置：[%d, %d])\n", 
            resultB.maxValue, resultB.maxRow, resultB.maxCol);
        System.out.printf("  最小值：%d (位置：[%d, %d])\n", 
            resultB.minValue, resultB.minRow, resultB.minCol);
        
        // 測試其他功能
        System.out.println("\n【其他功能測試】");
        System.out.println("矩陣 A 是否為方陣：" + isSquareMatrix(matrixA));
        System.out.println("矩陣 C 是否為方陣：" + isSquareMatrix(matrixC));
        
        // 創建並顯示單位矩陣
        int[][] identity = createIdentityMatrix(4);
        printMatrix(identity, "4×4 單位矩陣");
        
        // 錯誤處理測試
        System.out.println("【錯誤處理測試】");
        try {
            addMatrix(matrixA, matrixC);
        } catch (IllegalArgumentException e) {
            System.out.println("預期錯誤：" + e.getMessage());
        }
        
        try {
            multiplyMatrix(matrixA, matrixC);
        } catch (IllegalArgumentException e) {
            System.out.println("預期錯誤：" + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                測試完成");
        System.out.println("=".repeat(60));
    }
}