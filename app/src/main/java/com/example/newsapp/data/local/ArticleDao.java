package com.example.newsapp.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.models.Article;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //OnConflictStrategy: replace if already exists
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM articles")
    Flowable<List<Article>> getAllArticles();
}
