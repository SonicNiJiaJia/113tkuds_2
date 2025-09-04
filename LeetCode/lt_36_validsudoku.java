class lt_36_validsudoku {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c != '.') {
                    int num = c - '1';
                    int boxIndex = (i / 3) * 3 + j / 3;
                    
                    if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                        return false;
                    }
                    
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        
        return true;
    }
}

/*
解題邏輯與思路：

這題要驗證9x9數獨盤面是否有效，需要檢查三個條件：行、列、3x3子方格內數字1-9不重複。

核心思路：
1. 使用三個二維boolean陣列分別記錄每行、每列、每個3x3子方格中數字的使用情況
2. 一次遍歷整個盤面，同時檢查三個條件
3. 遇到重複數字立即返回false，否則標記該數字已使用

資料結構設計：
- rows[i][num]：記錄第i行是否已使用數字num+1
- cols[j][num]：記錄第j列是否已使用數字num+1  
- boxes[k][num]：記錄第k個3x3子方格是否已使用數字num+1

關鍵計算：
- 字元轉數字：num = c - '1' (將'1'-'9'轉為0-8)
- 3x3子方格索引：boxIndex = (i / 3) * 3 + j / 3
  * (i,j)所在的3x3子方格可以用行列座標計算
  * 將9x9盤面劃分為9個3x3子方格，從左到右、從上到下編號0-8

具體步驟：
1. 初始化三個9x9的boolean陣列，預設為false
2. 遍歷盤面每個位置(i,j)：
   - 跳過空格'.'
   - 計算數字索引num和子方格索引boxIndex
   - 檢查該數字是否在當前行、列、子方格中已存在
   - 如果存在重複，返回false
   - 否則標記該數字在對應位置已使用
3. 遍歷完成返回true

例如board[0][0]='5'：
- num = '5' - '1' = 4
- boxIndex = (0/3)*3 + 0/3 = 0
- 檢查rows[0][4], cols[0][4], boxes[0][4]是否為true
- 如果都為false則標記為true，繼續；否則返回false

時間複雜度：O(1) - 固定9x9盤面遍歷
空間複雜度：O(1) - 使用固定大小陣列
*/