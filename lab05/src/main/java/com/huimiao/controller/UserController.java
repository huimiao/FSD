package com.huimiao.controller;

import com.huimiao.common.util.ModelUtil;
import com.huimiao.model.User;
import com.huimiao.service.UserService;
import com.huimiao.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.huimiao.common.Constants.CAPTCHA_VALUE;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String register(HttpSession session, HttpServletResponse response, @RequestBody @Validated UserVo userVo) throws IOException {
        if (!userVo.getCaptcha().equalsIgnoreCase((String) session.getAttribute(CAPTCHA_VALUE)) ||
                userVo.getPassword().equalsIgnoreCase(userVo.getRepeatPassword())) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Check your password and capcha value");

            return null;
        }

        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setFirst_name(userVo.getFirst_name());
        user.setLast_name(userVo.getLast_name());
        user.setEmail(userVo.getEmail());
        userService.saveUser(user);

        return "OK";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/tutorials")
    public String tutorials(Model model) {
        ModelUtil.setModle(model);
        return "tutorials";
    }

    @PutMapping("/account")
    @ResponseBody
    public String account(HttpSession session, HttpServletResponse response, @RequestBody @Validated UserVo userVo, Model model) throws IOException {
        if (!userVo.getCaptcha().equalsIgnoreCase((String) session.getAttribute(CAPTCHA_VALUE)) ||
                userVo.getPassword().equalsIgnoreCase(userVo.getRepeatPassword())) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Check your password and capcha value");

            return null;
        }
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(userVo.getPassword());
        user.setFirst_name(userVo.getFirst_name());
        user.setLast_name(userVo.getLast_name());
        user.setEmail(userVo.getEmail());
        userService.updateUser(user);

        return "OK";
    }

    @GetMapping("/account")
    public String account(Model model) {
        ModelUtil.setModle(model);
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("user", userService.getUserDetail(username));

        return "account";
    }
}
