package com.project42.sai.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.CoupleDto;
import com.project42.sai.dto.UserDto;
import com.project42.sai.users.UserService;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

// 로그인 로그아웃 관련 처리
@Controller
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    CoupleService coupleService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    // 로그인 요청
    @PostMapping("/login")
    public String loginPage(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        // 관리자 로그인 세션
        // AdminDto admin = userService.adminLogin(loginId, password);
        // if (admin != null) {
        //     SessionUtil.setLoginAdmin(session, admin);
        //     return "redirect:/admin/dashboard";
        // }
        // 유저 로그인 세션

        UserDto user = userService.login(loginId, password);
        if (user == null) {
            System.out.println("로그인 실패");
            return "redirect:/login";
        }
            SessionUtil.setLoginUser(session, user);
            // 커플 정보 세션에 저장
            CoupleDto couple = coupleService.getCoupleByUserId(user.getId());

            if (couple == null) {
                return "redirect:/home";
            } 
                Long coupleId = couple.getId();
                SessionUtil.setCoupleId(session, coupleId);

                return "redirect:/home";
            
        

    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/SNSlogin")
    public String snsLoginForm(HttpSession session, Model model) {

        return "user/snsRegisterPage";
    }

    @GetMapping("/oauth2/google/callback")
    public String googleCallback(@RequestParam("code") String code, HttpSession session) {
        String accessToken = authService.getGoogleAccessToken(code);

        Map<String, Object> googleUser = authService.getGoogleUserInfo(accessToken);

        String providerId = (String) googleUser.get("id");       // 또는 "sub"
        String email = (String) googleUser.get("email");
        String name = (String) googleUser.get("name");

        UserDto user = userService.processSocialUser("google", providerId, email, name);

        SessionUtil.setLoginUser(session, user);

        CoupleDto couple = coupleService.getCoupleByUserId(user.getId());

        if (couple == null) {
            return "redirect:/invite/code";
        } else {

            Long coupleId = couple.getId();
            SessionUtil.setCoupleId(session, coupleId);

            return "redirect:/home";
        }

    }

}
