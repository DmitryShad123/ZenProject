package com.luchkin.ZenBlog.controllers;


import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.service.ArticleService;
import com.luchkin.ZenBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/adminconfig")
    public String getPageAdmin(Model model){

        return "admin/admin";
    }

    @PostMapping("/adminconfig")
    public String pageAdmin(@RequestParam String request,
                            Model model){
        User user = userService.findByNickname(request);

        if(userService.findByNickname(request) == null){
            model.addAttribute("request_error", "Пользователь не найден");
        }

        model.addAttribute("user", user);
        return "admin/admin";
    }

    @PostMapping("/adminconfig/{id}/giveadmin")
    public String pageAdminGiveAdmin(@PathVariable (value = "id") long id){
        User user = userService.findById(id);
        user.setRole("ADMIN");
        userService.saveUserEditing(user);
        return "admin/admin";
    }

    @PostMapping("/adminconfig/{id}/giveauser")
    public String pageAdminGiveUser(@PathVariable (value = "id") long id){
        User user = userService.findById(id);
        user.setRole("USER");
        userService.saveUserEditing(user);
        return "admin/admin";
    }

    @PostMapping("/adminconfig/{id}/givemoder")
    public String pageAdminGiveModer(@PathVariable(value = "id") long id){
        User user = userService.findById(id);
        user.setRole("MODER");
        userService.saveUserEditing(user);
        return "admin/admin";
    }
}
