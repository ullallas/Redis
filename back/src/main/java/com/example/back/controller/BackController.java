package com.example.back.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BackController {
  @GetMapping("/")
  public String hello() {
    return "hello";
  }

  // @GetMapping("/api/about")
  // public String about(@RequestParam String inputValue) throws Exception {
  // StringBuilder answer = new StringBuilder();

  // for (int i = 0; i < inputValue.length(); i++) {
  // if (inputValue.charAt(i) == '2') {
  // answer.append("0");
  // } else if (inputValue.charAt(i) == '0') {
  // answer.append("5");
  // } else if (inputValue.charAt(i) == '5') {
  // answer.append("2");
  // }
  // }

  // return answer.toString();
  // }
  @PostMapping("/api/about")
  public String about(@RequestBody Map<String, String> requestData) {
    String inputValue = requestData.get("inputValue"); // 요청 본문에서 데이터 추출

    StringBuilder answer = new StringBuilder();
    for (int i = 0; i < inputValue.length(); i++) {
      if (inputValue.charAt(i) == '2') {
        answer.append("0");
      } else if (inputValue.charAt(i) == '0') {
        answer.append("5");
      } else if (inputValue.charAt(i) == '5') {
        answer.append("2");
      }
    }

    return answer.toString();
  }
}
