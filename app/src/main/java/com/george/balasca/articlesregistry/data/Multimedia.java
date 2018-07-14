package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "multimedia",
        foreignKeys = @ForeignKey(entity = Article.class,
            parentColumns = "original_id",
            childColumns = "article_original_id",
            onDelete = CASCADE),
        indices = {@Index("article_original_id")})
public class Multimedia {

    @Expose(deserialize = false)
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @Expose(deserialize = false)
    @NonNull
    @ColumnInfo(name = "article_original_id")
    private String article_original_id;

    // Json - API
    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("subType")
    @ColumnInfo(name = "subtype")
    private String subtype;

    @SerializedName("url")
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("height")
    @ColumnInfo(name = "height")
    private String height;

    @SerializedName("width")
    @ColumnInfo(name = "width")
    private String width;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getArticle_original_id() {
        return article_original_id;
    }

    public void setArticle_original_id(@NonNull String article_original_id) {
        this.article_original_id = article_original_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
