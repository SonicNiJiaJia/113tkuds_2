import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class LC21_MergeTwoLists_Clinics {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        ListNode list1 = null;
        ListNode tail1 = null;
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            ListNode node = new ListNode(val);
            if (list1 == null) {
                list1 = node;
                tail1 = node;
            } else {
                tail1.next = node;
                tail1 = node;
            }
        }
        
        ListNode list2 = null;
        ListNode tail2 = null;
        for (int i = 0; i < m; i++) {
            int val = sc.nextInt();
            ListNode node = new ListNode(val);
            if (list2 == null) {
                list2 = node;
                tail2 = node;
            } else {
                tail2.next = node;
                tail2 = node;
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        
        if (list1 != null) {
            tail.next = list1;
        }
        if (list2 != null) {
            tail.next = list2;
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