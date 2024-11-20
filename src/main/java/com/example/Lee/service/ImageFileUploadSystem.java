package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ImageFileUploadSystem {

    private final String uploadDir = "/var/www/ptu/uploads";

    // 클라이언트에서 접근 가능한 Base URL을 환경설정에서 가져옴
    @Value("${client.base.url}")
    private String clientBaseUrl;

    public String saveImageFile(MultipartFile file, String userId) throws IOException {
        // 오늘 날짜로 디렉토리 생성
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path userDir = Paths.get(uploadDir, dateDir);

        // 디렉토리가 없으면 생성
        if (Files.notExists(userDir)) {
            Files.createDirectories(userDir);
        }

        // 파일 이름 생성
        String filenameBase = String.format("%s_%s", dateDir, userId);
        Path imagePath;
        int counter = 0;

        // 중복된 파일이 있는지 체크하고 이름 생성
        do {
            String filename = (counter == 0) ? filenameBase : filenameBase + counter;
            imagePath = userDir.resolve(filename + ".webp");
            counter++;
        } while (Files.exists(imagePath));

        // 실제 파일 저장
        Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        // 클라이언트에서 접근 가능한 경로 반환
        String relativePath = "/uploads/" + dateDir + "/" + imagePath.getFileName().toString();
        return clientBaseUrl + relativePath;
    }
}
