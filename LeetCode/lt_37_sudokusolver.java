class lt_37_sudokusolver {
    public void solveSudoku(char[][] board) {
        solve(board);
    }
    
    private boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;
                            
                            if (solve(board)) {
                                return true;
                            }
                            
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == c) {
                return false;
            }
            
            if (board[row][i] == c) {
                return false;
            }
            
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                return false;
            }
        }
        return true;
    }
}

/*
解題邏輯與思路：

這題要求解數獨難題，填入空白格子使整個盤面滿足數獨規則。這是一個經典的回溯算法問題。

核心思路：
1. 使用回溯法逐個填入空格
2. 對每個空格嘗試數字1-9，檢查是否違反數獨規則
3. 如果當前數字有效，遞歸解決剩餘問題
4. 如果遞歸失敗，回溯並嘗試下一個數字

算法結構：
主函數solve：
- 遍歷盤面找到空格('.')
- 對每個空格嘗試1-9
- 使用isValid檢查數字是否合法
- 如果合法，填入並遞歸求解
- 如果遞歸成功返回true，否則回溯(恢復'.')
- 如果1-9都嘗試失敗，返回false觸發回溯

輔助函數isValid：
檢查在位置(row,col)放入字符c是否合法：
1. 檢查同一列是否已存在c
2. 檢查同一行是否已存在c  
3. 檢查同一3x3子方格是否已存在c

關鍵技巧：
3x3子方格的遍歷：board[3*(row/3) + i/3][3*(col/3) + i%3]
- 3*(row/3)和3*(col/3)確定子方格左上角
- i/3和i%3將線性索引i(0-8)轉為子方格內的行列偏移

回溯過程：
1. 找到空格，嘗試數字1
2. 如果數字1合法且後續遞歸成功，直接返回
3. 如果後續遞歸失敗，恢復空格並嘗試數字2
4. 重複直到找到解或確定無解

遞歸終止條件：
- 成功：遍歷完所有格子未找到空格，說明已填滿
- 失敗：某個空格1-9都無法填入

時間複雜度：O(9^(空格數)) - 最壞情況下每個空格嘗試9個數字
空間複雜度：O(1) - 原地修改，遞歸深度最多81層
*/