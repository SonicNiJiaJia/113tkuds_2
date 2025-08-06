public class LinkedListRecursionDemo {
    
    static class ListNode {
        int data;
        ListNode next;
        
        public ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * 遞迴列印整個串列
     */
    public static void printList(ListNode head) {
        // 停止條件：空節點
        if (head == null) {
            return;
        }
        
        // 處理當前節點，然後遞迴處理下一個
        System.out.print(head.data + " ");
        printList(head.next);
    }
    
    /**
     * 遞迴反向列印串列
     */
    public static void printListReverse(ListNode head) {
        if (head == null) {
            return;
        }
        
        // 先遞迴處理下一個節點，再列印當前節點
        printListReverse(head.next);
        System.out.print(head.data + " ");
    }
    
    /**
     * 遞迴計算串列長度
     */
    public static int getLength(ListNode head) {
        // 停止條件：空節點
        if (head == null) {
            return 0;
        }
        
        // 1（當前節點） + 剩餘節點數量
        return 1 + getLength(head.next);
    }
    
    /**
     * 遞迴搜尋特定值
     */
    public static boolean search(ListNode head, int target) {
        // 停止條件：空節點（找不到）
        if (head == null) {
            return false;
        }
        
        // 找到目標值
        if (head.data == target) {
            return true;
        }
        
        // 遞迴搜尋剩餘節點
        return search(head.next, target);
    }
    
    /**
     * 遞迴計算串列總和
     */
    public static int sumList(ListNode head) {
        if (head == null) {
            return 0;
        }
        
        return head.data + sumList(head.next);
    }
    
    /**
     * 遞迴尋找最大值
     */
    public static int findMax(ListNode head) {
        if (head == null) {
            throw new IllegalArgumentException("空串列沒有最大值");
        }
        
        if (head.next == null) {
            return head.data;
        }
        
        int maxOfRest = findMax(head.next);
        return Math.max(head.data, maxOfRest);
    }
    
    /**
     * 建立測試串列的輔助方法
     */
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
    
    public static void main(String[] args) {
        // 建立測試串列：1 -> 2 -> 3 -> 4 -> 5
        int[] values = {1, 2, 3, 4, 5};
        ListNode head = createList(values);
        
        System.out.println("=== 鏈結串列遞迴操作 ===");
        
        System.out.print("正向列印串列：");
        printList(head);
        System.out.println();
        
        System.out.print("反向列印串列：");
        printListReverse(head);
        System.out.println();
        
        System.out.println("串列長度：" + getLength(head));
        System.out.println("串列總和：" + sumList(head));
        System.out.println("最大值：" + findMax(head));
        
        System.out.println("搜尋 3：" + search(head, 3));
        System.out.println("搜尋 6：" + search(head, 6));
        
        // 測試空串列
        System.out.println("\n=== 空串列測試 ===");
        ListNode emptyList = null;
        System.out.print("空串列列印：");
        printList(emptyList);
        System.out.println("(無輸出)");
        System.out.println("空串列長度：" + getLength(emptyList));
        System.out.println("空串列搜尋：" + search(emptyList, 1));
    }
}
