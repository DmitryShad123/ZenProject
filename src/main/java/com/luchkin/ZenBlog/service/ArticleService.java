package com.luchkin.ZenBlog.service;

import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.News;
import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article findById(Long id){
        return articleRepository.getOne(id);
    }

    public Article save(Article article, User user){
        article.setAuthor(user);
        articleRepository.save(article);
        return article;

    }

    public List<Article> findByAuthor(User user){
        return articleRepository.findByAuthor(user);
    }

    public void deliteById(Long id){
        articleRepository.deleteById(id);
    }

    public Article save(Article article){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date();
        article.setData_article(date);
        article.setData_article_format(formater.format(date));
        return articleRepository.save(article);
    }
}
