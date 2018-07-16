package com.george.balasca.articlesregistry;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;

import com.george.balasca.articlesregistry.data.AppDatabase;
import com.george.balasca.articlesregistry.data.Article;
import com.george.balasca.articlesregistry.data.ArticleDao;
import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;
import com.george.balasca.articlesregistry.data.Headline;
import com.george.balasca.articlesregistry.data.HeadlineDao;
import com.george.balasca.articlesregistry.data.Multimedia;
import com.george.balasca.articlesregistry.data.MultimediaDao;

import java.util.ArrayList;
import java.util.List;

public class AppRepository {

    private ArticleDao mArticleDao;
    private MultimediaDao mMultimediaDao;
    private HeadlineDao mHeadlineDao;

    private LiveData<List<Article>> mAllArticles;
    private LiveData<PagedList<ArticleWithHeadlineAndMultimedia>> mAllArticlesWithHeadlineAndMultimedia;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mArticleDao = db.getArticleDao();
        mMultimediaDao = db.getMultimediaDao();
        mHeadlineDao = db.getHeadlineDao();


        mAllArticles = mArticleDao.getAllArticles();
        init();
    }

    public void init() {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(5)
                        .setPageSize(10).build();

        mAllArticlesWithHeadlineAndMultimedia = (new LivePagedListBuilder(mArticleDao.getArticleWithHeadlineAndMultimediaList(), pagedListConfig))
                .build();

    }

    // live Articles
    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    // live Articles
    public LiveData<PagedList<ArticleWithHeadlineAndMultimedia>> getArticleWithHeadlineAndMultimediaList() {
        return mAllArticlesWithHeadlineAndMultimedia;
    }

    // get Headline for Article
    public Headline getHeadlineForArticle(String original_id) {
        return  mHeadlineDao.findHeadlineByArticleUid(original_id);
    }

    // get Multimedia for Article
    public ArrayList<Multimedia> getMultimediaForArticle(String original_id) {
        return new ArrayList<Multimedia>( mMultimediaDao.findMultimediaByArticleUid(original_id) );
    }

    // insertArticle Article
    public void insertArticle(Article article) {
        new insertAsyncTaskArticle(mArticleDao).execute(article);
    }

    // insert Multimedia
    public void insertMultimedia(Multimedia multimedia) {
        new insertAsyncTaskMultimedia(mMultimediaDao).execute(multimedia);
    }

    // insert Headline
    public void insertHeadline(Headline headline) {
        new insertAsyncTaskHeadline(mHeadlineDao).execute(headline);
    }

    // Insert into the DB AsyncTask
    private static class insertAsyncTaskArticle extends AsyncTask<Article, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        insertAsyncTaskArticle(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // Insert into the DB AsyncTask
    private static class insertAsyncTaskMultimedia extends AsyncTask<Multimedia, Void, Void> {
        private MultimediaDao mAsyncTaskDao;

        insertAsyncTaskMultimedia(MultimediaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Multimedia... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // Insert into the DB AsyncTask
    private static class insertAsyncTaskHeadline extends AsyncTask<Headline, Void, Void> {
        private HeadlineDao mAsyncTaskDao;

        insertAsyncTaskHeadline(HeadlineDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Headline... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
