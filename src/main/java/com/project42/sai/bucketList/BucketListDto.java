package com.project42.sai.bucketList;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BucketListDto {

    private Long id;                // PK
    private Long coupleId;          // couple_id: 커플 식별자 (FK)
    private String title;           // 제목
    private String content;         // 내용
    private Long createdBy;         // 작성자 users.id (FK)
    private Boolean isComplete;     // 완료 여부 (TINYINT(1) -> Boolean 매핑)
    private LocalDateTime doneAt;   // 완료 시각 (완료 해제 시 null)
    private LocalDateTime createdAt; // 생성 시각
}
