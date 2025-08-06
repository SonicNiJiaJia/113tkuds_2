
import java.util.Scanner;

public class TicTacToeBoard {
    // 棋盤常數
    public static final int BOARD_SIZE = 3;
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';
    
    // 遊戲狀態列舉
    public enum GameStatus {
        IN_PROGRESS,    // 進行中
        PLAYER_X_WINS,  // X獲勝
        PLAYER_O_WINS,  // O獲勝
        DRAW            // 平手
    }
    
    // 棋盤陣列
    private char[][] board;
    private char currentPlayer;
    private int moveCount;
    
    // 建構子：初始化棋盤
    public TicTacToeBoard() {
        initializeBoard();
    }
    
    // 1. 初始化 3x3 的遊戲棋盤
    public void initializeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_X; // X先手
        moveCount = 0;
        
        // 填充空白格子
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    // 2. 實作放置棋子的功能（檢查位置是否有效）
    public boolean makeMove(int row, int col) {
        // 檢查位置是否有效
        if (!isValidPosition(row, col)) {
            return false;
        }
        
        // 檢查位置是否為空
        if (!isEmpty(row, col)) {
            return false;
        }
        
        // 放置棋子
        board[row][col] = currentPlayer;
        moveCount++;
        
        return true;
    }
    
    // 檢查位置是否在棋盤範圍內
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
    
    // 檢查位置是否為空
    private boolean isEmpty(int row, int col) {
        return board[row][col] == EMPTY;
    }
    
    // 切換玩家
    public void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }
    
    // 3. 檢查獲勝條件（行、列、對角線）
    public boolean checkWin(char player) {
        return checkRows(player) || checkColumns(player) || checkDiagonals(player);
    }
    
    // 檢查行
    private boolean checkRows(char player) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean rowWin = true;
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != player) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                return true;
            }
        }
        return false;
    }
    
    // 檢查列
    private boolean checkColumns(char player) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean colWin = true;
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (board[row][col] != player) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                return true;
            }
        }
        return false;
    }
    
    // 檢查對角線
    private boolean checkDiagonals(char player) {
        // 檢查主對角線（左上到右下）
        boolean mainDiagonal = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] != player) {
                mainDiagonal = false;
                break;
            }
        }
        
        // 檢查副對角線（右上到左下）
        boolean antiDiagonal = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - 1 - i] != player) {
                antiDiagonal = false;
                break;
            }
        }
        
        return mainDiagonal || antiDiagonal;
    }
    
    // 取得詳細獲勝資訊
    public String getWinDetails(char player) {
        // 檢查行
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean rowWin = true;
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != player) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                return String.format("第 %d 行", row + 1);
            }
        }
        
        // 檢查列
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean colWin = true;
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (board[row][col] != player) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                return String.format("第 %d 列", col + 1);
            }
        }
        
        // 檢查主對角線
        boolean mainDiagonal = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] != player) {
                mainDiagonal = false;
                break;
            }
        }
        if (mainDiagonal) {
            return "主對角線";
        }
        
        // 檢查副對角線
        boolean antiDiagonal = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - 1 - i] != player) {
                antiDiagonal = false;
                break;
            }
        }
        if (antiDiagonal) {
            return "副對角線";
        }
        
        return "未知";
    }
    
    // 4. 判斷遊戲是否結束（獲勝或平手）
    public GameStatus getGameStatus() {
        // 檢查是否有玩家獲勝
        if (checkWin(PLAYER_X)) {
            return GameStatus.PLAYER_X_WINS;
        }
        if (checkWin(PLAYER_O)) {
            return GameStatus.PLAYER_O_WINS;
        }
        
        // 檢查是否平手（棋盤滿了但沒人獲勝）
        if (isBoardFull()) {
            return GameStatus.DRAW;
        }
        
        // 遊戲還在進行中
        return GameStatus.IN_PROGRESS;
    }
    
    // 檢查棋盤是否已滿
    private boolean isBoardFull() {
        return moveCount == BOARD_SIZE * BOARD_SIZE;
    }
    
    // 取得當前玩家
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    // 取得移動次數
    public int getMoveCount() {
        return moveCount;
    }
    
    // 取得指定位置的棋子
    public char getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return EMPTY;
    }
    
    // 取得剩餘空格數
    public int getEmptyCells() {
        return BOARD_SIZE * BOARD_SIZE - moveCount;
    }
    
    // 列印棋盤
    public void printBoard() {
        System.out.println("\n當前棋盤狀態：");
        System.out.println("   1   2   3");
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.printf("%d  ", i + 1);
            
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf(" %c ", board[i][j]);
                if (j < BOARD_SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            
            // 印出分隔線（除了最後一行）
            if (i < BOARD_SIZE - 1) {
                System.out.println("   -----------");
            }
        }
        System.out.println();
    }
    
    // 列印遊戲資訊
    public void printGameInfo() {
        System.out.println("=".repeat(30));
        System.out.println("      井字遊戲資訊");
        System.out.println("=".repeat(30));
        System.out.printf("當前玩家：%c\n", currentPlayer);
        System.out.printf("移動次數：%d\n", moveCount);
        System.out.printf("剩餘空格：%d\n", getEmptyCells());
        
        GameStatus status = getGameStatus();
        System.out.printf("遊戲狀態：%s\n", getStatusString(status));
        
        if (status == GameStatus.PLAYER_X_WINS || status == GameStatus.PLAYER_O_WINS) {
            char winner = (status == GameStatus.PLAYER_X_WINS) ? PLAYER_X : PLAYER_O;
            System.out.printf("獲勝方式：%s\n", getWinDetails(winner));
        }
        
        System.out.println("=".repeat(30));
    }
    
    // 取得狀態字串
    private String getStatusString(GameStatus status) {
        switch (status) {
            case IN_PROGRESS:
                return "進行中";
            case PLAYER_X_WINS:
                return "X 獲勝！";
            case PLAYER_O_WINS:
                return "O 獲勝！";
            case DRAW:
                return "平手！";
            default:
                return "未知";
        }
    }
    
    // 重置遊戲
    public void resetGame() {
        initializeBoard();
        System.out.println("遊戲已重置！");
    }
    
    // 取得可用位置列表
    public void printAvailableMoves() {
        System.out.println("可用位置：");
        boolean hasAvailable = false;
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isEmpty(i, j)) {
                    System.out.printf("(%d,%d) ", i + 1, j + 1);
                    hasAvailable = true;
                }
            }
        }
        
        if (!hasAvailable) {
            System.out.print("無可用位置");
        }
        System.out.println();
    }
    
    // 主程式：互動式遊戲
    public static void main(String[] args) {
        TicTacToeBoard game = new TicTacToeBoard();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(40));
        System.out.println("           歡迎來到井字遊戲！");
        System.out.println("=".repeat(40));
        System.out.println("遊戲規則：");
        System.out.println("- 輸入行號和列號（1-3）來放置棋子");
        System.out.println("- X 先手，O 後手");
        System.out.println("- 率先連成一線者獲勝");
        System.out.println("- 輸入 'q' 退出遊戲，'r' 重新開始");
        System.out.println("-".repeat(40));
        
        while (true) {
            game.printBoard();
            game.printGameInfo();
            
            GameStatus status = game.getGameStatus();
            
            // 檢查遊戲是否結束
            if (status != GameStatus.IN_PROGRESS) {
                System.out.println("\n遊戲結束！");
                
                if (status == GameStatus.PLAYER_X_WINS) {
                    System.out.println("🎉 恭喜！玩家 X 獲勝！");
                } else if (status == GameStatus.PLAYER_O_WINS) {
                    System.out.println("🎉 恭喜！玩家 O 獲勝！");
                } else {
                    System.out.println("🤝 平手！雙方都很厲害！");
                }
                
                System.out.print("\n是否要再玩一局？(y/n): ");
                String choice = scanner.nextLine().toLowerCase();
                if (choice.equals("y") || choice.equals("yes")) {
                    game.resetGame();
                    continue;
                } else {
                    break;
                }
            }
            
            // 玩家輸入
            game.printAvailableMoves();
            System.out.printf("玩家 %c 的回合，請輸入位置 (行 列) 或指令: ", 
                game.getCurrentPlayer());
            
            String input = scanner.nextLine().trim();
            
            // 處理特殊指令
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                System.out.println("謝謝遊玩！再見！");
                break;
            } else if (input.equalsIgnoreCase("r") || input.equalsIgnoreCase("reset")) {
                game.resetGame();
                continue;
            }
            
            // 解析位置輸入
            try {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("❌ 請輸入兩個數字（行 列），例如：1 2");
                    continue;
                }
                
                int row = Integer.parseInt(parts[0]) - 1; // 轉換為陣列索引
                int col = Integer.parseInt(parts[1]) - 1;
                
                // 嘗試放置棋子
                if (game.makeMove(row, col)) {
                    System.out.printf("✅ 玩家 %c 在位置 (%d, %d) 放置了棋子\n", 
                        game.getCurrentPlayer(), row + 1, col + 1);
                    
                    // 切換玩家
                    game.switchPlayer();
                } else {
                    if (!game.isValidPosition(row, col)) {
                        System.out.println("❌ 位置超出範圍！請輸入 1-3 之間的數字");
                    } else {
                        System.out.println("❌ 該位置已被占用！請選擇其他位置");
                    }
                }
                
            } catch (NumberFormatException e) {
                System.out.println("❌ 請輸入有效的數字！");
            }
        }
        
        scanner.close();
    }
}