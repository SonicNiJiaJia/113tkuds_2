import java.util.*;

public class MaxHeap {
    // 使用 ArrayList 作為底層儲存結構
    // ArrayList 是動態陣列，可以自動調整大小
    private List<Integer> heap;
    
    // 建構子：初始化空的 heap
    public MaxHeap() {
        heap = new ArrayList<>();
    }
    
    // 計算父節點索引的輔助方法
    // 輸入：子節點的索引 i
    // 輸出：父節點的索引
    // 公式：(i-1)/2，整數除法會自動捨去小數
    private int parent(int i) { 
        return (i - 1) / 2; 
    }
    
    // 計算左子節點索引
    // 公式：2*i+1
    private int leftChild(int i) { 
        return 2 * i + 1; 
    }
    
    // 計算右子節點索引  
    // 公式：2*i+2
    private int rightChild(int i) { 
        return 2 * i + 2; 
    }
    
    // 向上調整：維護堆積性質
    // 當插入新元素時，可能破壞 Max Heap 性質
    // 需要將新元素與父節點比較，必要時進行交換
    private void heapifyUp(int i) {
        // 循環條件：
        // 1. i > 0：確保不是根節點（根節點沒有父節點）
        // 2. heap.get(i) > heap.get(parent(i))：子節點值大於父節點值
        while (i > 0 && heap.get(i) > heap.get(parent(i))) {
            // 交換當前節點與父節點的值
            Collections.swap(heap, i, parent(i));
            // 更新索引，繼續向上檢查
            i = parent(i);
        }
    }
    
    // 向下調整：維護堆積性質
    // 當移除根節點後，需要重新建立堆積性質
    private void heapifyDown(int i) {
        // 假設當前節點就是最大值節點
        int maxIdx = i;
        // 計算左右子節點索引
        int left = leftChild(i);
        int right = rightChild(i);
        
        // 檢查左子節點是否存在且值更大
        // left < heap.size() 確保索引在有效範圍內
        if (left < heap.size() && heap.get(left) > heap.get(maxIdx)) {
            maxIdx = left;  // 更新最大值節點索引
        }
        
        // 檢查右子節點是否存在且值更大
        if (right < heap.size() && heap.get(right) > heap.get(maxIdx)) {
            maxIdx = right;  // 更新最大值節點索引
        }
        
        // 如果最大值不是當前節點，需要進行調整
        if (maxIdx != i) {
            // 交換當前節點與最大值節點
            Collections.swap(heap, i, maxIdx);
            // 遞迴處理被影響的子樹
            heapifyDown(maxIdx);
        }
    }
    
    // 插入元素的公開方法
    public void insert(int value) {
        // 步驟1：將新元素加到陣列末尾
        heap.add(value);
        // 步驟2：調整堆積性質（從最後一個位置開始向上調整）
        heapifyUp(heap.size() - 1);
    }
    
    // 取出最大值（根節點）
    public int extractMax() {
        // 檢查堆積是否為空
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        
        // 步驟1：記錄根節點的值（最大值）
        int max = heap.get(0);
        // 步驟2：將最後一個元素移到根節點位置
        heap.set(0, heap.get(heap.size() - 1));
        // 步驟3：移除最後一個元素
        heap.remove(heap.size() - 1);
        
        // 步驟4：如果堆積不為空，從根節點開始向下調整
        if (!heap.isEmpty()) {
            heapifyDown(0);
        }
        
        return max;  // 回傳原本的最大值
    }
    
    // 查看最大值但不移除
    public int peek() {
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return heap.get(0);  // 根節點就是最大值
    }
    
    // 檢查堆積是否為空
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    // 取得堆積大小
    public int size() {
        return heap.size();
    }
    
    // 顯示堆積內容（用於除錯）
    public void display() {
        System.out.println("Heap 內容: " + heap);
    }
    
    // 主程式：示範 Max Heap 的使用
    public static void main(String[] args) {
        // 建立一個新的 Max Heap
        MaxHeap maxHeap = new MaxHeap();
        
        System.out.println("=== Max Heap 操作示範 ===");
        
        // 準備要插入的數據
        int[] values = {10, 20, 15, 30, 40};
        
        // 逐一插入元素並觀察變化
        for (int value : values) {
            maxHeap.insert(value);
            System.out.println("插入 " + value + " 後: " + maxHeap.heap);
        }
        
        // 查看當前最大值
        System.out.println("\n當前最大值: " + maxHeap.peek());
        
        // 依序取出所有元素（會得到降序排列）
        System.out.println("\n依序取出最大值:");
        while (!maxHeap.isEmpty()) {
            int max = maxHeap.extractMax();
            System.out.println("取出: " + max + ", 剩餘: " + maxHeap.heap);
        }
    }
}
