package com.sociallogin.demo.controller;

import com.sociallogin.demo.config.auth.PrincipalDetails;
import com.sociallogin.demo.enums.RoleType;
import com.sociallogin.demo.model.User;
import com.sociallogin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({ "", "/" })
    public ModelAndView index(@AuthenticationPrincipal PrincipalDetails principal) {
        ModelAndView mav = new ModelAndView();
        if (principal != null) {
            User user = principal.getUser();
            Map<String, String> model = new HashMap<>();
            model.put("provider", user.getProvider());
            model.put("name", user.getUsername());
            model.put("gender", user.getGender());
            model.put("email", user.getEmail());
            model.put("role", user.getRole().name());
            mav.addObject("model", model);
        }
        mav.setViewName("Index");
        return mav;
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "어드민 페이지입니다.";
    }

    @Secured("ROLE_MANAGER")
    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "매니저 페이지입니다.";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(User user) {
        System.out.println(user);
        user.setRole(RoleType.ROLE_USER);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입 잘 되어 있음
        return "redirect:/";
    }
}
