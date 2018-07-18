package com.george.balasca.articlesregistry;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;
import android.util.Log;

import com.george.balasca.articlesregistry.db.AppDatabase;
import com.george.balasca.articlesregistry.entities.Article;
import com.george.balasca.articlesregistry.data.ArticleDao;
import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;
import com.george.balasca.articlesregistry.data.ArticlesBoundaryCallback;
import com.george.balasca.articlesregistry.entities.Headline;
import com.george.balasca.articlesregistry.data.HeadlineDao;
import com.george.balasca.articlesregistry.entities.Multimedia;
import com.george.balasca.articlesregistry.data.MultimediaDao;

import java.util.ArrayList;
import java.util.List;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

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

    }

    // live Articles
    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    // live Articles
    public void deleteAllArticles() {
        new deleteAllArticlesAsyncTask(mArticleDao).execute();
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

    Long insertedArticleId;
    // insertArticle Article
    public AsyncTask<Article, Void, Void> insertArticle(Article article) {
        return new insertAsyncTaskArticle(mArticleDao).execute(article);
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
            if(params.length > 0)
                mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    // Insert into the DB AsyncTask
    private static class deleteAllArticlesAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        deleteAllArticlesAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public ArticleDao getmArticleDao() {
        return mArticleDao;
    }
}
