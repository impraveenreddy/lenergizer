package com.example.lenergizer.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lenergizer.model.Article;
import com.example.lenergizer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvPublishedDate.setText(article.getPublishedAt());
        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Picasso.get().load(article.getUrlToImage()).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(holder.ivImage);
        } else {
            Picasso.get().load(R.mipmap.loading).placeholder(R.mipmap.loading).error(R.mipmap.loading).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvPublishedDate;
        private ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
            tvPublishedDate = itemView.findViewById(R.id.textViewPublishedDate);
            ivImage = itemView.findViewById(R.id.imageViewNews);
        }
    }

    public void filterList(List<Article> filteredList) {
        articles = filteredList;
        notifyDataSetChanged();
    }
}
