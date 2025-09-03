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
class lt_19_removenthnodefromendoflist {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        second.next = second.next.next;
        
        return dummy.next;
    }
}

/*
解題邏輯與思路：
1. 使用虛擬頭節點(dummy node)：創建一個指向原頭節點的虛擬節點，簡化邊界情況處理(如移除頭節點)
2. 雙指針技巧：使用兩個指針first和second，初始都指向虛擬頭節點
3. 建立指針間距：讓first指針先移動n+1步，使first和second之間相距n+1個節點
4. 同步移動指針：當first和second同時向前移動，當first到達null時，second正好位於要刪除節點的前一個節點
5. 執行刪除操作：通過修改second.next = second.next.next來跳過要刪除的節點
6. 一次遍歷完成：整個過程只需要遍歷鏈表一次，時間複雜度O(L)，空間複雜度O(1)
*/