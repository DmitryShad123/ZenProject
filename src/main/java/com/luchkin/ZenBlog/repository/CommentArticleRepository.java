package com.luchkin.ZenBlog.repository;

import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.CommentArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentArticleRepository extends JpaRepository<CommentArticle, Long> {
    List<CommentArticle> findByArticle(Article article);
}
