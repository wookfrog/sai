package com.project42.sai.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DdayDto {
  private Long id;
  private Long coupleId;
  private String title;
  
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate baseDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
