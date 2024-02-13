import android.content.Context;

import com.example.lenergizer.data.NewsRepository;
import com.example.lenergizer.model.Article;
import com.example.lenergizer.presentation.MainActivity;
import com.example.lenergizer.presentation.NewsAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityUnitTest {

    @Mock
    Context context;

    @Mock
    NewsRepository newsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFilter() {
        // Mock data
        List<Article> originalArticles = new ArrayList<>();
        Article article1 = new Article();
        article1.setTitle("Test Title 1");
        originalArticles.add(article1);

        Article article2 = new Article();
        article2.setTitle("Test Title 2");
        originalArticles.add(article2);

        Mockito.when(newsRepository.getNewsFeed()).thenReturn(originalArticles);

        MainActivity activity = new MainActivity();
        activity.newsRepository = newsRepository;
        activity.originalArticles = originalArticles;

        // Call filter method
        activity.filter("Test");

        // Check if filtering is done correctly
        assertEquals(2, activity.newsAdapter.getItemCount()); // Both articles should be present in the list
    }
}