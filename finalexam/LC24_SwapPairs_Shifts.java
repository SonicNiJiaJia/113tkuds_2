import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class LC24_SwapPairs_Shifts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;
            ListNode b = prev.next.next;
            
            prev.next = b;
            a.next = b.next;
            b.next = a;
            
            prev = a;
        }
        
        ListNode current = dummy.next;
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
}