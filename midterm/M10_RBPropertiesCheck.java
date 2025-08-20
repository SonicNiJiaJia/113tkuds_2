import java.util.*;

public class M10_RBPropertiesCheck {
    static class Node {
        int val;
        char color;
        
        Node(int val, char color) {
            this.val = val;
            this.color = color;
        }
    }
    
    private static Node[] tree;
    private static int n;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        tree = new Node[n];
        
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            char color = sc.next().charAt(0);
            if (val == -1) {
                tree[i] = null;
            } else {
                tree[i] = new Node(val, color);
            }
        }
        
        String result = checkRBProperties();
        System.out.println(result);
        
        sc.close();
    }
    
    private static String checkRBProperties() {
        if (n == 0 || tree[0] == null) {
            return "RB Valid";
        }
        
        if (tree[0].color != 'B') {
            return "RootNotBlack";
        }
        
        String redRedCheck = checkRedRedViolation();
        if (!redRedCheck.equals("RB Valid")) {
            return redRedCheck;
        }
        
        if (checkBlackHeight(0) == -1) {
            return "BlackHeightMismatch";
        }
        
        return "RB Valid";
    }
    
    private static String checkRedRedViolation() {
        for (int i = 0; i < n; i++) {
            if (tree[i] != null && tree[i].color == 'R') {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < n && tree[left] != null && tree[left].color == 'R') {
                    return "RedRedViolation at index " + i;
                }
                
                if (right < n && tree[right] != null && tree[right].color == 'R') {
                    return "RedRedViolation at index " + i;
                }
            }
        }
        return "RB Valid";
    }
    
    private static int checkBlackHeight(int index) {
        if (index >= n || tree[index] == null) {
            return 1;
        }
        
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        
        int leftHeight = checkBlackHeight(left);
        int rightHeight = checkBlackHeight(right);
        
        if (leftHeight == -1 || rightHeight == -1 || leftHeight != rightHeight) {
            return -1;
        }
        
        return leftHeight + (tree[index].color == 'B' ? 1 : 0);
    }
}