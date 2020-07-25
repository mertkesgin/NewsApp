package com.example.newsapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "articles")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @SerializedName("source")
    @Expose
    @ColumnInfo(name = "source")
    private Source source;

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    private String author;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    private String publishedAt;

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
