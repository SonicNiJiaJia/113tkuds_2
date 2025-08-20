import java.util.Scanner;

public class M01_BuildHeap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String type = sc.next();
        int n = sc.nextInt();
        int[] heap = new int[n];
        
        for (int i = 0; i < n; i++) {
            heap[i] = sc.nextInt();
        }
        
        buildHeap(heap, type.equals("max"));
        
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(heap[i]);
        }
        System.out.println();
        
        sc.close();
    }
    
    private static void buildHeap(int[] heap, boolean isMaxHeap) {
        int n = heap.length;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(heap, i, n, isMaxHeap);
        }
    }
    
    private static void heapifyDown(int[] heap, int index, int size, boolean isMaxHeap) {
        int target = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        
        if (left < size && shouldSwap(heap[left], heap[target], isMaxHeap)) {
            target = left;
        }
        
        if (right < size && shouldSwap(heap[right], heap[target], isMaxHeap)) {
            target = right;
        }
        
        if (target != index) {
            swap(heap, index, target);
            heapifyDown(heap, target, size, isMaxHeap);
        }
    }
    
    private static boolean shouldSwap(int child, int parent, boolean isMaxHeap) {
        return isMaxHeap ? child > parent : child < parent;
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：自底向上建堆，每個節點的heapify操作次數與其高度成正比
 * 總操作次數為Σ(h*節點數) = O(n)，優於逐一插入的O(n log n)
 */
