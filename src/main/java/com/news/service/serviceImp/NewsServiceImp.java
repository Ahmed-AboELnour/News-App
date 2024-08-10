package com.news.service.serviceImp;

import com.news.entity.News;
import com.news.entity.User;
import com.news.exception.ResourceNotFoundException;
import com.news.model.NewsStatus;
import com.news.repository.NewsRepository;
import com.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NewsServiceImp implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public News createNews(News news) {
        news.setStatus(NewsStatus.PENDING);
        return newsRepository.save(news);
    }

    public News updateNews(Long id, News news) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));


        existingNews.setTitle(news.getTitle() == null ? existingNews.getTitle() : news.getTitle());
        existingNews.setTitleArabic(news.getTitleArabic() == null ? existingNews.getTitleArabic() : news.getTitleArabic());
        existingNews.setDescription(news.getDescription() == null ? existingNews.getDescription() : news.getDescription());
        existingNews.setDescriptionArabic(news.getDescriptionArabic() == null ? existingNews.getDescriptionArabic() : news.getDescriptionArabic());
        existingNews.setPublishDate(news.getPublishDate() == null ? existingNews.getPublishDate() : news.getPublishDate());
        existingNews.setStatus(NewsStatus.PENDING);
        existingNews.setImageUrl(news.getImageUrl() == null ? existingNews.getImageUrl() : news.getImageUrl());
        return newsRepository.save(existingNews);
    }

    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
        if (news.getStatus() == NewsStatus.PENDING) {
            newsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("News should get approval from Admin: " + id);
        }
    }

    public List<News> getAllNews() {
        return newsRepository.findByStatusAndSoftDeleted(NewsStatus.APPROVED,true);
    }

    public void approveNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
        news.setStatus(NewsStatus.APPROVED);
        newsRepository.save(news);
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void autoDeleteExpiredNews() {
        List<News> newsList = newsRepository.findByStatusAndSoftDeleted(NewsStatus.APPROVED,false);
        newsList.stream()
                .filter(news -> news.getPublishDate().isBefore(LocalDate.now()))
                .forEach(news -> news.setStatus(NewsStatus.DELETED));
        newsRepository.saveAll(newsList);
    }
}
