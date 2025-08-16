package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 */
@Controller
@RequestMapping("/base")
public class indexController {

    /**
     * 跳转首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("username", "管理员");
        model.addAttribute("isAdmin", true);
        return "index";
    }
}
