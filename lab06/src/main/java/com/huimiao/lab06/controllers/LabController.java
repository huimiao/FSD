package com.huimiao.lab06.controllers;

import com.huimiao.lab06.common.Captcha;
import com.huimiao.lab06.common.util.ModelUtil;
import com.huimiao.lab06.model.User;
import com.huimiao.lab06.service.Lab06UserDetailsService;
import com.huimiao.lab06.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.huimiao.lab06.common.Constants.CAPTCHA_VALUE;

@Controller
@Slf4j
public class LabController {
    @Autowired
    private Lab06UserDetailsService userDetailService;

    @GetMapping("/")
    public String test() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> register(@RequestBody @Validated UserVo userVo) {
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setFirst_name(userVo.getFirst_name());
        user.setLast_name(userVo.getLast_name());
        user.setEmail(userVo.getEmail());

        userDetailService.saveUser(user);

        Map<String, String> r = new HashMap<>(1);
        r.put("result", "OK");
        return r;
    }

    @PostMapping("/account")
    @ResponseBody
    public Map<String, String> account(@RequestBody @Validated UserVo userVo) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(userVo.getPassword());
        user.setFirst_name(userVo.getFirst_name());
        user.setLast_name(userVo.getLast_name());
        user.setEmail(userVo.getEmail());

        userDetailService.updateUser(user);

        Map<String, String> r = new HashMap<>(1);
        r.put("result", "OK");
        return r;
    }

    @GetMapping("/account")
    public String account(Model model) {
        ModelUtil.setModle(model);
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("user", userDetailService.getUserDetail(username));
        return "account";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/tutorials")
    public String tutorials() {
        return "tutorials";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, HttpSession session, Model model) {
        AuthenticationException ex = (AuthenticationException) session
                .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        String errorMessage = (error != null && ex != null) ? ex.getMessage() : null;

        model.addAttribute("errorMessage", errorMessage);

        log.debug("error message is {}", errorMessage);
        return "login";
    }

    @GetMapping("/captcha")
    @ResponseBody
    public String imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = Captcha.getImageCode(80, 45);
        request.getSession().setAttribute(CAPTCHA_VALUE, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime", System.currentTimeMillis());

        OutputStream os = response.getOutputStream();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return null;
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }
}
