package com.project42.sai.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DiaryImageDto {
  private Long id;
  private Long diaryId;
  private String imageUrl;
  private String originalFilename;
  
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
