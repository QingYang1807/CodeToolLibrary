package com.lqy.java.file;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    /**
     * 创建文件
     * @param filePath 文件路径
     * @return 文件
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path.toFile();
    }

    /**
     * 读取文件内容并返回为字符串
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("文件不存在: " + filePath);
        }
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    /**
     * 写入文件内容（覆盖写入）
     * @param filePath 文件路径
     * @param content 写入的内容
     * @throws IOException
     */
    public static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }

    /**
     * 向文件追加内容
     * @param filePath 文件路径
     * @param content 追加的内容
     * @throws IOException
     */
    public static void appendToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(content);
        }
    }

    /**
     * 清空文件内容
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void clearFileContent(String filePath) throws IOException {
        writeFile(filePath, "");
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("文件不存在: " + filePath);
        }
        Files.delete(path);
    }

    /**
     * 复制文件
     * @param sourceFilePath 源文件路径
     * @param targetFilePath 目标文件路径
     * @throws IOException
     */
    public static void copyFile(String sourceFilePath, String targetFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetFilePath);
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 移动文件
     * @param sourceFilePath 源文件路径
     * @param targetFilePath 目标文件路径
     * @throws IOException
     */
    public static void moveFile(String sourceFilePath, String targetFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetFilePath);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 创建目录
     * @param dirPath 目录路径
     * @throws IOException
     */
    public static void createDirectory(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * 获取文件大小
     * @param filePath 文件路径
     * @return 文件大小
     */
    public static long getFileSize(String filePath) {
        return new File(filePath).length();
    }

    /**
     * 列出目录下的所有文件
     * @param dirPath 目录路径
     * @return 文件列表
     */
    public static List<File> listFilesInDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        List<File> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 重命名文件
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
     * @param filePath 文件路径
     * @return 是否存在
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * 文件对比（逐字节比较）
     * @param filePath1 文件路径1
     * @param filePath2 文件路径2
     * @return 是否相同
     * @throws IOException
     */
    public static boolean filesAreEqual(String filePath1, String filePath2) throws IOException {
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        if (Files.size(path1) != Files.size(path2)) {
            return false;
        }

        try (InputStream is1 = Files.newInputStream(path1);
             InputStream is2 = Files.newInputStream(path2)) {

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
     * 合并多个文件
     * @param sourceFilePaths 源文件路径列表
     * @param targetFilePath 目标文件路径
     * @throws IOException
     */
    public static void mergeFiles(List<String> sourceFilePaths, String targetFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(targetFilePath)) {
            for (String sourceFilePath : sourceFilePaths) {
                try (InputStream is = new FileInputStream(sourceFilePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }

    /**
     * 搜索文件（根据文件名）
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

    private static void searchFilesRecursively(File dir, String fileName, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesRecursively(file, fileName, result);
            } else if (file.getName().equals(fileName)) {
                result.add(file);
            }
        }
    }
}
