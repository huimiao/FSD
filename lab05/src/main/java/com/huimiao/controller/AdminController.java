package com.huimiao.controller;

import com.huimiao.common.util.ModelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @GetMapping("/")
    public String home(Model model) {
        ModelUtil.setModle(model);
        return "admin/index";
    }
}
