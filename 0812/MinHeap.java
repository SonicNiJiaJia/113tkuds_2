import java.util.*;

public class MinHeap {
    private List<Integer> heap;
    
    public MinHeap() {
        heap = new ArrayList<>();
    }
    
    private int parent(int i) { 
        return (i - 1) / 2; 
    }
    
    private int leftChild(int i) { 
        return 2 * i + 1; 
    }
    
    private int rightChild(int i) { 
        return 2 * i + 2; 
    }
    
    // 向上調整：與 Max Heap 的差別僅在比較方向
    private void heapifyUp(int i) {
        while (i > 0 && heap.get(i) < heap.get(parent(i))) {
            Collections.swap(heap, i, parent(i));
            i = parent(i);
        }
    }
    
    // 向下調整：尋找最小值而非最大值
    private void heapifyDown(int i) {
        int minIdx = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < heap.size() && heap.get(left) < heap.get(minIdx)) {
            minIdx = left;
        }
        
        if (right < heap.size() && heap.get(right) < heap.get(minIdx)) {
            minIdx = right;
        }
        
        if (minIdx != i) {
            Collections.swap(heap, i, minIdx);
            heapifyDown(minIdx);
        }
    }
    
    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }
    
    public int extractMin() {
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        
        int min = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heapifyDown(0);
        }
        
        return min;
    }
    
    public int peek() {
        if (heap.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return heap.get(0);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public void display() {
        System.out.println("Heap 內容: " + heap);
    }
    
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        
        System.out.println("=== Min Heap 操作示範 ===");
        
        // 使用相同資料觀察與 Max Heap 的差異
        int[] values = {40, 30, 15, 20, 10};
        for (int value : values) {
            minHeap.insert(value);
            System.out.println("插入 " + value + " 後: " + minHeap.heap);
        }
        
        System.out.println("\n當前最小值: " + minHeap.peek());
        
        // 依序取出所有元素（會得到升序排列）
        System.out.println("\n依序取出最小值:");
        while (!minHeap.isEmpty()) {
            System.out.println("取出: " + minHeap.extractMin() + 
                             ", 剩餘: " + minHeap.heap);
        }
    }
}
