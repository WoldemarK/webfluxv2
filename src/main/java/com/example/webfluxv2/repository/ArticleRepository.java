package com.example.webfluxv2.repository;

import com.example.webfluxv2.model.Article;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ArticleRepository extends R2dbcRepository<Article, Integer> {
    @Query("{'author': ?0}")
    Flux<Article>findByAuthor(String author);
}
