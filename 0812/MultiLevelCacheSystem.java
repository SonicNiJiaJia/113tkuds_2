import java.util.*;

public class MultiLevelCacheSystem {
    
    static class CacheEntry {
        String key;
        String value;
        long accessCount;
        long lastAccessTime;
        long insertTime;
        int currentLevel;
        double priority;
        
        CacheEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.accessCount = 1;
            this.lastAccessTime = System.nanoTime();
            this.insertTime = System.nanoTime();
            this.currentLevel = 1;
            this.priority = 0.0;
        }
        
        void updateAccess() {
            this.accessCount++;
            this.lastAccessTime = System.nanoTime();
        }
        
        void calculatePriority(int level, long currentTime) {
            double frequency = (double) accessCount;
            double recency = 1.0 / Math.max(1, (currentTime - lastAccessTime) / 1000000000.0);
            double age = 1.0 / Math.max(1, (currentTime - insertTime) / 1000000000.0);
            
            this.priority = frequency * 0.5 + recency * 0.3 + age * 0.2;
            
            if (level > 1) {
                this.priority *= 0.8;
            }
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s(L%d,AC:%d,P:%.2f)", 
                key, value, currentLevel, accessCount, priority);
        }
    }
    
    static class CacheLevel {
        int level;
        int capacity;
        int accessCost;
        Map<String, CacheEntry> cache;
        PriorityQueue<CacheEntry> priorityHeap;
        
        CacheLevel(int level, int capacity, int accessCost) {
            this.level = level;
            this.capacity = capacity;
            this.accessCost = accessCost;
            this.cache = new HashMap<>();
            this.priorityHeap = new PriorityQueue<>((a, b) -> Double.compare(a.priority, b.priority));
        }
        
        boolean contains(String key) {
            return cache.containsKey(key);
        }
        
        CacheEntry get(String key) {
            CacheEntry entry = cache.get(key);
            if (entry != null) {
                entry.updateAccess();
                updatePriorityHeap();
            }
            return entry;
        }
        
        void put(CacheEntry entry) {
            if (cache.containsKey(entry.key)) {
                cache.get(entry.key).value = entry.value;
                cache.get(entry.key).updateAccess();
            } else {
                entry.currentLevel = level;
                cache.put(entry.key, entry);
            }
            updatePriorityHeap();
        }
        
        CacheEntry evict() {
            updatePriorityHeap();
            if (!priorityHeap.isEmpty()) {
                CacheEntry toEvict = priorityHeap.poll();
                cache.remove(toEvict.key);
                return toEvict;
            }
            return null;
        }
        
        boolean isFull() {
            return cache.size() >= capacity;
        }
        
        void updatePriorityHeap() {
            priorityHeap.clear();
            long currentTime = System.nanoTime();
            for (CacheEntry entry : cache.values()) {
                entry.calculatePriority(level, currentTime);
                priorityHeap.offer(entry);
            }
        }
        
        List<CacheEntry> getAllEntries() {
            return new ArrayList<>(cache.values());
        }
        
        void remove(String key) {
            cache.remove(key);
            updatePriorityHeap();
        }
        
        @Override
        public String toString() {
            updatePriorityHeap();
            List<CacheEntry> sorted = new ArrayList<>(cache.values());
            sorted.sort((a, b) -> Double.compare(b.priority, a.priority));
            return "L" + level + "(" + cache.size() + "/" + capacity + "): " + sorted;
        }
    }
    
    static class MultiLevelCache {
        private CacheLevel[] levels;
        private long totalAccesses;
        private long totalCost;
        
        public MultiLevelCache() {
            levels = new CacheLevel[3];
            levels[0] = new CacheLevel(1, 2, 1);
            levels[1] = new CacheLevel(2, 5, 3);
            levels[2] = new CacheLevel(3, 10, 10);
            totalAccesses = 0;
            totalCost = 0;
        }
        
        public String get(String key) {
            totalAccesses++;
            
            for (CacheLevel level : levels) {
                if (level.contains(key)) {
                    totalCost += level.accessCost;
                    CacheEntry entry = level.get(key);
                    
                    promoteIfNeeded(entry);
                    
                    System.out.printf("GET %s from L%d (cost: %d) -> %s\n", 
                        key, level.level, level.accessCost, entry.value);
                    return entry.value;
                }
            }
            
            System.out.printf("GET %s -> NOT FOUND\n", key);
            return null;
        }
        
        public void put(String key, String value) {
            CacheEntry newEntry = new CacheEntry(key, value);
            
            for (CacheLevel level : levels) {
                if (level.contains(key)) {
                    level.put(newEntry);
                    System.out.printf("PUT %s:%s updated in L%d\n", key, value, level.level);
                    return;
                }
            }
            
            insertNewEntry(newEntry);
            rebalanceLevels();
            
            System.out.printf("PUT %s:%s inserted\n", key, value);
        }
        
        private void insertNewEntry(CacheEntry entry) {
            CacheLevel targetLevel = levels[0];
            
            if (!targetLevel.isFull()) {
                targetLevel.put(entry);
                return;
            }
            
            for (int i = 1; i < levels.length; i++) {
                if (!levels[i].isFull()) {
                    entry.currentLevel = levels[i].level;
                    levels[i].put(entry);
                    return;
                }
            }
            
            CacheEntry evicted = levels[levels.length - 1].evict();
            if (evicted != null) {
                System.out.printf("Evicted %s from L%d\n", evicted.key, levels.length);
            }
            
            entry.currentLevel = levels[levels.length - 1].level;
            levels[levels.length - 1].put(entry);
        }
        
        private void promoteIfNeeded(CacheEntry entry) {
            int currentLevelIndex = entry.currentLevel - 1;
            
            if (currentLevelIndex == 0) return;
            
            long currentTime = System.nanoTime();
            entry.calculatePriority(entry.currentLevel, currentTime);
            
            boolean shouldPromote = entry.accessCount >= 3 && 
                                  (entry.priority > getAveragePriority(currentLevelIndex - 1));
            
            if (shouldPromote && !levels[currentLevelIndex - 1].isFull()) {
                levels[currentLevelIndex].remove(entry.key);
                entry.currentLevel--;
                levels[currentLevelIndex - 1].put(entry);
                
                System.out.printf("Promoted %s from L%d to L%d\n", 
                    entry.key, currentLevelIndex + 1, currentLevelIndex);
            } else if (shouldPromote && levels[currentLevelIndex - 1].isFull()) {
                CacheLevel upperLevel = levels[currentLevelIndex - 1];
                CacheEntry victim = getLowestPriorityEntry(upperLevel);
                
                if (victim != null && entry.priority > victim.priority) {
                    levels[currentLevelIndex].remove(entry.key);
                    upperLevel.remove(victim.key);
                    
                    entry.currentLevel--;
                    victim.currentLevel++;
                    
                    levels[currentLevelIndex - 1].put(entry);
                    levels[currentLevelIndex].put(victim);
                    
                    System.out.printf("Swapped %s(L%d) with %s(L%d)\n", 
                        entry.key, currentLevelIndex, victim.key, currentLevelIndex + 1);
                }
            }
        }
        
        private double getAveragePriority(int levelIndex) {
            CacheLevel level = levels[levelIndex];
            if (level.cache.isEmpty()) return 0.0;
            
            long currentTime = System.nanoTime();
            double sum = 0.0;
            for (CacheEntry entry : level.cache.values()) {
                entry.calculatePriority(level.level, currentTime);
                sum += entry.priority;
            }
            return sum / level.cache.size();
        }
        
        private CacheEntry getLowestPriorityEntry(CacheLevel level) {
            level.updatePriorityHeap();
            return level.priorityHeap.isEmpty() ? null : level.priorityHeap.peek();
        }
        
        private void rebalanceLevels() {
            for (int i = 1; i < levels.length; i++) {
                CacheLevel currentLevel = levels[i];
                CacheLevel upperLevel = levels[i - 1];
                
                if (currentLevel.cache.isEmpty() || upperLevel.isFull()) continue;
                
                currentLevel.updatePriorityHeap();
                if (currentLevel.priorityHeap.isEmpty()) continue;
                
                List<CacheEntry> candidates = new ArrayList<>();
                for (CacheEntry entry : currentLevel.priorityHeap) {
                    candidates.add(entry);
                }
                candidates.sort((a, b) -> Double.compare(b.priority, a.priority));
                
                for (CacheEntry candidate : candidates) {
                    if (upperLevel.isFull()) break;
                    if (candidate.accessCount >= 2) {
                        currentLevel.remove(candidate.key);
                        candidate.currentLevel--;
                        upperLevel.put(candidate);
                        
                        System.out.printf("Rebalanced: moved %s from L%d to L%d\n", 
                            candidate.key, i + 1, i);
                        break;
                    }
                }
            }
        }
        
        public void printStatus() {
            System.out.println("\n=== Cache Status ===");
            for (CacheLevel level : levels) {
                System.out.println(level);
            }
            System.out.printf("Total Accesses: %d, Total Cost: %d, Avg Cost: %.2f\n", 
                totalAccesses, totalCost, totalAccesses > 0 ? (double)totalCost/totalAccesses : 0.0);
            System.out.println();
        }
        
        public void printStatistics() {
            System.out.println("\n=== Detailed Statistics ===");
            for (int i = 0; i < levels.length; i++) {
                CacheLevel level = levels[i];
                System.out.printf("Level %d: %d/%d entries, Cost per access: %d\n", 
                    i + 1, level.cache.size(), level.capacity, level.accessCost);
                
                if (!level.cache.isEmpty()) {
                    level.updatePriorityHeap();
                    System.out.println("  Entries: " + level.getAllEntries());
                }
            }
            System.out.println();
        }
    }
    
    public static void testBasicOperations() {
        System.out.println("=== Basic Operations Test ===");
        MultiLevelCache cache = new MultiLevelCache();
        
        cache.put("1", "A");
        cache.put("2", "B");
        cache.put("3", "C");
        cache.printStatus();
        
        cache.get("1");
        cache.get("1");
        cache.get("2");
        cache.printStatus();
        
        cache.put("4", "D");
        cache.put("5", "E");
        cache.put("6", "F");
        cache.printStatus();
    }
    
    public static void testPromotionDemotion() {
        System.out.println("=== Promotion and Demotion Test ===");
        MultiLevelCache cache = new MultiLevelCache();
        
        for (int i = 1; i <= 8; i++) {
            cache.put(String.valueOf(i), "Value" + i);
        }
        cache.printStatus();
        
        for (int i = 0; i < 5; i++) {
            cache.get("1");
            cache.get("2");
        }
        cache.printStatus();
        
        for (int i = 0; i < 3; i++) {
            cache.get("7");
        }
        cache.printStatus();
    }
    
    public static void testPerformanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        MultiLevelCache multiCache = new MultiLevelCache();
        Map<String, String> singleCache = new HashMap<>();
        
        Random rand = new Random(42);
        int operations = 1000;
        
        long startTime = System.nanoTime();
        for (int i = 0; i < operations; i++) {
            String key = "key" + (rand.nextInt(50) + 1);
            String value = "value" + i;
            
            if (rand.nextBoolean()) {
                multiCache.put(key, value);
            } else {
                multiCache.get(key);
            }
        }
        long multiCacheTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        for (int i = 0; i < operations; i++) {
            String key = "key" + (rand.nextInt(50) + 1);
            String value = "value" + i;
            
            if (rand.nextBoolean()) {
                singleCache.put(key, value);
            } else {
                singleCache.get(key);
            }
        }
        long singleCacheTime = System.nanoTime() - startTime;
        
        System.out.printf("Multi-level cache: %.2f ms\n", multiCacheTime / 1000000.0);
        System.out.printf("Single cache: %.2f ms\n", singleCacheTime / 1000000.0);
        multiCache.printStatistics();
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== Complexity Analysis ===");
        System.out.println("設計參數：");
        System.out.println("- L1: 容量2, 成本1 (最快存取)");
        System.out.println("- L2: 容量5, 成本3 (中等存取)");
        System.out.println("- L3: 容量10, 成本10 (最慢存取)");
        System.out.println();
        System.out.println("操作複雜度：");
        System.out.println("GET操作：");
        System.out.println("  時間複雜度：O(L + log K) (L=層級數, K=每層最大容量)");
        System.out.println("  - 搜尋各層級：O(L)");
        System.out.println("  - 更新heap：O(log K)");
        System.out.println();
        System.out.println("PUT操作：");
        System.out.println("  時間複雜度：O(L * log K)");
        System.out.println("  - 檢查各層級：O(L)");
        System.out.println("  - 插入和重平衡：O(L * log K)");
        System.out.println();
        System.out.println("空間複雜度：O(N) (N為總容量)");
        System.out.println("  - 每個條目的儲存和索引");
        System.out.println("  - 每層的priority heap");
        System.out.println();
        System.out.println("優化策略：");
        System.out.println("- 頻率優先：高存取頻率的資料往上層移動");
        System.out.println("- 時間局部性：最近存取的資料保持在快速層級");
        System.out.println("- 成本感知：平衡存取成本和命中率");
        System.out.println("- 動態重平衡：根據存取模式調整資料分布");
    }
    
    public static void main(String[] args) {
        testBasicOperations();
        testPromotionDemotion();
        testPerformanceComparison();
        complexityAnalysis();
    }
}