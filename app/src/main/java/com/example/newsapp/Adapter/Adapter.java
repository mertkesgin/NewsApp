package com.example.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newsapp.Activities.DetailActivity;
import com.example.newsapp.R;
import com.example.newsapp.Models.Article;
import com.example.newsapp.Utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Article> articleList;
    private Context mContext;

    public Adapter(List<Article> articles, Context context) {
        this.articleList = articles;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article article = articleList.get(position);

        holder.title.setText(article.getTitle());
        holder.desc.setText(article.getDescription());
        holder.author.setText(article.getAuthor());
        holder.time.setText(" \u2022" + Utils.DateToTimeFormat(article.getPublishedAt()));
        holder.published_at.setText(Utils.DateFormat(article.getPublishedAt()));
        holder.source.setText(article.getSource().getName());


        String imageUrl = article.getUrlToImage();

        Picasso.get().load(imageUrl).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("title",article.getTitle());
                intent.putExtra("author",article.getAuthor());
                intent.putExtra("date",article.getPublishedAt());
                intent.putExtra("urlToImage",article.getUrlToImage());
                intent.putExtra("url",article.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, desc, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }
    }
}
