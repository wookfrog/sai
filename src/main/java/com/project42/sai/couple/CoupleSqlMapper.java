package com.project42.sai.couple;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project42.sai.dto.CoupleDto;

@Mapper
public interface CoupleSqlMapper {
      // 커플 INSERT
    void insertCouple(CoupleDto coupleDto);

    // 유저 ID로 커플 조회
    CoupleDto selectCoupleByUserId(Long userId);
    CoupleDto getCoupleById(@Param("id") Long id);
}
