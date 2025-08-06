import java.util.*;

public class FileSystemDemo {
    
    static class FileNode {
        String name;
        boolean isDirectory;
        List<FileNode> children;
        FileNode parent;
        int size;  // 檔案大小（位元組）
        
        // 建立目錄
        public FileNode(String name, boolean isDirectory) {
            this.name = name;
            this.isDirectory = isDirectory;
            this.children = isDirectory ? new ArrayList<>() : null;
            this.parent = null;
            this.size = 0;
        }
        
        // 建立檔案（有大小）
        public FileNode(String name, int size) {
            this.name = name;
            this.isDirectory = false;
            this.children = null;
            this.parent = null;
            this.size = size;
        }
    }
    
    static class FileSystem {
        FileNode root;
        FileNode currentDirectory;
        
        public FileSystem() {
            root = new FileNode("/", true);
            currentDirectory = root;
        }
        
        /**
         * 建立目錄
         */
        public boolean mkdir(String dirName) {
            if (findChild(currentDirectory, dirName) != null) {
                System.out.println("目錄已存在: " + dirName);
                return false;
            }
            
            FileNode newDir = new FileNode(dirName, true);
            newDir.parent = currentDirectory;
            currentDirectory.children.add(newDir);
            
            System.out.println("建立目錄: " + dirName);
            return true;
        }
        
        /**
         * 建立檔案
         */
        public boolean createFile(String fileName, int size) {
            if (findChild(currentDirectory, fileName) != null) {
                System.out.println("檔案已存在: " + fileName);
                return false;
            }
            
            FileNode newFile = new FileNode(fileName, size);
            newFile.parent = currentDirectory;
            currentDirectory.children.add(newFile);
            
            System.out.println("建立檔案: " + fileName + " (" + size + " bytes)");
            return true;
        }
        
        /**
         * 切換目錄
         */
        public boolean cd(String dirName) {
            if (dirName.equals("..")) {
                if (currentDirectory.parent != null) {
                    currentDirectory = currentDirectory.parent;
                    return true;
                }
                return false;
            }
            
            FileNode targetDir = findChild(currentDirectory, dirName);
            if (targetDir != null && targetDir.isDirectory) {
                currentDirectory = targetDir;
                return true;
            }
            
            System.out.println("目錄不存在: " + dirName);
            return false;
        }
        
        /**
         * 列出目前目錄內容
         */
        public void ls() {
            System.out.println("目錄內容 (" + getCurrentPath() + "):");
            if (currentDirectory.children.isEmpty()) {
                System.out.println("  (空目錄)");
                return;
            }
            
            for (FileNode child : currentDirectory.children) {
                String type = child.isDirectory ? "[DIR]" : "[FILE]";
                String sizeInfo = child.isDirectory ? "" : " (" + child.size + " bytes)";
                System.out.println("  " + type + " " + child.name + sizeInfo);
            }
        }
        
        /**
         * 取得目前路徑
         */
        public String getCurrentPath() {
            List<String> pathComponents = new ArrayList<>();
            FileNode current = currentDirectory;
            
            while (current != null && current != root) {
                pathComponents.add(current.name);
                current = current.parent;
            }
            
            if (pathComponents.isEmpty()) {
                return "/";
            }
            
            Collections.reverse(pathComponents);
            return "/" + String.join("/", pathComponents);
        }
        
        /**
         * 計算目錄大小（遞迴）
         */
        public long calculateDirectorySize(FileNode dir) {
            if (!dir.isDirectory) {
                return dir.size;
            }
            
            long totalSize = 0;
            for (FileNode child : dir.children) {
                totalSize += calculateDirectorySize(child);
            }
            return totalSize;
        }
        
        /**
         * 尋找檔案或目錄（遞迴搜尋）
         */
        public List<String> findFile(String fileName) {
            List<String> results = new ArrayList<>();
            findFileHelper(root, fileName, "", results);
            return results;
        }
        
        private void findFileHelper(FileNode node, String fileName, String currentPath, List<String> results) {
            String nodePath = currentPath + "/" + node.name;
            
            if (node.name.equals(fileName)) {
                results.add(nodePath);
            }
            
            if (node.isDirectory && node.children != null) {
                for (FileNode child : node.children) {
                    findFileHelper(child, fileName, nodePath, results);
                }
            }
        }
        
        /**
         * 顯示目錄樹結構
         */
        public void tree() {
            System.out.println("檔案系統樹狀結構:");
            displayTree(root, "", true);
        }
        
        private void displayTree(FileNode node, String prefix, boolean isLast) {
            String icon = node.isDirectory ? "📁" : "📄";
            String sizeInfo = node.isDirectory ? "" : " (" + node.size + " bytes)";
            
            System.out.println(prefix + (isLast ? "└── " : "├── ") + icon + " " + node.name + sizeInfo);
            
            if (node.isDirectory && node.children != null && !node.children.isEmpty()) {
                for (int i = 0; i < node.children.size(); i++) {
                    boolean childIsLast = (i == node.children.size() - 1);
                    displayTree(node.children.get(i), prefix + (isLast ? "    " : "│   "), childIsLast);
                }
            }
        }
        
        /**
         * 輔助方法：在目錄中尋找子節點
         */
        private FileNode findChild(FileNode parent, String name) {
            if (parent.children == null) return null;
            
            for (FileNode child : parent.children) {
                if (child.name.equals(name)) {
                    return child;
                }
            }
            return null;
        }
    }
    
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        
        System.out.println("=== 檔案系統模擬 ===");
        
        // 建立目錄結構
        fs.mkdir("documents");
        fs.mkdir("programs");
        fs.mkdir("pictures");
        
        fs.createFile("readme.txt", 1024);
        fs.createFile("config.ini", 512);
        
        System.out.println();
        fs.ls();
        
        // 進入 documents 目錄
        System.out.println("\n進入 documents 目錄");
        fs.cd("documents");
        fs.mkdir("projects");
        fs.mkdir("reports");
        fs.createFile("important.doc", 2048);
        fs.createFile("notes.txt", 256);
        
        System.out.println();
        fs.ls();
        
        // 進入 projects 目錄
        System.out.println("\n進入 projects 目錄");
        fs.cd("projects");
        fs.mkdir("java");
        fs.mkdir("python");
        fs.createFile("project1.zip", 5120);
        
        System.out.println();
        fs.ls();
        
        // 進入 java 目錄
        System.out.println("\n進入 java 目錄");
        fs.cd("java");
        fs.createFile("Main.java", 1536);
        fs.createFile("Utils.java", 2048);
        fs.createFile("README.md", 512);
        
        System.out.println();
        fs.ls();
        
        // 顯示完整樹狀結構
        System.out.println("\n=== 完整檔案系統結構 ===");
        fs.tree();
        
        // 計算目錄大小
        System.out.println("\n=== 目錄大小計算 ===");
        long rootSize = fs.calculateDirectorySize(fs.root);
        System.out.printf("整個檔案系統大小: %d bytes (%.2f KB)\n", rootSize, rootSize / 1024.0);
        
        // 回到根目錄計算各子目錄大小
        while (fs.currentDirectory != fs.root) {
            fs.cd("..");
        }
        
        for (FileNode child : fs.root.children) {
            if (child.isDirectory) {
                long dirSize = fs.calculateDirectorySize(child);
                System.out.printf("目錄 %s 大小: %d bytes (%.2f KB)\n", 
                                child.name, dirSize, dirSize / 1024.0);
            }
        }
        
        // 檔案搜尋
        System.out.println("\n=== 檔案搜尋 ===");
        List<String> results = fs.findFile("readme.txt");
        System.out.println("搜尋 'readme.txt':");
        for (String path : results) {
            System.out.println("  " + path);
        }
        
        results = fs.findFile("Main.java");
        System.out.println("搜尋 'Main.java':");
        for (String path : results) {
            System.out.println("  " + path);
        }
    }
}
