package com.project42.sai.video;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/video")
public class VideoCallController {

    @GetMapping("/test")
    public String videoCallTestPage(@RequestParam("roomId") String roomId,@RequestParam("userId") String userId, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("roomId", roomId);
        return "video/videoCallPage"; 
    }

}
