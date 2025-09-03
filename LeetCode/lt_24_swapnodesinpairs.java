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
class lt_24_swapnodesinpairs {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;
            
            prev.next = second;
            first.next = second.next;
            second.next = first;
            
            prev = first;
        }
        
        return dummy.next;
    }
}

/*
解題邏輯與思路：
1. 建立虛擬頭節點：創建dummy節點指向原頭節點，簡化邊界條件處理
2. 使用prev指針追蹤前一個節點：prev指針始終指向當前需要交換的兩個節點的前一個節點
3. 檢查交換條件：確保存在兩個相鄰節點可以進行交換(prev.next和prev.next.next都不為null)
4. 三步交換操作：
   - first指向第一個節點，second指向第二個節點
   - 將prev連接到second節點
   - 將first連接到second的下一個節點
   - 將second連接到first節點
5. 更新prev指針：交換完成後，prev指針移動到已交換的兩個節點中的後一個(原first節點)
6. 時間複雜度O(n)，空間複雜度O(1)，其中n為鏈表節點數量
*/