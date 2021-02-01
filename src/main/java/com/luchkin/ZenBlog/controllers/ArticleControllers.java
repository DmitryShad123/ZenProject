package com.luchkin.ZenBlog.controllers;

import com.luchkin.ZenBlog.model.Article;
import com.luchkin.ZenBlog.model.CommentArticle;
import com.luchkin.ZenBlog.model.News;
import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.service.ArticleService;
import com.luchkin.ZenBlog.service.CommentArticleService;
import com.luchkin.ZenBlog.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleControllers {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentArticleService commentArticleService;

    @GetMapping("/article")
    public String getPageArticle(Model model){
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "article/article";
    }

    @PostMapping("/article")
    public String getHome(@RequestParam String nickname,
                          Model model){

        if(userService.findByNickname(nickname) == null){
            model.addAttribute("error_nickname", "Статьи написанные пользователем с таким никнеймом не найдены");
            List<Article> articles = articleService.findAll();
            model.addAttribute("articles", articles);
            return "article/article";
        }

        User user = userService.findByNickname(nickname);
        List<Article> request = articleService.findByAuthor(user);
        model.addAttribute("request", request);

        return "article/article";
    }

    @GetMapping("/article/add")
    public String getPageArticlePage(Model model){
        model.addAttribute("article", new Article());
        return "article/article-add";
    }

    @PostMapping("/article/add")
    public String pageArticlePage(@ModelAttribute("article") Article article, Model model){

        if(article.getTitle().length() < 5 || article.getTitle().length() > 50 ||
                article.getAnons_article().length() < 5 || article.getAnons_article().length() > 100 ||
                article.getFull_text().length() < 15 || article.getFull_text().length() > 1500){
            if(article.getTitle().length() < 5 || article.getTitle().length() > 50){
                model.addAttribute("title", "Название не должно быть меньше 5 символов, и не долно быть больше 50");
            }
            if(article.getAnons_article().length() < 5 || article.getAnons_article().length() > 100){
                model.addAttribute("anons_article", "Анонс не должен быть меньше 5 символов, и не долно быть больше 100");
            }
            if(article.getFull_text().length() < 15 || article.getFull_text().length() > 1500){
                model.addAttribute("full_text", "Полный текст статьи не должен быть меньше 15 символов, и не долно быть больше 1500");
            }
            return "article/article-add";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());

        articleService.save(article, user);
        return "redirect:/article";
    }

    @GetMapping("/article/{id}")
    public String getPageArticleDetails(@PathVariable(value = "id") long id,
                                     Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());

        Article article = articleService.findById(id);

        List<CommentArticle> allCommentThisArticle = commentArticleService.findByAllCommentThisArticle(article);
        model.addAttribute("allCommentThisArticle", allCommentThisArticle);


        if(auth.getName() == "anonymousUser") {
            model.addAttribute("error", "Удалить/редактировать нельзя");
            model.addAttribute("article", article);
            return "article/article-details";
        }
        if (user.getId() != article.getAuthor().getId()) {
            model.addAttribute("error", "Удалить/редактировать нельзя");
            model.addAttribute("article", article);
            return "article/article-details";
        }

        model.addAttribute("article", article);
        return "article/article-details";
    }

    @GetMapping("/article/{id}/edit")
    private String pageEditArticle(@PathVariable(value = "id") long id, Model model){
        Article article = articleService.findById(id);
        model.addAttribute("articleEdit", article);
        return "article/article-edit";
    }

    @PostMapping("/article/{id}/edit")
    private String editArticle(@ModelAttribute("articleEdit") Article article,
                               Model model){

        if(article.getTitle().length() < 5 || article.getTitle().length() > 50 ||
                article.getAnons_article().length() < 5 || article.getAnons_article().length() > 100 ||
                article.getFull_text().length() < 15 || article.getFull_text().length() > 1500) {
            if (article.getTitle().length() < 5 || article.getTitle().length() > 50) {
                model.addAttribute("title", "Название не должно быть меньше 5 символов, и не долно быть больше 50");
            }
            if (article.getAnons_article().length() < 5 || article.getAnons_article().length() > 100) {
                model.addAttribute("anons_article", "Анонс не должен быть меньше 5 символов, и не долно быть больше 100");
            }
            if (article.getFull_text().length() < 15 || article.getFull_text().length() > 1500) {
                model.addAttribute("full_text", "Полный текст статьи не должен быть меньше 15 символов, и не долно быть больше 1500");
            }
            return "article/article-edit";
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());
        articleService.save(article, user);
        return "redirect:/article";
    }




    @PostMapping("/article/{id}/remove")
    private String deliteArticle(@PathVariable(value = "id") long id, Model model){
        articleService.deliteById(id);
        return "redirect:/article";
    }

    @PostMapping("/article/{id}/comment")
    private String addComment(@RequestParam String comment, @PathVariable(value = "id") long id, Model model){
        Article article = articleService.findById(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getName();
        User user = userService.findByEmail(auth.getName());
        List<CommentArticle> allCommentThisArticle = commentArticleService.findByAllCommentThisArticle(article);

        if(comment.length() < 3 || comment.length() > 20 ||
                auth.getName() == "anonymousUser" ||
                user.getId() != article.getAuthor().getId()){

            if(auth.getName() == "anonymousUser"){
                model.addAttribute("error", "Удалить/редактировать нельзя");
                model.addAttribute("error_comment","Пользователь не авторизован");
            }

            if(comment.length() < 3 || comment.length() > 20) {
                model.addAttribute("error_comment_length","Комментарий должен быть не меньше 3 и не больше 20 символов");
            }

            if(auth.getName() != "anonymousUser"){
                if(user.getId() != article.getAuthor().getId()){

                    CommentArticle commentArticle = new CommentArticle();
                    commentArticle.setComment(comment);
                    commentArticleService.save(commentArticle, user, article);

                    model.addAttribute("error", "Удалить/редактировать нельзя");
                }

            }
            model.addAttribute("article", article);
            model.addAttribute("allCommentThisArticle", allCommentThisArticle);
            return "article/article-details";
        }

        model.addAttribute("article", article);

        CommentArticle commentArticle = new CommentArticle();
        commentArticle.setComment(comment);
        commentArticleService.save(commentArticle, user, article);

        model.addAttribute("allCommentThisArticle", allCommentThisArticle);

        return "article/article-details";
    }
}
