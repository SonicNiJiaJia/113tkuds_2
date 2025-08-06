
import java.util.Arrays;

public class ArrayAccessDemo {
    public static void main(String[] args) {
        int[] scores = {85, 92, 77, 60, 88};
        
        // 讀取操作 - 隨機存取任意元素
        int firstScore = scores[0];     // 取得第一個元素：85
        int lastScore = scores[4];      // 取得最後一個元素：88
        int middleScore = scores[2];    // 取得中間元素：77
        
        System.out.println("第一個成績：" + firstScore);
        System.out.println("最後一個成績：" + lastScore);
        System.out.println("中間成績：" + middleScore);
        
        // 修改操作 - 直接更新指定位置的值
        scores[0] = 90;                 // 將第一個成績改為 90
        scores[scores.length - 1] = 95; // 將最後一個成績改為 95
        
        // 取得陣列的屬性資訊
        int arrayLength = scores.length;
        
        System.out.println("\n修改後的陣列：");
        for (int i = 0; i < arrayLength; i++) {
            System.out.printf("索引 %d：%d\n", i, scores[i]);
        }
        
        System.out.println("陣列內容：" + Arrays.toString(scores));
    }
}
