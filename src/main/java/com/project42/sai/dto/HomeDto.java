package com.project42.sai.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class HomeDto {
  private Long id;
  private Long coupleId;
  private Long characterId;
  private String backgroundUrl;
  
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
