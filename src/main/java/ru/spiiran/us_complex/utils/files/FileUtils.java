package ru.spiiran.us_complex.utils.files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {
    public static void deleteFile(String filePath) throws IOException {
        Path fileToDelete = Paths.get(filePath);
        if (Files.exists(fileToDelete)) {
            Files.delete(fileToDelete);
        } else{
            throw new FileNotFoundException("SMAO is corrupted");
        }
    }
    public static void deleteDirectoryContents(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
