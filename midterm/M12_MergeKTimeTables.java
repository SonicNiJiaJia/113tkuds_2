import java.util.*;

public class M12_MergeKTimeTables {
    static class TimeEntry implements Comparable<TimeEntry> {
        int time;
        int listIndex;
        int elementIndex;
        
        TimeEntry(int time, int listIndex, int elementIndex) {
            this.time = time;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
        
        @Override
        public int compareTo(TimeEntry other) {
            return Integer.compare(this.time, other.time);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        
        List<List<Integer>> timeTables = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            int len = sc.nextInt();
            List<Integer> table = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                table.add(sc.nextInt());
            }
            timeTables.add(table);
        }
        
        List<Integer> merged = mergeKTables(timeTables);
        
        for (int i = 0; i < merged.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(merged.get(i));
        }
        System.out.println();
        
        sc.close();
    }
    
    private static List<Integer> mergeKTables(List<List<Integer>> timeTables) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<TimeEntry> minHeap = new PriorityQueue<>();
        
        for (int i = 0; i < timeTables.size(); i++) {
            if (!timeTables.get(i).isEmpty()) {
                minHeap.offer(new TimeEntry(timeTables.get(i).get(0), i, 0));
            }
        }
        
        while (!minHeap.isEmpty()) {
            TimeEntry current = minHeap.poll();
            result.add(current.time);
            
            int nextIndex = current.elementIndex + 1;
            if (nextIndex < timeTables.get(current.listIndex).size()) {
                int nextTime = timeTables.get(current.listIndex).get(nextIndex);
                minHeap.offer(new TimeEntry(nextTime, current.listIndex, nextIndex));
            }
        }
        
        return result;
    }
}