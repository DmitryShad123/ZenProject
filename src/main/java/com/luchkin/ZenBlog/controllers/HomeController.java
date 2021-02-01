package com.luchkin.ZenBlog.controllers;


import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.service.ArticleService;
import com.luchkin.ZenBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getPageHome(Model model){
        return "home";
    }



}
