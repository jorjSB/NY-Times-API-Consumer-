package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "article",
        indices={
                @Index(value="uid"),
                @Index(value="original_id", unique = true)
        })
public class Article {

    @Ignore
    ArrayList<Multimedia> multimediaArrayList;

    @Ignore
    Headline headline;

    @Expose(deserialize = false)
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @Expose(deserialize = false)
    @ColumnInfo(name = "favourite")
    private Boolean favourite;

    // Json - API
    @SerializedName("web_url")
    @ColumnInfo(name = "web_url")
    private String web_url;

    @SerializedName("snippet")
    @ColumnInfo(name = "snippet")
    private String snippet;

    @SerializedName("source")
    @ColumnInfo(name = "source")
    private String source;

    @SerializedName("pub_date")
    @ColumnInfo(name = "pub_date")
    private String pub_date;

    @SerializedName("document_type")
    @ColumnInfo(name = "document_type")
    private String document_type;

    @SerializedName("_id")
    @NonNull
    @ColumnInfo(name = "original_id")
    private String original_id;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    @NonNull
    public String getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(@NonNull String original_id) {
        this.original_id = original_id;
    }

    public ArrayList<Multimedia> getMultimediaArrayList() {
        return multimediaArrayList;
    }

    public void setMultimediaArrayList(ArrayList<Multimedia> multimediaArrayList) {
        this.multimediaArrayList = multimediaArrayList;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }
}