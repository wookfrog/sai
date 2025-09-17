package com.project42.sai.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private Long userId;
    private String type;
    private String title;
    private String message;
    private Long targetId;
    private Boolean isRead;
    private String linkUrl;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
}
