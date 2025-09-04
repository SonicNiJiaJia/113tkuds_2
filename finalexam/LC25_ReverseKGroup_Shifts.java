import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class LC25_ReverseKGroup_Shifts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        
        String line = sc.nextLine();
        if (line.trim().isEmpty()) {
            System.out.println();
            return;
        }
        
        String[] nums = line.split(" ");
        
        ListNode head = null;
        ListNode tail = null;
        
        for (String numStr : nums) {
            int val = Integer.parseInt(numStr);
            ListNode node = new ListNode(val);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        
        head = reverseKGroup(head, k);
        
        ListNode current = head;
        boolean first = true;
        while (current != null) {
            if (!first) {
                System.out.print(" ");
            }
            System.out.print(current.val);
            current = current.next;
            first = false;
        }
        System.out.println();
    }
    
    private static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        int length = getLength(head);
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (length >= k) {
            ListNode start = prev.next;
            ListNode end = start;
            for (int i = 1; i < k; i++) {
                end = end.next;
            }
            ListNode next = end.next;
            
            reverse(start, end);
            
            prev.next = end;
            start.next = next;
            prev = start;
            
            length -= k;
        }
        
        return dummy.next;
    }
    
    private static void reverse(ListNode start, ListNode end) {
        ListNode endNext = end.next;
        ListNode prev = endNext;
        ListNode current = start;
        
        while (current != endNext) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
    }
    
    private static int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
}