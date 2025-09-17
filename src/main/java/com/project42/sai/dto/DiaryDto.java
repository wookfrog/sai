package com.project42.sai.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DiaryDto {
  private Long id;
  private Long coupleId;
  private Long ddayId;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate targetDate;

  private Boolean isDuplicated;
  
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
