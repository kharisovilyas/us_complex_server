package ru.spiiran.us_complex.utils.files;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DirectoryNameGenerator {
    private static DirectoryNameGenerator instance;
    private String directoryName;

    private DirectoryNameGenerator() {
        if (instance != null) {
            throw new IllegalStateException("Already initialized");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss");
        directoryName = "modelling_" + now.format(formatter);
    }

    public static synchronized DirectoryNameGenerator getInstance() {
        if (instance == null) {
            instance = new DirectoryNameGenerator();
        }
        return instance;
    }

    public String generateUniqueDirectoryName() {
        return directoryName;
    }
}
