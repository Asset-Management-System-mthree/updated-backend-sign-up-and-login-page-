package org.example.auth.service;

import org.example.auth.entity.NewsArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlphaVantageNewsData {

    @Autowired
    private NewsArticleService newsArticleService;

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    private static final String ALPHA_VANTAGE_NEWS_URL = "https://www.alphavantage.co/query?function=NEWS_SENTIMENT&apikey=";

    public void fetchAndStoreNewsData() {
        List<Map<String, Object>> apiResponse = fetchFromApi();

        List<NewsArticle> articles = new ArrayList<>();
        for (Map<String, Object> articleData : apiResponse) {
            NewsArticle article = new NewsArticle();
            article.setTitle((String) articleData.get("title"));
            article.setSummary((String) articleData.get("summary"));
            article.setImageUrl((String) articleData.get("imageUrl"));
            article.setSource((String) articleData.get("source"));
            articles.add(article);
        }

        newsArticleService.saveAllArticles(articles);
    }

    private List<Map<String, Object>> fetchFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> newsList = new ArrayList<>();

        try {
            String url = ALPHA_VANTAGE_NEWS_URL + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray newsArray = jsonResponse.optJSONArray("feed");

            if (newsArray != null) {
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject newsObject = newsArray.getJSONObject(i);
                    Map<String, Object> newsData = new HashMap<>();

                    newsData.put("title", newsObject.optString("title"));
                    newsData.put("summary", newsObject.optString("summary"));
                    newsData.put("imageUrl", newsObject.optString("banner_image"));
                    newsData.put("source", newsObject.optString("source"));

                    newsList.add(newsData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public List<NewsArticle> getFinancialNews() {
        return newsArticleService.getAllArticles();
    }
}
