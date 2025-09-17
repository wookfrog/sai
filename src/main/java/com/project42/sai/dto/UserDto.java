package com.project42.sai.dto;

import java.time.LocalDate;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Alias("userDto")  // MyBatis에서 DTO 인식명으로 등록
@Data
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String providerId;
    private String provider;     // "local", "kakao", "google", "naver"
    private String nickname;
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}
