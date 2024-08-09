package com.news.entity;

import com.news.model.NewsStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String titleArabic;
    private String description;
    private String descriptionArabic;
    private LocalDate publishDate;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private NewsStatus status;
    private boolean softDeleted = false;

}
