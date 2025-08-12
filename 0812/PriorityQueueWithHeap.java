import java.util.*;

public class PriorityQueueWithHeap {
    
    static class Task {
        String name;
        int priority;
        long timestamp;
        
        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.timestamp = System.nanoTime();
        }
    }
    
    private PriorityQueue<Task> heap;
    private Map<String, Task> taskMap;
    
    public PriorityQueueWithHeap() {
        heap = new PriorityQueue<>((a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority);
            }
            return Long.compare(a.timestamp, b.timestamp);
        });
        taskMap = new HashMap<>();
    }
    
    public void addTask(String name, int priority) {
        if (taskMap.containsKey(name)) {
            changePriority(name, priority);
            return;
        }
        
        Task task = new Task(name, priority);
        heap.offer(task);
        taskMap.put(name, task);
    }
    
    public String executeNext() {
        if (heap.isEmpty()) {
            return null;
        }
        
        Task task = heap.poll();
        taskMap.remove(task.name);
        return task.name;
    }
    
    public String peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.peek().name;
    }
    
    public void changePriority(String name, int newPriority) {
        Task oldTask = taskMap.get(name);
        if (oldTask == null) {
            return;
        }
        
        heap.remove(oldTask);
        taskMap.remove(name);
        
        Task newTask = new Task(name, newPriority);
        heap.offer(newTask);
        taskMap.put(name, newTask);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
    
    public void printQueue() {
        List<Task> tasks = new ArrayList<>(heap);
        tasks.sort((a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority);
            }
            return Long.compare(a.timestamp, b.timestamp);
        });
        
        System.out.print("當前佇列：");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.print(tasks.get(i).name + "(" + tasks.get(i).priority + ")");
            if (i < tasks.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        PriorityQueueWithHeap pq = new PriorityQueueWithHeap();
        
        System.out.println("添加任務：");
        pq.addTask("備份", 1);
        System.out.println("添加：備份(1)");
        
        pq.addTask("緊急修復", 5);
        System.out.println("添加：緊急修復(5)");
        
        pq.addTask("更新", 3);
        System.out.println("添加：更新(3)");
        
        pq.printQueue();
        
        System.out.println("\n執行任務：");
        while (!pq.isEmpty()) {
            String next = pq.executeNext();
            System.out.println("執行：" + next);
        }
        
        System.out.println("\n測試優先級修改：");
        pq.addTask("任務A", 2);
        pq.addTask("任務B", 4);
        pq.addTask("任務C", 1);
        System.out.println("添加任務A(2), 任務B(4), 任務C(1)");
        pq.printQueue();
        
        pq.changePriority("任務C", 6);
        System.out.println("修改任務C優先級為6");
        pq.printQueue();
        
        System.out.println("\n執行修改後的任務：");
        while (!pq.isEmpty()) {
            String next = pq.executeNext();
            System.out.println("執行：" + next);
        }
    }
}