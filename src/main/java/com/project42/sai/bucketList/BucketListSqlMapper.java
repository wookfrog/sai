package com.project42.sai.bucketList;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BucketListSqlMapper {
    void insertBucketItem(BucketListDto Dto);
    List<BucketListDto> selectBucketItemsByCoupleId(Long coupleId);
    BucketListDto selectBucketItemById(@Param("id") Long id);
    void deleteBucketItem(@Param("id") Long id);
}
