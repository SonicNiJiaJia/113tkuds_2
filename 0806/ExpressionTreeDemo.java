import java.util.*;

public class ExpressionTreeDemo {
    
    static class TreeNode {
        String data;  // 可以是運算子或運算元
        TreeNode left;
        TreeNode right;
        
        public TreeNode(String data) {
            this.data = data;
        }
        
        // 判斷是否為運算子
        public boolean isOperator() {
            return data.equals("+") || data.equals("-") || 
                   data.equals("*") || data.equals("/");
        }
    }
    
    /**
     * 從後綴表達式建立表達式樹
     */
    public static TreeNode buildExpressionTree(String[] postfix) {
        Stack<TreeNode> stack = new Stack<>();
        
        for (String token : postfix) {
            TreeNode node = new TreeNode(token);
            
            if (isOperator(token)) {
                // 運算子：從堆疊取兩個運算元
                node.right = stack.pop();  // 右運算元
                node.left = stack.pop();   // 左運算元
            }
            // 運算元或運算子都放入堆疊
            stack.push(node);
        }
        
        return stack.pop();  // 最後剩下的就是根節點
    }
    
    /**
     * 判斷是否為運算子
     */
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
    
    /**
     * 計算表達式樹的值
     */
    public static double evaluateExpressionTree(TreeNode root) {
        if (root == null) return 0;
        
        // 如果是葉節點（運算元）
        if (!root.isOperator()) {
            return Double.parseDouble(root.data);
        }
        
        // 遞迴計算左右子樹的值
        double leftValue = evaluateExpressionTree(root.left);
        double rightValue = evaluateExpressionTree(root.right);
        
        // 根據運算子計算結果
        switch (root.data) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ArithmeticException("除零錯誤");
                }
                return leftValue / rightValue;
            default:
                throw new IllegalArgumentException("未知運算子: " + root.data);
        }
    }
    
    /**
     * 將表達式樹轉換為中綴表達式
     */
    public static String toInfixExpression(TreeNode root) {
        if (root == null) return "";
        
        // 如果是葉節點（運算元）
        if (!root.isOperator()) {
            return root.data;
        }
        
        String leftExpr = toInfixExpression(root.left);
        String rightExpr = toInfixExpression(root.right);
        
        return "(" + leftExpr + " " + root.data + " " + rightExpr + ")";
    }
    
    /**
     * 將表達式樹轉換為前綴表達式
     */
    public static String toPrefixExpression(TreeNode root) {
        if (root == null) return "";
        
        if (!root.isOperator()) {
            return root.data;
        }
        
        String leftExpr = toPrefixExpression(root.left);
        String rightExpr = toPrefixExpression(root.right);
        
        return root.data + " " + leftExpr + " " + rightExpr;
    }
    
    /**
     * 將表達式樹轉換為後綴表達式
     */
    public static String toPostfixExpression(TreeNode root) {
        if (root == null) return "";
        
        if (!root.isOperator()) {
            return root.data;
        }
        
        String leftExpr = toPostfixExpression(root.left);
        String rightExpr = toPostfixExpression(root.right);
        
        return leftExpr + " " + rightExpr + " " + root.data;
    }
    
    /**
     * 視覺化顯示表達式樹
     */
    public static void displayTree(TreeNode root, String prefix, boolean isLast) {
        if (root != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + root.data);
            
            if (root.left != null || root.right != null) {
                if (root.left != null) {
                    displayTree(root.left, prefix + (isLast ? "    " : "│   "), root.right == null);
                }
                if (root.right != null) {
                    displayTree(root.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        // 範例：(3 + 4) * (2 - 1) 的後綴表達式是 3 4 + 2 1 - *
        String[] postfixExpression = {"3", "4", "+", "2", "1", "-", "*"};
        
        System.out.println("=== 表達式樹示範 ===");
        System.out.println("後綴表達式: " + Arrays.toString(postfixExpression));
        
        // 建立表達式樹
        TreeNode root = buildExpressionTree(postfixExpression);
        
        // 視覺化顯示樹結構
        System.out.println("\n表達式樹結構:");
        displayTree(root, "", true);
        
        // 轉換為不同格式
        System.out.println("\n=== 表達式轉換 ===");
        System.out.println("中綴表達式: " + toInfixExpression(root));
        System.out.println("前綴表達式: " + toPrefixExpression(root));
        System.out.println("後綴表達式: " + toPostfixExpression(root));
        
        // 計算結果
        System.out.println("\n=== 表達式計算 ===");
        double result = evaluateExpressionTree(root);
        System.out.printf("計算結果: %.2f\n", result);
        
        // 測試更複雜的表達式：(5 + 3) * (8 - 6) / 2
        // 後綴表達式：5 3 + 8 6 - * 2 /
        String[] complexPostfix = {"5", "3", "+", "8", "6", "-", "*", "2", "/"};
        System.out.println("\n=== 複雜表達式測試 ===");
        System.out.println("後綴表達式: " + Arrays.toString(complexPostfix));
        
        TreeNode complexRoot = buildExpressionTree(complexPostfix);
        System.out.println("中綴表達式: " + toInfixExpression(complexRoot));
        System.out.printf("計算結果: %.2f\n", evaluateExpressionTree(complexRoot));
    }
}
