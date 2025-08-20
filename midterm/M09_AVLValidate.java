import java.util.*;

public class M09_AVLValidate {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    private static final int INVALID = Integer.MIN_VALUE;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
        
        TreeNode root = buildTree(values);
        
        if (!isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            System.out.println("Invalid BST");
        } else if (checkAVL(root) == INVALID) {
            System.out.println("Invalid AVL");
        } else {
            System.out.println("Valid");
        }
        
        sc.close();
    }
    
    private static TreeNode buildTree(int[] values) {
        if (values.length == 0 || values[0] == -1) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            if (i < values.length && values[i] != -1) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            if (i < values.length && values[i] != -1) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    private static boolean isValidBST(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        return isValidBST(node.left, min, node.val) && 
               isValidBST(node.right, node.val, max);
    }
    
    private static int checkAVL(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = checkAVL(node.left);
        if (leftHeight == INVALID) {
            return INVALID;
        }
        
        int rightHeight = checkAVL(node.right);
        if (rightHeight == INVALID) {
            return INVALID;
        }
        
        int balanceFactor = Math.abs(leftHeight - rightHeight);
        if (balanceFactor > 1) {
            return INVALID;
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：每個節點被訪問一次進行BST和AVL檢查，計算高度和平衡因子
 * 總時間複雜度為O(n)，其中n為節點數量
 */
