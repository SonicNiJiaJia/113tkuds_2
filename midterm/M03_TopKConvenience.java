import java.util.*;

public class M03_TopKConvenience {
    static class Product implements Comparable<Product> {
        String name;
        int qty;
        int order;
        
        Product(String name, int qty, int order) {
            this.name = name;
            this.qty = qty;
            this.order = order;
        }
        
        @Override
        public int compareTo(Product other) {
            if (this.qty != other.qty) {
                return Integer.compare(this.qty, other.qty);
            }
            return Integer.compare(other.order, this.order);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        PriorityQueue<Product> minHeap = new PriorityQueue<>();
        
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int qty = sc.nextInt();
            Product product = new Product(name, qty, i);
            
            if (minHeap.size() < k) {
                minHeap.offer(product);
            } else if (product.compareTo(minHeap.peek()) > 0) {
                minHeap.poll();
                minHeap.offer(product);
            }
        }
        
        List<Product> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        
        Collections.reverse(result);
        
        for (Product p : result) {
            System.out.println(p.name + " " + p.qty);
        }
        
        sc.close();
    }
}

/*
 * Time Complexity: O(n log K)
 * 說明：處理n個商品，每個商品最多進行一次堆操作（插入或替換）
 * 每次堆操作需O(log K)時間，總計O(n log K)，當K<<n時比完全排序O(n log n)更優
 */
