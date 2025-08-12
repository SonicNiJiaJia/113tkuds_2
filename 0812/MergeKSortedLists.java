import java.util.*;

class ListNode {
    int val;
    ListNode next;
    
    ListNode(int val) {
        this.val = val;
    }
    
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    
    @Override
    public String toString() {
        return "Node(" + val + ")";
    }
}

public class MergeKSortedLists {
    
    // 主要合併演算法
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Min Heap 根據節點值排序
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        System.out.println("=== 合併過程 ===");
        
        // 將每個串列的第一個節點加入 heap
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                minHeap.offer(lists[i]);
                System.out.println("將串列 " + i + " 的首節點 " + lists[i].val + " 加入 heap");
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int step = 1;
        
        while (!minHeap.isEmpty()) {
            // 取出當前最小節點
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;
            
            System.out.println("步驟 " + step + ": 選擇節點 " + node.val);
            
            // 如果該節點還有下一個節點，將其加入 heap
            if (node.next != null) {
                minHeap.offer(node.next);
                System.out.println("  將下一個節點 " + node.next.val + " 加入 heap");
            }
            
            // 顯示當前 heap 狀態
            if (!minHeap.isEmpty()) {
                List<Integer> heapValues = new ArrayList<>();
                for (ListNode n : minHeap) {
                    heapValues.add(n.val);
                }
                System.out.println("  當前 heap: " + heapValues);
            }
            
            step++;
        }
        
        return dummy.next;
    }
    
    // 方法2：分治法合併（展示不同解法）
    public static ListNode mergeKListsDivideConquer(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        if (lists.length == 1) return lists[0];
        
        return divideAndMerge(lists, 0, lists.length - 1);
    }
    
    private static ListNode divideAndMerge(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        if (start + 1 == end) return mergeTwoLists(lists[start], lists[end]);
        
        int mid = start + (end - start) / 2;
        ListNode left = divideAndMerge(lists, start, mid);
        ListNode right = divideAndMerge(lists, mid + 1, end);
        
        return mergeTwoLists(left, right);
    }
    
    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        current.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
    
    // 建立測試用的連結串列
    public static ListNode createList(int[] values) {
        if (values.length == 0) return null;
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    // 印出連結串列
    public static void printList(String label, ListNode head) {
        List<Integer> values = new ArrayList<>();
        ListNode current = head;
        
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }
        
        System.out.println(label + ": " + values);
    }
    
    public static void main(String[] args) {
        System.out.println("=== 合併 K 個有序連結串列 ===");
        
        // 建立測試串列
        ListNode list1 = createList(new int[]{1, 4, 5});
        ListNode list2 = createList(new int[]{1, 3, 4});
        ListNode list3 = createList(new int[]{2, 6});
        
        printList("串列 1", list1);
        printList("串列 2", list2);
        printList("串列 3", list3);
        
        System.out.println("\n方法1: 使用 Min Heap");
        ListNode[] lists1 = {
            createList(new int[]{1, 4, 5}),
            createList(new int[]{1, 3, 4}),
            createList(new int[]{2, 6})
        };
        ListNode merged1 = mergeKLists(lists1);
        printList("合併結果", merged1);
        
        System.out.println("\n方法2: 分治法");
        ListNode[] lists2 = {
            createList(new int[]{1, 4, 5}),
            createList(new int[]{1, 3, 4}),
            createList(new int[]{2, 6})
        };
        ListNode merged2 = mergeKListsDivideConquer(lists2);
        printList("合併結果", merged2);
        
        System.out.println("\n複雜度比較:");
        System.out.println("Min Heap 方法: 時間 O(n log k), 空間 O(k)");
        System.out.println("分治法: 時間 O(n log k), 空間 O(log k)");
        System.out.println("其中 n 為總節點數，k 為串列數量");
    }
}

