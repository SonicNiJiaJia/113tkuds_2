public class AVLNode {
    int data;
    AVLNode left, right;
    int height;

    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }

    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }

    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        AVLNode root = new AVLNode(10);
        root.left = new AVLNode(5);
        root.right = new AVLNode(15);
        root.left.updateHeight();
        root.right.updateHeight();
        root.updateHeight();
        System.out.println("height=" + root.height + ", balance=" + root.getBalance());
    }

    // 提供 JUnit 測試用的 getter
    public int getValue() {
        return data;
    }
    
    public int getBalanceFactor() {
        return getBalance();
    }
}