package com.example.webfluxv2.controller;

import com.example.webfluxv2.model.Article;
import com.example.webfluxv2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/findAll")
    public Flux<Article> getAllArticles() {
        return articleService.findAllArticles();
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Article>> createArticle(@RequestBody Article article) {
        return articleService.saveArticle(article)
                .map(savedArticle -> new ResponseEntity<>(savedArticle, HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Article>> getArticleById(@PathVariable(value = "id") Integer articleId) {
        return articleService.findOneArticle(articleId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound()
                        .build());
    }

    @GetMapping("/{author}")
    public Flux<ResponseEntity<Article>> getArticleByAuthor(@PathVariable String author) {
        return articleService.findByAuthor(author)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Article>> updateArticle(@PathVariable(value = "id") Integer articleId,
                                                       @RequestBody Article article) {
        return articleService.findOneArticle(articleId)
                .flatMap(existingArticle -> {
                    existingArticle.setTitle(article.getTitle());
                    existingArticle.setContent(article.getContent());
                    existingArticle.setAuthor(article.getAuthor());
                    existingArticle.setPublishedAt(article.getPublishedAt());
                    return articleService.saveArticle(existingArticle);
                })
                .map(updatedArticle -> new ResponseEntity<>(updatedArticle, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteArticle(@PathVariable(value = "id") Integer articleId) {
        return articleService.deleteArticle(articleId)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
    }
}
