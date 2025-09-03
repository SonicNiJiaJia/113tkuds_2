import java.util.*;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class lt_23_mergeksortedlists {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        return mergeKListsHelper(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeKListsHelper(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        
        if (start > end) {
            return null;
        }
        
        int mid = start + (end - start) / 2;
        ListNode left = mergeKListsHelper(lists, start, mid);
        ListNode right = mergeKListsHelper(lists, mid + 1, end);
        
        return mergeTwoLists(left, right);
    }
    
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return dummy.next;
    }
}

/*
解題邏輯與思路：
1. 分治法(Divide and Conquer)策略：將k個鏈表的合併問題分解為兩兩合併的子問題
2. 遞迴分割：將鏈表陣列從中間分割，分別處理左半部和右半部的合併
3. 終止條件：
   - 當start == end時，回傳該位置的鏈表
   - 當start > end時，回傳null
4. 合併兩個有序鏈表：使用雙指針技巧逐一比較節點值，建構合併後的有序鏈表
5. 二元樹式合併：透過遞迴呼叫形成類似二元樹的合併結構，每層將問題規模減半
6. 時間複雜度O(N*log(k))，空間複雜度O(log(k))，其中N是所有節點總數，k是鏈表數量
*/