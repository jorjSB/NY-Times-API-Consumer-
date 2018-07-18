package com.george.balasca.articlesregistry.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.george.balasca.articlesregistry.AppRepository;
import com.george.balasca.articlesregistry.data.ArticlesBoundaryCallback;
import com.george.balasca.articlesregistry.entities.Article;
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
//        mAllArticlesWithHeadlineAndMultimedia = mAppRepository.getArticleWithHeadlineAndMultimediaList();

        init();
    }

    public void init() {

        ArticlesBoundaryCallback boundaryCallback = new ArticlesBoundaryCallback();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10)
                        .build();

        mAllArticlesWithHeadlineAndMultimedia = (new LivePagedListBuilder(mAppRepository.getmArticleDao().getArticleWithHeadlineAndMultimediaList(), pagedListConfig))
                .setBoundaryCallback(boundaryCallback)
                .build();

    }

    // delete all Articles
    public void deleteAllArticles() { mAppRepository.deleteAllArticles(); }

    // get all Articles
    public LiveData<List<Article>> getAllArticles() { return mAllArticles; }

    // get all Articles
    public LiveData<PagedList<ArticleWithHeadlineAndMultimedia>> getArticleWithHeadlineAndMultimediaList() { return mAllArticlesWithHeadlineAndMultimedia; }

    // insertArticle one Article
    public void insert(Article article) { mAppRepository.insertArticle(article); }


}
