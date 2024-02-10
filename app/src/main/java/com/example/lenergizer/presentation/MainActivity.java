package com.example.lenergizer.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lenergizer.presentation.NewsAdapter;
import com.example.lenergizer.data.NewsRepository;
import com.example.lenergizer.model.Article;
import com.example.lenergizer.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsRepository newsRepository;
    private List<Article> originalArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRepository = new NewsRepository(this);
        originalArticles = newsRepository.getNewsFeed();
        newsAdapter = new NewsAdapter(this, originalArticles);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return true;
    }

    private void filter(String query) {
        List<Article> filteredList = new ArrayList<>();
        for (Article article : originalArticles) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(article);
            }
        }
        newsAdapter.filterList(filteredList);
    }
}
