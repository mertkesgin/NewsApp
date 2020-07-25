package com.example.newsapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedViewHolder> {

    private List<Article> articleList;
    private Context mContext;

    public SavedNewsAdapter(List<Article> articleList, Context mContext) {
        this.articleList = articleList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("article",article);
                Navigation.findNavController(v).navigate(R.id.action_savedNewsFragment_to_detailsFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class SavedViewHolder extends RecyclerView.ViewHolder{

        TextView title, desc, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        CardView cardView;

        public SavedViewHolder(@NonNull View itemView) {
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
