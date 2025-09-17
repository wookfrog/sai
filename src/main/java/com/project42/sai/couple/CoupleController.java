package com.project42.sai.couple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project42.sai.dto.UserDto;
import com.project42.sai.invite.InviteService;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class CoupleController {

    @Autowired
    InviteService inviteService;
    @Autowired
    CoupleService coupleService;

    // 초대코드 입력 페이지
    @GetMapping("/invite")
    public String invitePage() {
        
        return "couple/invitePage";
    }

    // 커플 매칭
    @PostMapping("couple/join")
    public String matchCouple(@RequestParam("code") String code, HttpSession session, Model model) {
        UserDto loginUser = (UserDto) SessionUtil.getLoginUser(session);
        try {
            coupleService.matchCouple(code, loginUser.getId(), loginUser.getGender());
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/home"; 
        }
    }

    // 내 초대코드 발급/조회 페이지
    @GetMapping("/invite/code")
    public String showInviteCodePage(HttpSession session, Model model){
        UserDto user = SessionUtil.getLoginUser(session);
        String couple = inviteService.getOrCreateInviteCode(user.getId()).getCode();
        model.addAttribute("inviteCode", couple);
        return "couple/inviteCodePage";
        
    }

}
// 커플 생성 페이지
// 깨진 커플 
