package com.news.repository;

import com.news.entity.BlacklistedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends CrudRepository<BlacklistedToken, String> {
    boolean existsByToken(String token);
}