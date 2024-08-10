package com.news.repository;

import com.news.entity.News;
import com.news.model.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByStatusAndSoftDeleted(NewsStatus status,boolean softDeleted);

    @Modifying
    @Transactional
    @Query("UPDATE News n SET n.softDeleted = true WHERE n.publishDate < :currentDate")
    void softDeleteExpiredNews(LocalDate currentDate);
}