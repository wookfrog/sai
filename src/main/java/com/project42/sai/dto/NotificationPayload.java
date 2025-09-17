package com.project42.sai.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor   
@NoArgsConstructor    
public class NotificationPayload {
    private String type;
    private String title;
    private String message;
    private String linkUrl;
    private LocalDateTime createdAt;
}
