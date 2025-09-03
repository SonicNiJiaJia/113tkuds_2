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
class lt_21_mergetwosortedlists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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
1. 建立虛擬頭節點(dummy node)：創建一個虛擬節點簡化邊界處理，避免特別處理第一個節點
2. 使用指針追蹤當前位置：current指針用來建構新的合併鏈表
3. 雙指針比較合併：
   - 同時遍歷兩個鏈表，比較當前節點的值
   - 將較小值的節點連接到結果鏈表中
   - 移動被選中鏈表的指針到下一個節點
4. 處理剩餘節點：當其中一個鏈表遍歷完成時，將另一個鏈表的剩餘部分直接連接到結果後面
5. 返回結果：返回dummy.next作為合併後鏈表的頭節點
6. 時間複雜度O(m+n)，空間複雜度O(1)，其中m和n分別是兩個鏈表的長度
*/