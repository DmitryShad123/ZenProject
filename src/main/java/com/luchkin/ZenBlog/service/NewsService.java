package com.luchkin.ZenBlog.service;

import com.luchkin.ZenBlog.model.News;
import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserService userService;

    public News findById(Long id){
        return newsRepository.getOne(id);
    }

    public List<News> findAll(){
        return newsRepository.findAll();
    }

    public News save(News news){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date();
        news.setData_news(date);
        news.setData_news_format(formater.format(date));
        return newsRepository.save(news);
    }

    public void deliteById(Long id){
        newsRepository.deleteById(id);
    }


}
