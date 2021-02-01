package com.luchkin.ZenBlog.controllers;

import com.luchkin.ZenBlog.model.News;
import com.luchkin.ZenBlog.service.NewsService;
import com.luchkin.ZenBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsControllers {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @GetMapping("/news")
    public String getPageNews(Model model){
        List<News> allNews = newsService.findAll();
        model.addAttribute("allNews", allNews);
        return "news/news";
    }

    @GetMapping("/news/add")
    public String getPageNewsAdd(Model model){
        model.addAttribute("news", new News());
        return "news/news-add";
    }

    @PostMapping("/news/add")
    public String pageNewsAdd(@ModelAttribute("news") News news,
                              Model model){


        if(news.getTitle().length() < 5 || news.getTitle().length() > 50 ||
        news.getAnons_news().length() < 5 || news.getAnons_news().length() > 100 ||
        news.getFull_text().length() < 15 || news.getFull_text().length() > 1500){
            if(news.getTitle().length() < 5 || news.getTitle().length() > 50){
                model.addAttribute("title", "Название не должно быть меньше 5 символов, и не долно быть больше 50");
            }
            if(news.getAnons_news().length() < 5 || news.getAnons_news().length() > 100){
                model.addAttribute("anons_news", "Анонс не должен быть меньше 5 символов, и не долно быть больше 100");
            }
            if(news.getFull_text().length() < 15 || news.getFull_text().length() > 1500){
                model.addAttribute("full_text", "Полный текст новости не должен быть меньше 15 символов, и не долно быть больше 1500");
            }
            return "news/news-add";
        }

        newsService.save(news);
        return "redirect:/news";
    }

    @GetMapping("/news/{id}")
    public String getPageNewsDetails(@PathVariable(value = "id") long id,
                                     Model model){

        News news = newsService.findById(id);
        model.addAttribute("news", news);

        return "news/news-details";
    }

    @GetMapping("/news/{id}/edit")
    public String getPageNewsEdit(@PathVariable(value = "id") long id,
                                  Model model){
        News news = newsService.findById(id);
        model.addAttribute("newsEdit", news);
        return "news/news-edit";
    }

    @PostMapping("/news/{id}/edit")
    public String pageNewsRemove(@ModelAttribute("newsEdit") News news,
                                 @PathVariable(value = "id") long id,
                                 Model model){

        if(news.getTitle().length() < 5 || news.getTitle().length() > 50 ||
                news.getAnons_news().length() < 5 || news.getAnons_news().length() > 100 ||
                news.getFull_text().length() < 15 || news.getFull_text().length() > 1500){
            if(news.getTitle().length() < 5 || news.getTitle().length() > 50){
                model.addAttribute("title", "Название не должно быть меньше 5 символов, и не долно быть больше 50");
            }
            if(news.getAnons_news().length() < 5 || news.getAnons_news().length() > 100){
                model.addAttribute("anons_news", "Анонс не должен быть меньше 5 символов, и не долно быть больше 100");
            }
            if(news.getFull_text().length() < 15 || news.getFull_text().length() > 1500){
                model.addAttribute("full_text", "Полный текст новости не должен быть меньше 15 символов, и не долно быть больше 1500");
            }
            System.out.println("123");
            return "news/news-edit";
        }

        newsService.save(news);
        return "redirect:/news";
    }



    @PostMapping("/news/{id}/remove")
    public String removeNews(@PathVariable(value = "id") long id,
                             Model model){
        newsService.deliteById(id);
        return "redirect:/news";
    }
}
