package com.project42.sai.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.CoupleDto;
import com.project42.sai.dto.UserDto;
import com.project42.sai.users.UserService;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    CoupleService coupleService;
    @Autowired
    UserService userService;

    // 로그인 여부에 따라 홈화면 아니면 로그인 페이지로 이동
    @RequestMapping("/")
    public String root(HttpSession session) {
        UserDto user = SessionUtil.getLoginUser(session);
        if (user != null) {
            return "redirect:/home";
        }

        return "redirect:/login";
    }

    // 홈 페이지
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        UserDto user = SessionUtil.getLoginUser(session);
        CoupleDto couple = coupleService.getCoupleByUserId(user.getId());
        // 로그인 여부 확인
        if (user == null) {
            return "redirect:/login";

        }

        // 필수 정보 누락이면 정보 입력 페이지로 이동
        if (user.getBirth() == null || user.getGender() == null || user.getNickname() == null || user.getGender().isEmpty() || user.getNickname().isEmpty()) {
            return "user/profileSetup";
        }

        model.addAttribute("userId", user.getId());

        // 커플 여부 확인 
        if (couple != null) {
            model.addAttribute("matched", true);
            model.addAttribute("coupleName", user.getNickname());
            model.addAttribute("roomId", couple.getId());
        } else {
            model.addAttribute("notMatched", true);
        }

        return "common/home";  // ✅ 진짜 홈화면으로 분리
    }

}
