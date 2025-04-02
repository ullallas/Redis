package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
public class RedisController {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @GetMapping("/sss")
  public String getMethodName() {
    // redisTemplate.multi();
    try {
      redisTemplate.opsForValue().set("key1", "fucku");
      redisTemplate.opsForValue().set("key2", "쿠지같군");

      // redisTemplate.exec();
    } catch (Exception e) {
      // redisTemplate.discard();
    }
    return "OK2";
  }

  /*
   * curl -X POST "http://localhost:8080/set" -H "Content-Type: application/json"
   * -d "{'data':'haha'}"
   */
  @PostMapping("/set")
  public ResponseEntity<Void> login(@RequestBody Requesta request, final HttpServletRequest httpRequest) {
    final HttpSession session = httpRequest.getSession();
    session.setAttribute("data", request.getAaa());
    System.out.println("sssssssssss ===>>>> " + request.getAaa());
    session.setMaxInactiveInterval(3600);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get/{key}")
  public ResponseEntity<?> getValueFromKey(@PathVariable String key) {
    ValueOperations<String, String> vop = redisTemplate.opsForValue();
    String value = vop.get(key);
    return new ResponseEntity<>(value, HttpStatus.OK);
  }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Requesta {
  private String aaa;
}