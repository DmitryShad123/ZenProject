package com.luchkin.ZenBlog.controllers;

import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PersonalAccountController {

    @Autowired
    public UserService userService;

    @GetMapping("/account")
    public String getaccount(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());

        if(auth.getName() == "anonymousUser"){
            return "redirect:/login";
        }

/*        if(user.getRole() == "MODER" || user.getRole() == "ADMIN"){
            if(user.getRole() == "MODER"){
                model.addAttribute("error_moder");
            }
            if(user.getRole() == "ADMIN"){
                model.addAttribute("error_admin");
            }
        }*/

        model.addAttribute("user", user);
        return "login/personal-account";
    }

    @PostMapping("/account/login")
    public String getLogin(){
        return "redirect:/login";
    }
    @PostMapping("/account/registration")
    public String getRegistration(){
        return "redirect:/registration";
    }

    @PostMapping("/account/remove")
    public String accountRemove(@PathVariable(value = "id") long id){
        userService.deliteById(id);
        return "redirect:/home";
    }

    @GetMapping("/account/edit")
    public String getAccountEdit(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("user", user);
        return "login/login-edit";
    }

    @PostMapping("/account/edit")
    public String pageRegistration(@ModelAttribute("user") User user, Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user1 = userService.findByEmail(auth.getName());
        user.setId(user1.getId());
        user.setRole(user1.getRole());
        System.out.println(user.getRole());


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
            return "login/login-edit";
        }
        userService.saveUserEditing(user);
        return "redirect:/home";
    }

}
