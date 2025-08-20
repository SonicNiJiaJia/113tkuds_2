import java.util.Scanner;

public class M02_YouBikeNextArrival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        
        int[] times = new int[n];
        for (int i = 0; i < n; i++) {
            String time = sc.nextLine();
            times[i] = timeToMinutes(time);
        }
        
        String queryTime = sc.nextLine();
        int queryMinutes = timeToMinutes(queryTime);
        
        int result = binarySearch(times, queryMinutes);
        
        if (result == -1) {
            System.out.println("No bike");
        } else {
            System.out.println(minutesToTime(times[result]));
        }
        
        sc.close();
    }
    
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
    
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
    
    private static int binarySearch(int[] times, int target) {
        int left = 0;
        int right = times.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (times[mid] > target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
}