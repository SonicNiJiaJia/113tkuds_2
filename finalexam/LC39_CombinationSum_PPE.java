import java.util.*;

public class LC39_CombinationSum_PPE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        
        int[] candidates = new int[n];
        for (int i = 0; i < n; i++) {
            candidates[i] = sc.nextInt();
        }
        
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        
        for (List<Integer> combination : result) {
            for (int i = 0; i < combination.size(); i++) {
                System.out.print(combination.get(i));
                if (i < combination.size() - 1) System.out.print(" ");
            }
            System.out.println();
        }
        
        sc.close();
    }
    
    private static void backtrack(int[] candidates, int remain, int start, List<Integer> path, List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (remain < 0) return;
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remain) break;
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}