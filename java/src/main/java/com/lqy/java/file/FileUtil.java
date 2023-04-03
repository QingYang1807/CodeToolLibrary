package com.lqy.java.file;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 创建文件
     * create a file
     * @param filePath 文件路径
     * @return 文件
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 读取文件内容
     * read file content
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }

    /**
     * 写入文件内容
     * write file content
     * @param filePath 文件路径
     * @param content 写入的内容
     * @throws IOException
     */
    public static void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = content.getBytes();
        Files.write(path, bytes);
    }

    /**
     * 复制文件
     * copy file
     * @param sourceFilePath 源文件路径
     * @param destinationFilePath 目标文件路径
     * @throws IOException
     */
    public static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationFilePath);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 复制文件
     * @param sourceFilePath 源文件路径
     * @param targetFilePath 目标文件路径
     * @throws IOException
     */
    public static void copyFile2(String sourceFilePath, String targetFilePath) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        try (InputStream is = new FileInputStream(sourceFile);
             OutputStream os = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 删除文件
     * delete file
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }

    /**
     * 追加内容到文件
     * append content to file
     * @param filePath 文件路径
     * @param content 追加的内容
     * @throws IOException
     */
    public static void appendToFile(String filePath, String content) throws IOException {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        }
    }

    /**
     * 创建目录
     * create directory
     * @param dirPath 目录路径
     */
    public static void createDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 获取文件大小
     * get file size
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    /**
     * 遍历目录下的所有文件
     * list all files in directory
     * @param dirPath 目录路径
     */
    public static void listFilesInDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    public static void listAllFilesRecursively(String dirPath) {
        File dir = new File(dirPath);
        listFilesRecursively(dir);
    }

    /**
     * 递归遍历目录下的所有文件
     * recursively list all files in directory
     * @param dir 目录
     */
    private static void listFilesRecursively(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                listFilesRecursively(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * 读取文件的一部分
     * read part of file
     * @param filePath 文件路径
     * @param startPosition 开始位置
     * @param length 长度
     * @return
     * @throws IOException
     */
    public static byte[] readFilePart(String filePath, int startPosition, int length) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            byte[] bytes = new byte[length];
            raf.seek(startPosition);
            raf.read(bytes);
            return bytes;
        }
    }

    /**
     * 文件重命名
     * rename file
     * @param oldFileName 旧文件名
     * @param newFileName 新文件名
     * @return 是否重命名成功
     */
    public static boolean renameFile(String oldFileName, String newFileName) {
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);
        return oldFile.renameTo(newFile);
    }

    /**
     * 检查文件是否存在
     * check if file exists
     * @param filePath 文件路径
     * @return 是否存在
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 文件对比（逐字节比较）
     * compare files byte by byte
     * @param filePath1 文件路径1
     * @param filePath2 文件路径2
     * @return 是否相同
     * @throws IOException
     */
    public static boolean filesAreEqual(String filePath1, String filePath2) throws IOException {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);

        if (file1.length() != file2.length()) {
            return false;
        }

        try (InputStream is1 = new FileInputStream(file1);
             InputStream is2 = new FileInputStream(file2)) {
            int data1;
            int data2;
            while ((data1 = is1.read()) != -1) {
                data2 = is2.read();
                if (data1 != data2) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 搜索文件（根据文件名）
     * search files by name
     * @param dirPath 目录路径
     * @param fileName 文件名
     * @return 文件列表
     */
    public static List<File> searchFilesByName(String dirPath, String fileName) {
        File dir = new File(dirPath);
        List<File> result = new ArrayList<>();
        searchFilesRecursively(dir, fileName, result);
        return result;
    }

    /**
     * 递归搜索文件（根据文件名）
     * recursively search files by name
     * @param dir 目录
     * @param fileName 文件名
     * @param result 结果列表
     */
    private static void searchFilesRecursively(File dir, String fileName, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesRecursively(file, fileName, result);
            } else {
                if (file.getName().equals(fileName)) {
                    result.add(file);
                }
            }
        }
    }

    /**
     * 文件合并
     * merge files
     * @param sourceFilePaths 源文件路径列表
     * @param targetFilePath 目标文件路径
     * @throws IOException
     */
    public static void mergeFiles(List<String> sourceFilePaths, String targetFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(targetFilePath)) {
            for (String sourceFilePath : sourceFilePaths) {
                File sourceFile = new File(sourceFilePath);
                try (InputStream is = new FileInputStream(sourceFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }

}
