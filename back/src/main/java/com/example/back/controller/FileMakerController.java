package com.example.back.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class FileMakerController {

  @PostMapping("/fileReader")
  public String fileReader() {
    String filePath = "E:/pic/testFile.txt";
    File file = new File(filePath);

    if (file.exists()) {
      try {
        String content = Files.readString(Paths.get(filePath));
        return content;
      } catch (IOException e) {
        e.printStackTrace();
        return "파일을 읽는 중 오류 발생";
      }
    } else {
      return "파일 없어";
    }
  }

  @PostMapping("/fileMaker")
  public String fileMaker(@RequestBody Map<String, String> payload) {
    String filePath = "E:/pic/testFile1.txt";

    try {
      String data = "" +
          "id: winter\n" +
          "email: winter@mycompany.com\n" +
          "tel: 010-123-1234\n" +
          "message: " + payload.get("message");

      // Path 객체 생성
      Path path = Paths.get(filePath);

      // 파일 생성 및 데이터 저장
      Files.writeString(path, data, Charset.forName("UTF-8"));

      String content = Files.readString(Paths.get(filePath));

      return content;
    } catch (IOException e) {
      e.printStackTrace();
      return "파일을 읽는 중 오류 발생";
    }
  }

  @PostMapping("/fileDelete")
  public String fileDelete() {
    String filePath = "E:/pic/testFile1.txt";
    File file = new File(filePath);

    if (file.exists()) { // 파일 존재 여부 확인
      if (file.delete()) { // 파일 삭제 시도
        return "파일 삭제 성공: " + filePath;
      } else {
        return "파일 삭제 실패";
      }
    } else {
      return "파일이 존재하지 않음";
    }
  }

  private static final String UPLOAD_DIR = "uploads/";

  @PostMapping("/api/fileUpload")
  public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("파일이 없습니다.");
    }

    try {
      // 디렉토리 없으면 생성
      File directory = new File(UPLOAD_DIR);
      if (!directory.exists()) {
        directory.mkdirs();
      }

      // 파일 저장
      Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
      Files.write(filePath, file.getBytes());

      return ResponseEntity.ok("파일 업로드 성공: " + file.getOriginalFilename());
    } catch (IOException e) {
      return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
    }
  }
}
