import java.util.*;

public class HeapBuilder {
    private List<Integer> heap;
    
    public HeapBuilder(int[] arr) {
        heap = new ArrayList<>();
        // 將陣列元素複製到 heap 中
        for (int value : arr) {
            heap.add(value);
        }
        buildHeap();
    }
    
    private int leftChild(int i) { 
        return 2 * i + 1; 
    }
    
    private int rightChild(int i) { 
        return 2 * i + 2; 
    }
    
    private void heapifyDown(int i) {
        int maxIdx = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < heap.size() && heap.get(left) > heap.get(maxIdx)) {
            maxIdx = left;
        }
        
        if (right < heap.size() && heap.get(right) > heap.get(maxIdx)) {
            maxIdx = right;
        }
        
        if (maxIdx != i) {
            Collections.swap(heap, i, maxIdx);
            heapifyDown(maxIdx);
        }
    }
    
    // Bottom-up 建構法：從最後一個非葉子節點開始向下調整
    private void buildHeap() {
        // 最後一個非葉子節點的索引
        int startIdx = (heap.size() / 2) - 1;
        
        System.out.println("開始建立 Heap，從索引 " + startIdx + " 開始調整");
        System.out.println("初始陣列: " + heap);
        
        for (int i = startIdx; i >= 0; i--) {
            System.out.println("\n調整索引 " + i + " (值: " + heap.get(i) + ")");
            heapifyDown(i);
            System.out.println("調整後: " + heap);
        }
    }
    
    // 驗證是否為有效的 Max Heap
    public boolean isValidHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = leftChild(i);
            int right = rightChild(i);
            
            if (left < heap.size() && heap.get(i) < heap.get(left)) {
                return false;
            }
            if (right < heap.size() && heap.get(i) < heap.get(right)) {
                return false;
            }
        }
        return true;
    }
    
    public void display() {
        System.out.println("最終 Heap: " + heap);
        System.out.println("是否為有效 Max Heap: " + isValidHeap());
    }
    
    public static void main(String[] args) {
        System.out.println("=== 從陣列建立 Heap 示範 ===");
        
        int[] arr = {4, 10, 3, 5, 1, 15, 20, 17};
        System.out.println("原始陣列: " + Arrays.toString(arr));
        
        HeapBuilder heapBuilder = new HeapBuilder(arr);
        heapBuilder.display();
        
        // 展示樹狀結構
        System.out.println("\n樹狀結構表示:");
        System.out.println("      " + heapBuilder.heap.get(0));
        System.out.println("    /   \\");
        System.out.println("   " + heapBuilder.heap.get(1) + "     " + heapBuilder.heap.get(2));
        System.out.println("  / \\   / \\");
        System.out.println(" " + heapBuilder.heap.get(3) + "   " + heapBuilder.heap.get(4) + " " + heapBuilder.heap.get(5) + "   " + heapBuilder.heap.get(6));
        if (heapBuilder.heap.size() > 7) {
            System.out.println("/");
            System.out.println(heapBuilder.heap.get(7));
        }
    }
}
