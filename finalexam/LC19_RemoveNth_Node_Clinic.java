import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class LC19_RemoveNth_Node_Clinic {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        ListNode head = null;
        ListNode tail = null;
        
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            ListNode node = new ListNode(val);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        
        int k = sc.nextInt();
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        slow.next = slow.next.next;
        
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
        if (dummy.next != null) {
            System.out.println();
        }
    }
}