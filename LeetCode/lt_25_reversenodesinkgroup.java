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
class lt_25_reversenodesinkgroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;
        
        while (true) {
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if (kthNode == null) {
                break;
            }
            
            ListNode nextGroupStart = kthNode.next;
            ListNode prev = nextGroupStart;
            ListNode curr = prevGroupEnd.next;
            
            while (curr != nextGroupStart) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            ListNode temp = prevGroupEnd.next;
            prevGroupEnd.next = kthNode;
            prevGroupEnd = temp;
        }
        
        return dummy.next;
    }
    
    private ListNode getKthNode(ListNode start, int k) {
        ListNode curr = start;
        for (int i = 0; i < k && curr != null; i++) {
            curr = curr.next;
        }
        return curr;
    }
}

/*
解題邏輯與思路：
1. 建立虛擬頭節點：使用dummy節點簡化邊界處理，prevGroupEnd追蹤每組的前一個節點
2. 檢查是否有足夠節點：使用getKthNode函數檢查從當前位置開始是否有k個節點可以反轉
3. 反轉k個節點：
   - 記錄下一組的起始位置nextGroupStart
   - 使用三指針技巧(prev, curr, next)在範圍內反轉鏈表
   - prev初始指向nextGroupStart，確保反轉後正確連接到下一組
4. 更新連接：
   - 將前一組的末尾連接到當前組反轉後的頭部(第k個節點)
   - 更新prevGroupEnd為當前組反轉後的尾部(原來的第1個節點)
5. 迭代處理：重複上述過程直到剩餘節點不足k個
6. 時間複雜度O(n)，空間複雜度O(1)，其中n為鏈表節點總數
*/