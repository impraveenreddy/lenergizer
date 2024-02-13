package com.example.lenergizer;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import com.example.lenergizer.model.Article;
import com.example.lenergizer.presentation.NewsAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NewsAdapterUnitTest {

    @Test
    public void testFilterList() {
        // Mock data
        List<Article> articles = new ArrayList<>();
        Article article1 = new Article();
        article1.setTitle("Test Title 1");
        articles.add(article1);

        Article article2 = new Article();
        article2.setTitle("Test Title 2");
        articles.add(article2);

        NewsAdapter adapter = new NewsAdapter(Mockito.mock(Context.class), articles);

        // Call filterList method with filtered data
        List<Article> filteredList = new ArrayList<>();
        filteredList.add(article1);
        adapter.filterList(filteredList);

        // Check if the adapter's data is replaced with filtered list
        assertEquals(1, adapter.getItemCount());
        assertEquals("Test Title 1", adapter.articles.get(0).getTitle());
    }
}