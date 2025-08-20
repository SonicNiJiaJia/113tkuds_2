import java.util.*;

public class M08_BSTRangedSum {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
        
        int L = sc.nextInt();
        int R = sc.nextInt();
        
        TreeNode root = buildTree(values);
        int sum = rangeSum(root, L, R);
        
        System.out.println("Sum: " + sum);
        
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
    
    private static int rangeSum(TreeNode node, int L, int R) {
        if (node == null) {
            return 0;
        }
        
        if (node.val < L) {
            return rangeSum(node.right, L, R);
        }
        
        if (node.val > R) {
            return rangeSum(node.left, L, R);
        }
        
        return node.val + rangeSum(node.left, L, R) + rangeSum(node.right, L, R);
    }
}