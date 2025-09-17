package com.project42.sai.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScheduleDto {
  private Long id;
  private Long coupleId;
  private Long userId;
  private ScheduleType scheduleType;
  private String color;
  private String title;
  private Boolean isAllDay;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;

  private AlarmOption alarmOption;
  private String locationName;
  private BigDecimal latitude;
  private BigDecimal logitude;
  private RepeatType repeatType;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime repeatEndDate;

  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public enum ScheduleType {
    MAN, WOMAN, BOTH
  }

  public enum AlarmOption {
    NONE, AT_TIME, BEFORE_10_MINUTE, BEFORE_1_HOUR, BEFORE_1_DAY
  }

  public enum RepeatType {
    NONE, DAILY, WEEKLY, MONTHLY, YEARLY  
  }
}
