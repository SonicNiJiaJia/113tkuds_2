import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class LC23_MergeKLists_Hospitals {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        sc.nextLine();
        
        ListNode[] lists = new ListNode[k];
        
        for (int i = 0; i < k; i++) {
            String line = sc.nextLine();
            String[] nums = line.split(" ");
            
            ListNode head = null;
            ListNode tail = null;
            
            for (String numStr : nums) {
                int val = Integer.parseInt(numStr);
                if (val == -1) break;
                
                ListNode node = new ListNode(val);
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    tail = node;
                }
            }
            lists[i] = head;
        }
        
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        for (ListNode list : lists) {
            if (list != null) {
                pq.offer(list);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            
            if (node.next != null) {
                pq.offer(node.next);
            }
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
        if (dummy.next != null) {
            System.out.println();
        }
    }
}