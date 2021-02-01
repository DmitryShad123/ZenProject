package com.luchkin.ZenBlog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPage {

    @GetMapping("/403")
    public String getPageError(){
        return "login/error-page";
    }
}
