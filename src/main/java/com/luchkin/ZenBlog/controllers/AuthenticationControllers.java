package com.luchkin.ZenBlog.controllers;

import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationControllers {


    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getPageLogin(){
        return "login/login";
    }

    @GetMapping("/registration")
    public String getPageRegistration(Model model){
        model.addAttribute("user", new User());
        return "login/registration";
    }

    @PostMapping("/registration")
    public String pageRegistration(@ModelAttribute("user") User user,
                                   Model model){

        if(user.getFirst_name().length() < 2 || user.getFirst_name().length() > 16 ||
        user.getLast_name().length() < 3 || user.getLast_name().length() > 16 ||
        user.getEmail().length() < 6 || user.getEmail().length() > 25 ||
        userService.findByEmail(user.getEmail()) != null ||
        user.getNickname().length() < 2 || user.getNickname().length() > 16 ||
        userService.findByNickname(user.getNickname()) != null ||
        user.getPassword().length() < 8 || user.getPassword().length() > 25 ||
        !user.getPassword().equals(user.getChecking_password())){

            if(user.getFirst_name().length() < 2 || user.getFirst_name().length() > 16){
                model.addAttribute("first_name", "Введенное имя не должно быть меньше 2 и не больше 16 символов");
            }
            if(user.getLast_name().length() < 3 || user.getLast_name().length() > 16){
                model.addAttribute("last_name", "Введенная фамилия не должна быть меньше 3 и не больше 16 символов");
            }
            if(user.getEmail().length() < 6 || user.getEmail().length() > 25){
                model.addAttribute("email", "Введенный Email не должно быть меньше 6 и не больше 25 символов");
            }
            if(userService.findByEmail(user.getEmail()) != null){
                model.addAttribute("email", "Введенный Email уже зарегистрирован");
            }
            if(user.getNickname().length() < 2 || user.getNickname().length() > 16){
                model.addAttribute("nickname", "Введенное логин не должно быть меньше 2 и не больше 16 символов");
            }
            if(userService.findByNickname(user.getNickname()) != null){
                model.addAttribute("nickname", "Пользователь с таким никнеймом уже создан");
            }
            if(user.getPassword().length() < 8 || user.getPassword().length() > 25){
                model.addAttribute("password", "Введенное пароль не должно быть меньше 8 и не больше 25 символов");
            }
            if(!user.getPassword().equals(user.getChecking_password())){
                model.addAttribute("checking_password", "Введенные пароли не совпадают");
            }
            return "login/registration";
        }
        userService.saveUser(user);
        return "redirect:/home";
    }
}
