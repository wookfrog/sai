package com.project42.sai.dto;

import lombok.Data;

@Data
public class QuestionDto {
  private Long id;
  private String content;
  private Boolean isDeleted;
}
