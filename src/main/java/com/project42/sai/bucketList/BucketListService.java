package com.project42.sai.bucketList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketListService {

    private final BucketListSqlMapper bucketListSqlMapper;

    // 버킷리스트 입력
    public void createBucketItem(Long CoupleId, String title, String content, Long createBy) {
        BucketListDto bucketList = new BucketListDto();
        bucketList.setCoupleId(CoupleId);
        bucketList.setCreatedBy(createBy);
        bucketList.setTitle(title);
        bucketList.setContent(content);
        bucketListSqlMapper.insertBucketItem(bucketList);
    }

    // 버킷리스트 출력
    public List<BucketListDto> getBucketItemsByCoupleId(Long coupleId) {
        return bucketListSqlMapper.selectBucketItemsByCoupleId(coupleId);
    }

    // 버킷리스트 삭제
    @Transactional
    public void deleteBucketItem(Long id, Long coupleId) {
        BucketListDto item = bucketListSqlMapper.selectBucketItemById(id);
        if (item == null || !item.getCoupleId().equals(coupleId)) {
            throw new IllegalArgumentException("권한이 없거나 존재하지 않는 항목입니다.");
        }
        bucketListSqlMapper.deleteBucketItem(id);
    }
}
