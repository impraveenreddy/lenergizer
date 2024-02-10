package com.example.lenergizer.data;

import android.content.Context;

import com.example.lenergizer.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewsRepository {
    private Context context;

    public NewsRepository(Context context) {
        this.context = context;
    }

    public List<Article> getNewsFeed() {
        List<Article> newsList = new ArrayList<>();

        try {
            String json = loadJSONFromAsset("news_feed.json");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray articlesArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articlesArray.length(); i++) {
                JSONObject articleObject = articlesArray.getJSONObject(i);
                Article article = new Article();
                article.setPublishedAt(convertData(articleObject.getString("publishedAt")));
                article.setTitle(articleObject.getString("title"));
                article.setUrl(articleObject.getString("url"));
                if (articleObject.has("urlToImage")) {
                    article.setUrlToImage(articleObject.getString("urlToImage"));
                } else {
                    article.setUrlToImage("");
                }
                newsList.add(article);
            }

            // Sort the newsList in ascending order based on published date
            Collections.sort(newsList, sortByDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private Comparator<Article> sortByDate = new Comparator<Article>() {
        public int compare(Article s1, Article s2) {
            if (s2.getPublishedAt() == null) {
                return -1;
            }
            if (s1.getPublishedAt() == null) {
                return 1;
            }
            if (s1.getPublishedAt().equals(s2.getPublishedAt())) {
                return 0;
            }
            return s2.getPublishedAt().compareTo(s1.getPublishedAt());
        }
    };

    public String convertData(String originalDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd | hh:mm a");
        String formattedDate = null;
        Date date;

        try {
            date = inputFormat.parse(originalDate);
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
