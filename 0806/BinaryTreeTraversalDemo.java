import java.util.*;

public class BinaryTreeTraversalDemo {
    
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        
        public TreeNode(int data) {
            this.data = data;
        }
    }
    
    /**
     * 前序走訪：根 -> 左 -> 右
     * 應用：複製樹、計算表達式的前綴表示法
     */
    public static void preOrderTraversal(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + " ");  // 處理根節點
            preOrderTraversal(root.left);       // 走訪左子樹
            preOrderTraversal(root.right);      // 走訪右子樹
        }
    }
    
    /**
     * 中序走訪：左 -> 根 -> 右
     * 應用：在BST中產生排序序列
     */
    public static void inOrderTraversal(TreeNode root) {
        if (root != null) {
            inOrderTraversal(root.left);        // 走訪左子樹
            System.out.print(root.data + " ");  // 處理根節點
            inOrderTraversal(root.right);       // 走訪右子樹
        }
    }
    
    /**
     * 後序走訪：左 -> 右 -> 根
     * 應用：刪除樹、計算表達式的後綴表示法
     */
    public static void postOrderTraversal(TreeNode root) {
        if (root != null) {
            postOrderTraversal(root.left);      // 走訪左子樹
            postOrderTraversal(root.right);     // 走訪右子樹
            System.out.print(root.data + " ");  // 處理根節點
        }
    }
    
    /**
     * 層序走訪：逐層從左到右走訪
     * 應用：列印樹的層級結構、尋找最短路徑
     */
    public static void levelOrderTraversal(TreeNode root) {
        if (root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.data + " ");
            
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }
    
    /**
     * 層序走訪 - 分層顯示
     */
    public static void levelOrderByLevels(TreeNode root) {
        if (root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.printf("第 %d 層: ", level);
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                System.out.print(current.data + " ");
                
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            
            System.out.println();
            level++;
        }
    }
    
    public static void main(String[] args) {
        // 建立測試樹
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        
        System.out.println("=== 二元樹走訪方法 ===");
        
        System.out.print("前序走訪 (根→左→右): ");
        preOrderTraversal(root);
        System.out.println();
        
        System.out.print("中序走訪 (左→根→右): ");
        inOrderTraversal(root);
        System.out.println();
        
        System.out.print("後序走訪 (左→右→根): ");
        postOrderTraversal(root);
        System.out.println();
        
        System.out.print("層序走訪 (逐層): ");
        levelOrderTraversal(root);
        System.out.println();
        
        System.out.println("\n=== 分層顯示 ===");
        levelOrderByLevels(root);
    }
}
