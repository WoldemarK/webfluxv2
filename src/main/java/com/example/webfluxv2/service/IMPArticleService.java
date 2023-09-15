package com.example.webfluxv2.service;

import com.example.webfluxv2.model.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMPArticleService {
    Mono<Article> saveArticle(Article article);
    Flux<Article> findAllArticles();
    Mono<Article> findOneArticle(Integer id);
    Flux<Article> findByAuthor(String author);
    Mono<Void> deleteArticle(Integer id);
}
