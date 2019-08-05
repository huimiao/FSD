package com.huimiao.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Collection;

public class ModelUtil {
    public static void setModle(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) auth.getAuthorities();
        StringBuffer authorities = new StringBuffer();
        for (GrantedAuthority ga : grantedAuthorities) {
            authorities.append(ga.getAuthority() + ":");
        }
        model.addAttribute("role", authorities.toString());
        if (authorities.toString().indexOf("USER") != -1 || authorities.toString().indexOf("ADMIN") != -1) {
            model.addAttribute("logged", "true");
        } else {
            model.addAttribute("logged", "false");
        }
    }
}
