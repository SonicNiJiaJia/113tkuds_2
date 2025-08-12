import java.util.*;

public class MeetingRoomScheduler {
    
    static class Meeting {
        int start;
        int end;
        int duration;
        int id;
        
        Meeting(int start, int end, int id) {
            this.start = start;
            this.end = end;
            this.duration = end - start;
            this.id = id;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }
    
    static class Event {
        int time;
        int type;
        
        Event(int time, int type) {
            this.time = time;
            this.type = type;
        }
    }
    
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        for (int[] interval : intervals) {
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }
        
        return minHeap.size();
    }
    
    public static int minMeetingRoomsEvent(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        List<Event> events = new ArrayList<>();
        
        for (int[] interval : intervals) {
            events.add(new Event(interval[0], 1));
            events.add(new Event(interval[1], -1));
        }
        
        events.sort((a, b) -> {
            if (a.time != b.time) {
                return a.time - b.time;
            }
            return a.type - b.type;
        });
        
        int maxRooms = 0;
        int currentRooms = 0;
        
        for (Event event : events) {
            currentRooms += event.type;
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        
        return maxRooms;
    }
    
    public static int maxMeetingTime(int[][] intervals, int numRooms) {
        if (intervals == null || intervals.length == 0 || numRooms == 0) {
            return 0;
        }
        
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            meetings.add(new Meeting(intervals[i][0], intervals[i][1], i));
        }
        
        meetings.sort((a, b) -> a.end - b.end);
        
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int i = 0; i < numRooms; i++) {
            rooms.offer(0);
        }
        
        List<Meeting> scheduled = new ArrayList<>();
        int totalTime = 0;
        
        for (Meeting meeting : meetings) {
            if (!rooms.isEmpty() && rooms.peek() <= meeting.start) {
                rooms.poll();
                rooms.offer(meeting.end);
                scheduled.add(meeting);
                totalTime += meeting.duration;
            }
        }
        
        return totalTime;
    }
    
    public static List<Meeting> getOptimalSchedule(int[][] intervals, int numRooms) {
        if (intervals == null || intervals.length == 0 || numRooms == 0) {
            return new ArrayList<>();
        }
        
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            meetings.add(new Meeting(intervals[i][0], intervals[i][1], i));
        }
        
        meetings.sort((a, b) -> a.end - b.end);
        
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int i = 0; i < numRooms; i++) {
            rooms.offer(0);
        }
        
        List<Meeting> scheduled = new ArrayList<>();
        
        for (Meeting meeting : meetings) {
            if (!rooms.isEmpty() && rooms.peek() <= meeting.start) {
                rooms.poll();
                rooms.offer(meeting.end);
                scheduled.add(meeting);
            }
        }
        
        return scheduled;
    }
    
    public static void testMinRooms(int[][] intervals) {
        System.out.println("會議：" + Arrays.deepToString(intervals));
        
        int result1 = minMeetingRooms(intervals);
        int result2 = minMeetingRoomsEvent(intervals);
        
        System.out.println("Min Heap方法：需要" + result1 + "個會議室");
        System.out.println("事件排序方法：需要" + result2 + "個會議室");
        
        analyzeSchedule(intervals);
        System.out.println();
    }
    
    private static void analyzeSchedule(int[][] intervals) {
        if (intervals.length == 0) return;
        
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> a[0] - b[0]);
        
        System.out.println("詳細排程過程：");
        for (int[] meeting : sorted) {
            if (!rooms.isEmpty() && rooms.peek() <= meeting[0]) {
                int freedRoom = rooms.poll();
                System.out.println("會議" + Arrays.toString(meeting) + " 使用已釋放的會議室(結束時間:" + freedRoom + ")");
                rooms.offer(meeting[1]);
            } else {
                System.out.println("會議" + Arrays.toString(meeting) + " 需要新會議室");
                rooms.offer(meeting[1]);
            }
            System.out.println("  當前使用" + rooms.size() + "個會議室，結束時間：" + new ArrayList<>(rooms));
        }
    }
    
    public static void testMaxTime(int[][] intervals, int numRooms) {
        System.out.println("會議：" + Arrays.deepToString(intervals) + ", 會議室數量：" + numRooms);
        
        int maxTime = maxMeetingTime(intervals, numRooms);
        List<Meeting> schedule = getOptimalSchedule(intervals, numRooms);
        
        System.out.println("最大總會議時間：" + maxTime);
        System.out.print("最佳安排：");
        for (int i = 0; i < schedule.size(); i++) {
            System.out.print(schedule.get(i));
            if (i < schedule.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        System.out.println("詳細分析：");
        for (Meeting meeting : schedule) {
            System.out.println("  會議" + meeting + " 時長：" + meeting.duration);
        }
        System.out.println();
    }
    
    public static void performanceTest() {
        System.out.println("=== 效能測試 ===");
        
        Random rand = new Random(42);
        int n = 1000;
        int[][] intervals = new int[n][2];
        
        for (int i = 0; i < n; i++) {
            int start = rand.nextInt(1000);
            int duration = rand.nextInt(100) + 1;
            intervals[i] = new int[]{start, start + duration};
        }
        
        long startTime = System.nanoTime();
        int rooms1 = minMeetingRooms(intervals);
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int rooms2 = minMeetingRoomsEvent(intervals);
        long time2 = System.nanoTime() - startTime;
        
        System.out.println("測試" + n + "個會議：");
        System.out.println("Min Heap方法：" + rooms1 + "個會議室，時間：" + time1/1000000 + "ms");
        System.out.println("事件方法：" + rooms2 + "個會議室，時間：" + time2/1000000 + "ms");
        
        startTime = System.nanoTime();
        int maxTime = maxMeetingTime(intervals, rooms1 / 2);
        long time3 = System.nanoTime() - startTime;
        
        System.out.println("最大化時間(" + (rooms1/2) + "個會議室)：" + maxTime + "，時間：" + time3/1000000 + "ms");
    }
    
    public static void complexityAnalysis() {
        System.out.println("=== 複雜度分析 ===");
        System.out.println("假設有 N 個會議");
        System.out.println();
        System.out.println("最少會議室數量問題：");
        System.out.println("方法1 - Min Heap：");
        System.out.println("  時間複雜度：O(N log N)");
        System.out.println("  空間複雜度：O(N)");
        System.out.println("  排序 + 每個會議最多一次heap操作");
        System.out.println();
        System.out.println("方法2 - 事件排序：");
        System.out.println("  時間複雜度：O(N log N)");
        System.out.println("  空間複雜度：O(N)");
        System.out.println("  創建2N個事件 + 排序");
        System.out.println();
        System.out.println("最大化會議時間問題：");
        System.out.println("  時間複雜度：O(N log N)");
        System.out.println("  空間複雜度：O(R) (R為會議室數量)");
        System.out.println("  貪心策略：按結束時間排序");
        System.out.println();
        System.out.println("應用場景：");
        System.out.println("- 會議室預訂系統");
        System.out.println("- CPU任務調度");
        System.out.println("- 資源分配優化");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 最少會議室數量測試 ===");
        
        testMinRooms(new int[][]{{0,30},{5,10},{15,20}});
        testMinRooms(new int[][]{{9,10},{4,9},{4,17}});
        testMinRooms(new int[][]{{1,5},{8,9},{8,9}});
        
        System.out.println("=== 最大化會議時間測試 ===");
        
        testMaxTime(new int[][]{{1,4},{2,3},{4,6}}, 1);
        testMaxTime(new int[][]{{0,30},{5,10},{15,20}}, 2);
        testMaxTime(new int[][]{{1,3},{3,6},{6,8},{8,10}}, 2);
        
        performanceTest();
        System.out.println();
        complexityAnalysis();
    }
}