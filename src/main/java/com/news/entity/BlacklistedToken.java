package com.news.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    @Column(nullable = false)
    private Date expiryDate;

    public BlacklistedToken(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public BlacklistedToken() {

    }
}