package org.example.auth.service;


import org.example.auth.entity.NewsArticle;
import org.example.auth.repo.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsArticleService {
    @Autowired
    private NewsArticleRepository newsArticleRepository;

    public List<NewsArticle> getAllArticles() {
        return newsArticleRepository.findAll();
    }

    public void saveAllArticles(List<NewsArticle> articles) {
        newsArticleRepository.saveAll(articles);
    }
}
