package com.george.balasca.articlesregistry.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.george.balasca.articlesregistry.entities.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "headline",
        foreignKeys = @ForeignKey(entity = Article.class,
                parentColumns = "_id",
                childColumns = "article_original_id",
                onDelete = CASCADE),
        indices = {@Index("article_original_id")})
public class Headline {

    @Expose(deserialize = false)
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @Expose(deserialize = false)
    @NonNull
    @ColumnInfo(name = "article_original_id")
    private String article_original_id;

    // Json - API
    @SerializedName("main")
    @ColumnInfo(name = "headline_main")
    private String headline_main;

    @SerializedName("kicker")
    @ColumnInfo(name = "headline_content_kicker")
    private String headline_content_kicker;

    @SerializedName("print_headline")
    @ColumnInfo(name = "headline_print")
    private String headline_print;

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getArticle_original_id() {
        return article_original_id;
    }

    public void setArticle_original_id(@NonNull String article_original_id) {
        this.article_original_id = article_original_id;
    }

    public String getHeadline_main() {
        return headline_main;
    }

    public void setHeadline_main(String headline_main) {
        this.headline_main = headline_main;
    }

    public String getHeadline_content_kicker() {
        return headline_content_kicker;
    }

    public void setHeadline_content_kicker(String headline_content_kicker) {
        this.headline_content_kicker = headline_content_kicker;
    }

    public String getHeadline_print() {
        return headline_print;
    }

    public void setHeadline_print(String headline_print) {
        this.headline_print = headline_print;
    }
}