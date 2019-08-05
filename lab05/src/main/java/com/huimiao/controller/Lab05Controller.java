package com.huimiao.controller;

import com.huimiao.common.Captcha;
import com.huimiao.common.util.ModelUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.huimiao.common.Constants.CAPTCHA_VALUE;
import static com.huimiao.common.Constants.JWT_TOKEN_NAME;

@Controller
public class Lab05Controller {
    @GetMapping("/")
    public String home(Model model) {
        ModelUtil.setModle(model);
        return "index";
    }

    @GetMapping("/logoff")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        SecurityContextHolder.clearContext();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(JWT_TOKEN_NAME)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        model.addAttribute("role", "");
        model.addAttribute("logged", "false");
        return "index";
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
