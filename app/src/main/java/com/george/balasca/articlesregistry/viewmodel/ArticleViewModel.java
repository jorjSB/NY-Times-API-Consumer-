package com.george.balasca.articlesregistry.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.george.balasca.articlesregistry.AppRepository;
import com.george.balasca.articlesregistry.data.Article;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private LiveData<List<Article>> mAllArticles;


    public ArticleViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllArticles = mAppRepository.getAllArticles();
    }


    // get all Articles
    public LiveData<List<Article>> getAllArticles() { return mAllArticles; }

    // insertArticle one Article
    public void insert(Article article) { mAppRepository.insertArticle(article); }


}
