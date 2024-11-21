package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ImageFileUploadSystem {

    private final String uploadDir = "/var/www/ptu/uploads";

    // 클라이언트에서 접근 가능한 Base URL을 환경설정에서 가져옴
    @Value("${client.base.url}")
    private String clientBaseUrl;

    /**
     * Base64 문자열을 디코딩하여 byte[]로 반환하는 메서드
     * @param base64Image Base64 인코딩된 이미지 문자열
     * @return 디코딩된 byte[] 데이터
     * @throws IllegalArgumentException 잘못된 Base64 문자열인 경우 예외 발생
     */
    public byte[] decodeBase64Image(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            throw new IllegalArgumentException("Invalid Base64 input");
        }

        // Base64 문자열에서 데이터 부분만 추출
        String[] parts = base64Image.split(",");
        String base64Data = parts.length > 1 ? parts[1] : parts[0];

        // Base64 디코딩 수행
        return Base64.getDecoder().decode(base64Data);
    }

    /**
     * 디코딩된 byte[] 데이터를 파일로 저장하는 메서드
     * 기존 MultipartFile 방식도 지원
     * @param file (Optional) MultipartFile 이미지 파일
     * @param imageBytes (Optional) Base64 디코딩된 byte[] 데이터
     * @param userId 사용자 ID
     * @return 저장된 이미지 경로 (클라이언트 접근용 URL)
     * @throws IOException 파일 저장 실패 시 발생
     */
    public String saveImageFile(MultipartFile file, byte[] imageBytes, String userId) throws IOException {
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
        if (file != null) {
            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING); // MultipartFile 처리
        } else if (imageBytes != null) {
            Files.write(imagePath, imageBytes); // Base64 디코딩된 byte[] 처리
        } else {
            throw new IllegalArgumentException("No valid image data provided");
        }

        // 저장된 파일의 상대 경로 생성
        String relativePath = "/uploads/" + dateDir + "/" + imagePath.getFileName().toString();

        // DB에 저장할 경로: 클라이언트가 접근 가능한 URL
        return clientBaseUrl + relativePath;
    }

    /**
     * 이미지 파일 삭제 메서드
     * @param imagePath 삭제할 파일 경로
     * @return 삭제 성공 여부
     */
    public boolean deleteImageFile(String imagePath) {
        try {
            // 이미지 경로가 유효한지 확인
            if (imagePath == null || imagePath.isEmpty()) {
                return false;
            }

            // 서버 내 실제 파일 경로 생성
            Path filePath = Paths.get(uploadDir, imagePath.replaceFirst("^/uploads/", ""));

            // 파일이 존재하는 경우 삭제
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true; // 성공적으로 삭제됨
            } else {
                System.out.println("파일이 존재하지 않습니다: " + filePath);
                return false; // 파일이 존재하지 않음
            }
        } catch (Exception e) {
            // 예외 처리
            System.err.println("이미지 파일 삭제 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
   
}
