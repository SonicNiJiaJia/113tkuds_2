public class ValidMaxHeapChecker {
    
    public static boolean isValidMaxHeap(int[] arr) {
        if (arr.length <= 1) {
            return true;
        }
        
        int lastNonLeafIndex = (arr.length - 2) / 2;
        
        for (int i = 0; i <= lastNonLeafIndex; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            
            if (leftChild < arr.length && arr[i] < arr[leftChild]) {
                System.out.println("違反規則的節點位置：索引" + leftChild + "的" + arr[leftChild] + "大於父節點索引" + i + "的" + arr[i]);
                return false;
            }
            
            if (rightChild < arr.length && arr[i] < arr[rightChild]) {
                System.out.println("違反規則的節點位置：索引" + rightChild + "的" + arr[rightChild] + "大於父節點索引" + i + "的" + arr[i]);
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {100, 90, 80, 70, 60, 75, 65},
            {100, 90, 80, 95, 60, 75, 65},
            {50},
            {}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.print("測試案例" + (i + 1) + ": ");
            for (int j = 0; j < testCases[i].length; j++) {
                System.out.print(testCases[i][j]);
                if (j < testCases[i].length - 1) {
                    System.out.print(", ");
                }
            }
            if (testCases[i].length == 0) {
                System.out.print("[]");
            }
            
            System.out.println(" → " + isValidMaxHeap(testCases[i]));
            System.out.println();
        }
    }
}