package com.news.service;

import com.news.entity.News;

import java.util.List;

public interface NewsService {

     News createNews(News news);

    News updateNews(Long id, News news);

    void deleteNews(Long id);

    List<News> getAllNews();

    void approveNews(Long id);

    News getNewsById(Long id);

    void autoDeleteExpiredNews();
}
