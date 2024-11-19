package com.example.Lee.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ImageFileUploadSystem {
    private final String uploadDir = "/var/www/ptu/uploads";

    public String saveImageFile(MultipartFile file, String userId) throws IOException {
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path userDir = Paths.get(uploadDir, dateDir);

        if (Files.notExists(userDir)) {
            Files.createDirectories(userDir);
        }

        String filenameBase = String.format("%s_%s", dateDir, userId);
        Path imagePath;
        int counter = 0;

        do {
            String filename = (counter == 0) ? filenameBase : filenameBase + counter;
            imagePath = userDir.resolve(filename + ".webp");
            counter++;
        } while (Files.exists(imagePath));

        Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }
}
