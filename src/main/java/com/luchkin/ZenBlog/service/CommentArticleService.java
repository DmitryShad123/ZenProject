package com.luchkin.ZenBlog.service;

import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.CommentArticle;
import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.repository.CommentArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentArticleService {

    @Autowired
    private CommentArticleRepository commentArticleRepository;

    public List<CommentArticle> findByAllCommentThisArticle(Article article){
        return commentArticleRepository.findByArticle(article);
    }

    public CommentArticle save(CommentArticle commentArticle, User user, Article article){
        commentArticle.setAuthor(user);
        commentArticle.setArticle(article);
        return commentArticleRepository.save(commentArticle);
    }
}
