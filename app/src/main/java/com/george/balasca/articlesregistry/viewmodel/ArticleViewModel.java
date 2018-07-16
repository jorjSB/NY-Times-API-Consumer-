package com.george.balasca.articlesregistry.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.george.balasca.articlesregistry.AppRepository;
import com.george.balasca.articlesregistry.data.Article;
import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private AppRepository mAppRepository;
    private LiveData<List<Article>> mAllArticles;
    private LiveData<PagedList<ArticleWithHeadlineAndMultimedia>> mAllArticlesWithHeadlineAndMultimedia;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mAllArticles = mAppRepository.getAllArticles();
        mAllArticlesWithHeadlineAndMultimedia = mAppRepository.getArticleWithHeadlineAndMultimediaList();

    }

    // get all Articles
    public LiveData<List<Article>> getAllArticles() { return mAllArticles; }

    // get all Articles
    public LiveData<PagedList<ArticleWithHeadlineAndMultimedia>> getArticleWithHeadlineAndMultimediaList() { return mAllArticlesWithHeadlineAndMultimedia; }

    // insertArticle one Article
    public void insert(Article article) { mAppRepository.insertArticle(article); }


}
