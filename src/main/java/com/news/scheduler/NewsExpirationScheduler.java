package com.news.scheduler;

import com.news.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class NewsExpirationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NewsExpirationScheduler.class);

    @Autowired
    private NewsRepository newsRepository;

    // Run every day at midnight
    @Scheduled(cron = "0 */2 * * * ?")
    public void markExpiredNewsAsDeleted() {
        logger.info("Running method: markExpiredNewsAsDeleted");
        Date currentDate = new Date();
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        newsRepository.softDeleteExpiredNews(localDate);
    }
}
