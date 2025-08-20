import java.util.*;

public class M11_HeapSortWithTie {
    static class Student {
        int score;
        int index;
        
        Student(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Student[] students = new Student[n];
        
        for (int i = 0; i < n; i++) {
            int score = sc.nextInt();
            students[i] = new Student(score, i);
        }
        
        heapSort(students);
        
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(students[i].score);
        }
        System.out.println();
        
        sc.close();
    }
    
    private static void heapSort(Student[] arr) {
        int n = arr.length;
        
        buildMaxHeap(arr, n);
        
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, 0, i);
        }
    }
    
    private static void buildMaxHeap(Student[] arr, int n) {
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, i, n);
        }
    }
    
    private static void heapifyDown(Student[] arr, int index, int size) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        
        if (left < size && compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }
        
        if (right < size && compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }
        
        if (largest != index) {
            swap(arr, index, largest);
            heapifyDown(arr, largest, size);
        }
    }
    
    private static int compare(Student a, Student b) {
        if (a.score != b.score) {
            return Integer.compare(a.score, b.score);
        }
        return Integer.compare(b.index, a.index);
    }
    
    private static void swap(Student[] arr, int i, int j) {
        Student temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/*
 * Time Complexity: O(n log n)
 * 說明：buildMaxHeap需要O(n)時間，之後進行n-1次extractMax操作
 * 每次extractMax包含一次swap和一次heapify，heapify需要O(log n)時間
 */