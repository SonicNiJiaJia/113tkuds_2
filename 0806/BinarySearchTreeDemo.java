public class BinarySearchTreeDemo {
    
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        
        public TreeNode(int data) {
            this.data = data;
        }
    }
    
    static class BST {
        TreeNode root;
        
        public BST() {
            root = null;
        }
        
        /**
         * 插入節點
         */
        public void insert(int data) {
            root = insertRec(root, data);
        }
        
        private TreeNode insertRec(TreeNode root, int data) {
            // 如果樹為空，建立新節點
            if (root == null) {
                return new TreeNode(data);
            }
            
            // 遞迴插入
            if (data < root.data) {
                root.left = insertRec(root.left, data);
            } else if (data > root.data) {
                root.right = insertRec(root.right, data);
            }
            // 如果值相等，不插入重複值
            
            return root;
        }
        
        /**
         * 搜尋節點
         */
        public boolean search(int data) {
            return searchRec(root, data);
        }
        
        private boolean searchRec(TreeNode root, int data) {
            // 空樹或找不到
            if (root == null) {
                return false;
            }
            
            // 找到目標值
            if (root.data == data) {
                return true;
            }
            
            // 遞迴搜尋
            if (data < root.data) {
                return searchRec(root.left, data);
            } else {
                return searchRec(root.right, data);
            }
        }
        
        /**
         * 刪除節點
         */
        public void delete(int data) {
            root = deleteRec(root, data);
        }
        
        private TreeNode deleteRec(TreeNode root, int data) {
            if (root == null) {
                return null;
            }
            
            if (data < root.data) {
                root.left = deleteRec(root.left, data);
            } else if (data > root.data) {
                root.right = deleteRec(root.right, data);
            } else {
                // 找到要刪除的節點
                
                // 情況1：沒有子節點或只有一個子節點
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }
                
                // 情況2：有兩個子節點
                // 找到右子樹的最小值（中序後繼）
                root.data = findMin(root.right);
                // 刪除中序後繼
                root.right = deleteRec(root.right, root.data);
            }
            
            return root;
        }
        
        /**
         * 尋找最小值
         */
        public int findMin() {
            if (root == null) {
                throw new RuntimeException("樹為空");
            }
            return findMin(root);
        }
        
        private int findMin(TreeNode root) {
            while (root.left != null) {
                root = root.left;
            }
            return root.data;
        }
        
        /**
         * 尋找最大值
         */
        public int findMax() {
            if (root == null) {
                throw new RuntimeException("樹為空");
            }
            return findMax(root);
        }
        
        private int findMax(TreeNode root) {
            while (root.right != null) {
                root = root.right;
            }
            return root.data;
        }
        
        /**
         * 中序走訪（會得到排序結果）
         */
        public void inOrderTraversal() {
            inOrderRec(root);
            System.out.println();
        }
        
        private void inOrderRec(TreeNode root) {
            if (root != null) {
                inOrderRec(root.left);
                System.out.print(root.data + " ");
                inOrderRec(root.right);
            }
        }
        
        /**
         * 驗證是否為有效的BST
         */
        public boolean isValidBST() {
            return validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        
        private boolean validate(TreeNode node, int min, int max) {
            if (node == null) return true;
            
            if (node.data <= min || node.data >= max) {
                return false;
            }
            
            return validate(node.left, min, node.data) && 
                   validate(node.right, node.data, max);
        }
    }
    
    public static void main(String[] args) {
        BST bst = new BST();
        
        System.out.println("=== 二元搜尋樹操作示範 ===");
        
        // 插入操作
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        System.out.println("插入數據: " + java.util.Arrays.toString(values));
        
        for (int value : values) {
            bst.insert(value);
        }
        
        System.out.print("中序走訪（排序結果）: ");
        bst.inOrderTraversal();
        
        // 搜尋操作
        System.out.println("\n=== 搜尋操作 ===");
        int[] searchValues = {35, 55, 25, 100};
        for (int value : searchValues) {
            boolean found = bst.search(value);
            System.out.printf("搜尋 %d: %s\n", value, found ? "找到" : "找不到");
        }
        
        // 最大值和最小值
        System.out.println("\n=== 極值查詢 ===");
        System.out.println("最小值: " + bst.findMin());
        System.out.println("最大值: " + bst.findMax());
        
        // 刪除操作
        System.out.println("\n=== 刪除操作 ===");
        
        System.out.print("刪除葉節點 10 前: ");
        bst.inOrderTraversal();
        bst.delete(10);
        System.out.print("刪除葉節點 10 後: ");
        bst.inOrderTraversal();
        
        System.out.print("刪除有一個子節點的 20 前: ");
        bst.inOrderTraversal();
        bst.delete(20);
        System.out.print("刪除有一個子節點的 20 後: ");
        bst.inOrderTraversal();
        
        System.out.print("刪除有兩個子節點的 30 前: ");
        bst.inOrderTraversal();
        bst.delete(30);
        System.out.print("刪除有兩個子節點的 30 後: ");
        bst.inOrderTraversal();
        
        // 驗證BST
        System.out.println("是否為有效的BST: " + bst.isValidBST());
    }
}
