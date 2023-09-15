package com.example.webfluxv2.service;

import com.example.webfluxv2.model.Article;
import com.example.webfluxv2.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArticleService implements IMPArticleService {

    private final ArticleRepository articleRepository;
    @Override
    public Mono<Article> saveArticle(@Validated Article article) {
        return articleRepository.save(article);
    }
    @Override
    public Flux<Article> findAllArticles() {
        return articleRepository.findAll().switchIfEmpty(Flux.empty());
    }
    @Override
    public Mono<Article> findOneArticle(Integer id) {
        return articleRepository.findById(id).switchIfEmpty(Mono.empty());
    }
    @Override
    public Flux<Article> findByAuthor(String author) {
        return articleRepository.findByAuthor(author);
    }
    @Override
    public Mono<Void> deleteArticle(Integer id) {
        return articleRepository.deleteById(id);
    }
}
