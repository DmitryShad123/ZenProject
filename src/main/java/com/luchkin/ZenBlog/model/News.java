package com.luchkin.ZenBlog.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "anons_news")
    private String anons_news;
    @Column(name = "full_text")
    private String full_text;
    @Column(name = "data_news")
    private Date data_news;
    @Column(name = "data_news_format")
    private String data_news_format;
    //Разобраться как делать формат даты и времени, без перевода ниже перечисленных в строку, для хранения в БД формата Data.

    public News(){
    }

    public News(String title, String anons_news, String full_text, Date data_news, String data_news_format) {
        this.title = title;
        this.anons_news = anons_news;
        this.full_text = full_text;
        this.data_news = data_news;
        this.data_news_format = data_news_format;
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

    public String getAnons_news() {
        return anons_news;
    }

    public void setAnons_news(String anons_news) {
        this.anons_news = anons_news;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public Date getData_news() {
        return data_news;
    }

    public void setData_news(Date data_news) {
        this.data_news = data_news;
    }

    public String getData_news_format() {
        return data_news_format;
    }

    public void setData_news_format(String data_news_format) {
        this.data_news_format = data_news_format;
    }
}

