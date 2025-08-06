// RecursiveTreePreview.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RecursiveTreePreview {

    // --- 模擬檔案系統 ---
    // 為了模擬，我們將使用 Map 來表示資料夾結構
    // 鍵是資料夾名稱，值是其內容，內容可以是檔案數(Integer)或子資料夾(Map)
    // 假設所有檔案都是 Integer 類型，資料夾是 Map<String, Object> 類型

    /**
     * 遞迴計算資料夾的總檔案數（模擬檔案系統）。
     *
     * @param folder 模擬的資料夾結構 (Map<String, Object>)
     * @return 該資料夾及其所有子資料夾中的總檔案數
     */
    public int countFilesInFolder(Map<String, Object> folder) {
        if (folder == null) {
            return 0;
        }

        int totalFiles = 0;
        for (Map.Entry<String, Object> entry : folder.entrySet()) {
            Object content = entry.getValue();
            if (content instanceof Integer) { // 如果是檔案 (Integer 代表檔案數量)
                totalFiles += (Integer) content;
            } else if (content instanceof Map) { // 如果是子資料夾
                totalFiles += countFilesInFolder((Map<String, Object>) content);
            }
        }
        return totalFiles;
    }

    // --- 多層選單結構 ---

    /**
     * 表示選單項的類別。
     */
    static class MenuItem {
        String name;
        List<MenuItem> subItems;

        public MenuItem(String name) {
            this.name = name;
            this.subItems = new ArrayList<>();
        }

        public void addSubItem(MenuItem item) {
            this.subItems.add(item);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 遞迴列印多層選單結構。
     *
     * @param menuItems 選單項列表
     * @param level     當前選單層級 (用於縮排)
     */
    public void printMenu(List<MenuItem> menuItems, int level) {
        if (menuItems == null || menuItems.isEmpty()) {
            return;
        }

        String indent = "  ".repeat(level); // 兩個空格作為一個縮排單位

        for (MenuItem item : menuItems) {
            System.out.println(indent + "- " + item.name);
            if (!item.subItems.isEmpty()) {
                printMenu(item.subItems, level + 1); // 遞迴呼叫處理子選單
            }
        }
    }

    // --- 巢狀陣列的展平 ---
    // 使用 Object 陣列來表示可以包含基本類型或子陣列的巢狀結構

    /**
     * 遞迴處理巢狀陣列的展平。
     * 將巢狀陣列中的所有元素提取到一個單一的 List 中。
     *
     * @param nestedArray 巢狀陣列 (Object[])
     * @param flattenedList 用於儲存展平後元素的列表
     */
    public void flattenNestedArray(Object[] nestedArray, List<Object> flattenedList) {
        if (nestedArray == null) {
            return;
        }

        for (Object element : nestedArray) {
            if (element instanceof Object[]) { // 如果是子陣列
                flattenNestedArray((Object[]) element, flattenedList); // 遞迴呼叫展平子陣列
            } else { // 如果是單一元素
                flattenedList.add(element);
            }
        }
    }

    // --- 巢狀清單的最大深度 ---
    // 使用 Object 來表示清單中的元素，它可以是數字或另一個 List<Object>

    /**
     * 遞迴計算巢狀清單的最大深度。
     * 假設最外層清單的深度為 1。
     *
     * @param nestedList 巢狀清單 (List<Object>)
     * @return 巢狀清單的最大深度
     */
    public int calculateMaxDepth(List<Object> nestedList) {
        if (nestedList == null || nestedList.isEmpty()) {
            return 1; // 空列表的深度定義為 1
        }

        int maxDepth = 1; // 當前層級的深度
        for (Object element : nestedList) {
            if (element instanceof List) { // 如果是子清單
                // 遞迴計算子清單的深度，並加上當前層級的 1
                maxDepth = Math.max(maxDepth, 1 + calculateMaxDepth((List<Object>) element));
            }
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        RecursiveTreePreview preview = new RecursiveTreePreview();

        System.out.println("--- 遞迴計算資料夾的總檔案數 ---");
        // 模擬檔案系統結構
        Map<String, Object> folderA = new HashMap<>();
        folderA.put("file1.txt", 1);
        folderA.put("file2.jpg", 1);
        Map<String, Object> subFolderB = new HashMap<>();
        subFolderB.put("document.pdf", 1);
        Map<String, Object> subSubFolderC = new HashMap<>();
        subSubFolderC.put("report.docx", 1);
        subFolderB.put("subSubFolderC", subSubFolderC);
        folderA.put("subFolderB", subFolderB);
        folderA.put("emptyFolder", new HashMap<>()); // 空資料夾

        int totalFiles = preview.countFilesInFolder(folderA);
        System.out.println("資料夾 A 的總檔案數: " + totalFiles); // 預期: 4
        System.out.println("---");

        System.out.println("\n--- 遞迴列印多層選單結構 ---");
        // 建立選單結構
        MenuItem menuRoot = new MenuItem("主選單");
        MenuItem fileMenu = new MenuItem("檔案");
        fileMenu.addSubItem(new MenuItem("開啟"));
        fileMenu.addSubItem(new MenuItem("儲存"));
        MenuItem recentFiles = new MenuItem("最近檔案");
        recentFiles.addSubItem(new MenuItem("文件A.txt"));
        recentFiles.addSubItem(new MenuItem("圖片B.jpg"));
        fileMenu.addSubItem(recentFiles);

        MenuItem editMenu = new MenuItem("編輯");
        editMenu.addSubItem(new MenuItem("剪下"));
        editMenu.addSubItem(new MenuItem("複製"));
        editMenu.addSubItem(new MenuItem("貼上"));

        menuRoot.addSubItem(fileMenu);
        menuRoot.addSubItem(editMenu);

        System.out.println(menuRoot.name); // 列印主選單根節點
        preview.printMenu(menuRoot.subItems, 1); // 從子項目開始遞迴列印，層級從 1 開始
        System.out.println("---");

        System.out.println("\n--- 遞迴處理巢狀陣列的展平 ---");
        Object[] nestedArr = {1, new Object[]{2, 3, new Object[]{4, 5}}, 6, new Object[]{7}};
        List<Object> flattened = new ArrayList<>();
        preview.flattenNestedArray(nestedArr, flattened);
        System.out.println("原始巢狀陣列: " + Arrays.deepToString(nestedArr));
        System.out.println("展平後陣列: " + flattened); // 預期: [1, 2, 3, 4, 5, 6, 7]
        System.out.println("---");

        System.out.println("\n--- 遞迴計算巢狀清單的最大深度 ---");
        // 建立巢狀清單
        List<Object> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Object> list2 = new ArrayList<>(Arrays.asList(1, Arrays.asList(2, 3), 4));
        List<Object> list3 = new ArrayList<>(Arrays.asList(1, Arrays.asList(2, Arrays.asList(3, 4)), 5));
        List<Object> list4 = new ArrayList<>(); // 空列表
        List<Object> list5 = new ArrayList<>(Arrays.asList(1, 2, Arrays.asList(3, 4, Arrays.asList(5, 6, Arrays.asList(7)))));

        System.out.println("清單 " + list1 + " 的最大深度: " + preview.calculateMaxDepth(list1)); // 預期: 1
        System.out.println("清單 " + list2 + " 的最大深度: " + preview.calculateMaxDepth(list2)); // 預期: 2
        System.out.println("清單 " + list3 + " 的最大深度: " + preview.calculateMaxDepth(list3)); // 預期: 3
        System.out.println("清單 " + list4 + " (空列表) 的最大深度: " + preview.calculateMaxDepth(list4)); // 預期: 1
        System.out.println("清單 " + list5 + " 的最大深度: " + preview.calculateMaxDepth(list5)); // 預期: 4
        System.out.println("---");
    }
}