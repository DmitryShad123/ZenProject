package com.luchkin.ZenBlog.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "anons_article")
    private String anons_article;
    @Column(name = "full_text")
    private String full_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "data_article")
    private Date data_article;
    @Column(name = "data_article_format")
    private String data_article_format;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    List<CommentArticle> comment_articles;

    public Article() {
    }

    public Article(Long id, String title, String anons_article, String full_text, User author, Date data_article, String data_article_format) {
        this.id = id;
        this.title = title;
        this.anons_article = anons_article;
        this.full_text = full_text;
        this.author = author;
        this.data_article = data_article;
        this.data_article_format = data_article_format;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons_article() {
        return anons_article;
    }

    public void setAnons_article(String anons_article) {
        this.anons_article = anons_article;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getData_article() {
        return data_article;
    }

    public void setData_article(Date data_article) {
        this.data_article = data_article;
    }

    public String getData_article_format() {
        return data_article_format;
    }

    public void setData_article_format(String data_article_format) {
        this.data_article_format = data_article_format;
    }

    public List<CommentArticle> getComment_articles() {
        return comment_articles;
    }

    public void setComment_articles(List<CommentArticle> comment_articles) {
        this.comment_articles = comment_articles;
    }
}
