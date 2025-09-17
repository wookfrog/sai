package com.project42.sai.bucketList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.UserDto;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class BucketListController {
    private final BucketListService bucketListService; // 서비스 주입
    private final CoupleService coupleService;

    // 버킷리스트 출력
    @GetMapping("/bucket")
    public String listBucket(HttpSession session, Model model){
        UserDto loginUser = SessionUtil.getLoginUser(session);
        if(loginUser == null){
            return "redirect:/home";
        }
        Long coupleId = SessionUtil.getCoupleId(session);
        if (coupleId == null) {
            return "redirect:/home";
        }

        model.addAttribute("list", bucketListService.getBucketItemsByCoupleId(coupleId));
        return "bucket/list";
    }

    // 버킷리스트 등록 페이지
    @GetMapping("/bucket/new")
    public String newBucketForm(HttpSession session){
        UserDto loginUser = SessionUtil.getLoginUser(session);
        if(loginUser == null){
            return "redirect:/login";
        }

        Long coupleId = SessionUtil.getCoupleId(session);
        if(coupleId == null){
            return "redirect:/home";
        }

        return "bucket/new";

    }

    //  버킷리스트 등록
    @PostMapping("/bucket")
    public String createBucket(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session) {
        
        // 로그인 정보 확인
        UserDto loginUser = (UserDto) SessionUtil.getLoginUser(session);
        if(loginUser == null){
            return "redirect:/login";
        }
        Long coupleId = SessionUtil.getCoupleId(session);
        if(coupleId == null){
            return "redirect:/home";
        }

        bucketListService.createBucketItem(coupleId, title, content, loginUser.getId());

        return "redirect:/bucket";
    }

    // 삭제 
    @PostMapping("/bucket/{id}/delete")
    public String deleteBucket(@PathVariable("id") Long id, HttpSession session){
        UserDto loginUser = SessionUtil.getLoginUser(session);
        if(loginUser == null)return "redirect:/login";

        Long coupleId = SessionUtil.getCoupleId(session);
        if (coupleId==null) return "redirect:/home";
        
        bucketListService.deleteBucketItem(id, coupleId);
        return "redirect:/bucket";
    } 
}
