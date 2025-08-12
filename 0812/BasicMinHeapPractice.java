import java.util.ArrayList;

public class BasicMinHeapPractice {
    private ArrayList<Integer> heap;
    
    public BasicMinHeapPractice() {
        heap = new ArrayList<>();
    }
    
    public void insert(int val) {
        heap.add(val);
        heapifyUp(heap.size() - 1);
    }
    
    public int extractMin() {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        
        int min = heap.get(0);
        int lastElement = heap.get(heap.size() - 1);
        heap.set(0, lastElement);
        heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heapifyDown(0);
        }
        
        return min;
    }
    
    public int getMin() {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return heap.get(0);
    }
    
    public int size() {
        return heap.size();
    }
    
    public boolean isEmpty() {
        return heap.size() == 0;
    }
    
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) >= heap.get(parentIndex)) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    private void heapifyDown(int index) {
        while (true) {
            int smallest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
                smallest = leftChild;
            }
            
            if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
                smallest = rightChild;
            }
            
            if (smallest == index) {
                break;
            }
            
            swap(index, smallest);
            index = smallest;
        }
    }
    
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    public static void main(String[] args) {
        BasicMinHeapPractice minHeap = new BasicMinHeapPractice();
        
        minHeap.insert(15);
        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(8);
        minHeap.insert(25);
        minHeap.insert(5);
        
        while (!minHeap.isEmpty()) {
            System.out.println(minHeap.extractMin());
        }
    }
}