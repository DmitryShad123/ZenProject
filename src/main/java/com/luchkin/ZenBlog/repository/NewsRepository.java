package com.luchkin.ZenBlog.repository;

import com.luchkin.ZenBlog.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
