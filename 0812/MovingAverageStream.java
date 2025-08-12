import java.util.*;

public class MovingAverageStream {
    
    static class MovingAverage {
        private int size;
        private Queue<Integer> queue;
        private double sum;
        
        private PriorityQueue<Integer> maxHeap;
        private PriorityQueue<Integer> minHeap;
        private Map<Integer, Integer> toRemove;
        
        private TreeMap<Integer, Integer> sortedWindow;
        
        public MovingAverage(int size) {
            this.size = size;
            this.queue = new LinkedList<>();
            this.sum = 0;
            
            this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
            this.minHeap = new PriorityQueue<>();
            this.toRemove = new HashMap<>();
            
            this.sortedWindow = new TreeMap<>();
        }
        
        public double next(int val) {
            if (queue.size() == size) {
                int removed = queue.poll();
                sum -= removed;
                
                removeFromHeaps(removed);
                removeFromSortedWindow(removed);
            }
            
            queue.offer(val);
            sum += val;
            
            addToHeaps(val);
            addToSortedWindow(val);
            
            return sum / queue.size();
        }
        
        private void addToHeaps(int val) {
            if (maxHeap.isEmpty() || val <= maxHeap.peek()) {
                maxHeap.offer(val);
            } else {
                minHeap.offer(val);
            }
            rebalanceHeaps();
        }
        
        private void removeFromHeaps(int val) {
            toRemove.put(val, toRemove.getOrDefault(val, 0) + 1);
            
            if (!maxHeap.isEmpty() && val <= maxHeap.peek()) {
                if (maxHeap.peek().equals(val)) {
                    maxHeap.poll();
                    toRemove.put(val, toRemove.get(val) - 1);
                    if (toRemove.get(val) == 0) {
                        toRemove.remove(val);
                    }
                }
            } else {
                if (!minHeap.isEmpty() && minHeap.peek().equals(val)) {
                    minHeap.poll();
                    toRemove.put(val, toRemove.get(val) - 1);
                    if (toRemove.get(val) == 0) {
                        toRemove.remove(val);
                    }
                }
            }
            
            cleanHeaps();
            rebalanceHeaps();
        }
        
        private void cleanHeaps() {
            while (!maxHeap.isEmpty() && toRemove.containsKey(maxHeap.peek())) {
                int val = maxHeap.poll();
                toRemove.put(val, toRemove.get(val) - 1);
                if (toRemove.get(val) == 0) {
                    toRemove.remove(val);
                }
            }
            
            while (!minHeap.isEmpty() && toRemove.containsKey(minHeap.peek())) {
                int val = minHeap.poll();
                toRemove.put(val, toRemove.get(val) - 1);
                if (toRemove.get(val) == 0) {
                    toRemove.remove(val);
                }
            }
        }
        
        private void rebalanceHeaps() {
            cleanHeaps();
            
            int maxSize = (queue.size() + 1) / 2;
            int minSize = queue.size() / 2;
            
            while (maxHeap.size() > maxSize && !maxHeap.isEmpty()) {
                minHeap.offer(maxHeap.poll());
            }
            
            while (minHeap.size() > minSize && !minHeap.isEmpty()) {
                maxHeap.offer(minHeap.poll());
            }
        }
        
        private void addToSortedWindow(int val) {
            sortedWindow.put(val, sortedWindow.getOrDefault(val, 0) + 1);
        }
        
        private void removeFromSortedWindow(int val) {
            int count = sortedWindow.get(val);
            if (count == 1) {
                sortedWindow.remove(val);
            } else {
                sortedWindow.put(val, count - 1);
            }
        }
        
        public double getMedian() {
            if (queue.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            
            cleanHeaps();
            
            if (queue.size() % 2 == 1) {
                return maxHeap.peek();
            } else {
                if (!maxHeap.isEmpty() && !minHeap.isEmpty()) {
                    return ((double) maxHeap.peek() + minHeap.peek()) / 2.0;
                }
                return maxHeap.isEmpty() ? minHeap.peek() : maxHeap.peek();
            }
        }
        
        public int getMin() {
            if (queue.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            return sortedWindow.firstKey();
        }
        
        public int getMax() {
            if (queue.isEmpty()) {
                throw new RuntimeException("視窗為空");
            }
            return sortedWindow.lastKey();
        }
        
        public List<Integer> getCurrentWindow() {
            return new ArrayList<>(queue);
        }
        
        public void printStatus() {
            System.out.println("當前視窗：" + getCurrentWindow());
            System.out.println("平均數：" + String.format("%.2f", sum / queue.size()));
            if (!queue.isEmpty()) {
                System.out.println("中位數：" + getMedian());
                System.out.println("最小值：" + getMin());
                System.out.println("最大值：" + getMax());
            }
            System.out.println();
        }
    }
    
    public static void testBasicFunctionality() {
        System.out.println("=== 基本功能測試 ===");
        
        MovingAverage ma = new MovingAverage(3);
        
        System.out.println("ma.next(1) = " + ma.next(1));
        ma.printStatus();
        
        System.out.println("ma.next(10) = " + String.format("%.2f", ma.next(10)));
        ma.printStatus();
        
        System.out.println("ma.next(3) = " + String.format("%.2f", ma.next(3)));
        ma.printStatus();
        
        System.out.println("ma.next(5) = " + String.format("%.2f", ma.next(5)));
        ma.printStatus();
    }
    
    public static void testEdgeCases() {
        System.out.println("=== 邊界情況測試 ===");
        
        System.out.println("測試視窗大小為1：");
        MovingAverage ma1 = new MovingAverage(1);
        System.out.println("next(5) = " + ma1.next(5));
        System.out.println("中位數：" + ma1.getMedian());
        System.out.println("最小值：" + ma1.getMin());
        System.out.println("最大值：" + ma1.getMax());
        System.out.println();
        
        System.out.println("測試重複值：");
        MovingAverage ma2 = new MovingAverage(4);
        ma2.next(5);
        ma2.next(5);
        ma2.next(5);
        ma2.next(5);
        ma2.printStatus();
        
        System.out.println("測試負數：");
        MovingAverage ma3 = new MovingAverage(3);
        ma3.next(-1);
        ma3.next(-5);
        ma3.next(3);
        ma3.printStatus();
    }
    
    public static void testPerformance() {
        System.out.println("=== 效能測試 ===");
        
        int windowSize = 1000;
        int operations = 10000;
        MovingAverage ma = new MovingAverage(windowSize);
        
        Random rand = new Random(42);
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < operations; i++) {
            int val = rand.nextInt(10000) - 5000;
            ma.next(val);
            
            if (i % 1000 == 999) {
                ma.getMedian();
                ma.getMin();
                ma.getMax();
            }
        }
        
        long endTime = System.nanoTime();
        
        System.out.println("視窗大小：" + windowSize);
        System.out.println("操作次數：" + operations);
        System.out.println("總時間：" + (endTime - startTime) / 1000000 + " ms");
        System.out.println("平均每次操作：" + String.format("%.3f", (endTime - startTime) / 1000000.0 / operations) + " ms");
        System.out.println();
    }
    
    public static void testMedianAccuracy() {
        System.out.println("=== 中位數準確性測試 ===");
        
        MovingAverage ma = new MovingAverage(5);
        int[] values = {1, 5, 2, 8, 3, 7, 4, 6, 9};
        
        for (int val : values) {
            double avg = ma.next(val);
            List<Integer> window = ma.getCurrentWindow();
            
            List<Integer> sorted = new ArrayList<>(window);
            sorted.sort(Integer::compareTo);
            
            double expectedMedian;
            if (sorted.size() % 2 == 1) {
                expectedMedian = sorted.get(sorted.size() / 2);
            } else {
                expectedMedian = ((double) sorted.get(sorted.size() / 2 - 1) + sorted.get(sorted.size() / 2)) / 2.0;
            }
            
            double actualMedian = ma.getMedian();
            
            System.out.printf("加入 %d, 視窗 %s, 排序 %s\n", val, window, sorted);
            System.out.printf("期望中位數: %.1f, 實際中位數: %.1f, 匹配: %s\n", 
                expectedMedian, actualMedian, Math.abs(expectedMedian - actualMedian) < 0.001 ? "✓" : "✗");
            System.out.println();
        }
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("視窗大小為 K 的情況下：");
        System.out.println();
        System.out.println("next() 操作：");
        System.out.println("  時間複雜度：O(log K)");
        System.out.println("  - Queue操作：O(1)");
        System.out.println("  - Heap操作：O(log K)");
        System.out.println("  - TreeMap操作：O(log K)");
        System.out.println();
        System.out.println("getMedian() 操作：");
        System.out.println("  時間複雜度：O(log K)");
        System.out.println("  - 需要清理和平衡heap");
        System.out.println();
        System.out.println("getMin()/getMax() 操作：");
        System.out.println("  時間複雜度：O(1)");
        System.out.println("  - TreeMap的firstKey/lastKey");
        System.out.println();
        System.out.println("空間複雜度：O(K)");
        System.out.println("  - Queue：O(K)");
        System.out.println("  - 兩個Heap：O(K)");
        System.out.println("  - TreeMap：O(K)");
        System.out.println("  - 延遲刪除Map：O(K)");
        System.out.println();
        System.out.println("設計特點：");
        System.out.println("- 支援所有操作的高效實現");
        System.out.println("- 使用延遲刪除避免heap中的線性搜尋");
        System.out.println("- TreeMap提供O(1)的極值查詢");
        System.out.println("- 適合高頻率的串流資料處理");
    }
    
    public static void main(String[] args) {
        testBasicFunctionality();
        testEdgeCases();
        testMedianAccuracy();
        testPerformance();
        complexityAnalysis();
    }
}