package com.luchkin.ZenBlog.repository;

import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.CommentArticle;
import com.luchkin.ZenBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(User user);
}
