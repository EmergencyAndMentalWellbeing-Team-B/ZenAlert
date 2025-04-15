package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import com.example.repository.NewsRepository; // Add this
import com.example.model.NewsArticle;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final WebClient webClient;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        this.webClient = WebClient.create("https://newsapi.org/v2/everything");
    }

    public List<NewsArticle> getUserNews(String username) {
        return newsRepository.findByUsername(username);
    }

    public void saveNews(NewsArticle article) {
        newsRepository.save(article);
    }

    public String fetchNewsFromAPI(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", query)
                        .queryParam("apiKey", "REPLACE_THIS_WITH_YOUR_KEY") // Replace with your NewsAPI key fromm newsapi.org
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
