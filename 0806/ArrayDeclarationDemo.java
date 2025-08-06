
public class ArrayDeclarationDemo {
    public static void main(String[] args) {
        // 方法一：先宣告變數，後分配記憶體空間
        int[] scores;           // 宣告一個整數陣列參考變數
        scores = new int[5];    // 在堆積記憶體中分配 5 個整數的空間
        
        // 方法二：宣告變數的同時分配記憶體空間
        int[] grades = new int[5];  // 所有元素自動初始化為 0
        
        // 方法三：靜態初始化，直接給定初始值
        int[] numbers = {85, 92, 77, 60, 88};  // 編譯器自動計算陣列大小
        
        // 方法四：動態初始化的另一種寫法
        int[] values = new int[]{10, 20, 30, 40, 50};
        
        System.out.println("各陣列的長度：");
        System.out.println("scores: " + scores.length);
        System.out.println("grades: " + grades.length);
        System.out.println("numbers: " + numbers.length);
        System.out.println("values: " + values.length);
    }
}
