package com.project42.sai.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuthAuthorizeController {
    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.redirect-uri}")
    private String googleRedirectUri;

    @GetMapping("/oauth2/authorize/google")
    public String authorizeGoogle(){
        String URL = "http://accounts.google.com/o/oauth2/v2/auth"
        + "?client_id=" + googleClientId
        + "&redirect_uri=" + googleRedirectUri
        + "&response_type=code"
        + "&scope=email%20profile"
        + "&access_type=offline";
        return "redirect:" + URL;
    }

}
