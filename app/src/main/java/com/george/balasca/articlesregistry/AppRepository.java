package com.george.balasca.articlesregistry;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.george.balasca.articlesregistry.data.AppDatabase;
import com.george.balasca.articlesregistry.data.Article;
import com.george.balasca.articlesregistry.data.ArticleDao;
import com.george.balasca.articlesregistry.data.Headline;
import com.george.balasca.articlesregistry.data.HeadlineDao;
import com.george.balasca.articlesregistry.data.Multimedia;
import com.george.balasca.articlesregistry.data.MultimediaDao;

import java.util.List;

public class AppRepository {

    private ArticleDao mArticleDao;
    private MultimediaDao mMultimediaDao;
    private HeadlineDao mHeadlineDao;

    private LiveData<List<Article>> mAllArticles;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mArticleDao = db.getArticleDao();
        mMultimediaDao = db.getMultimediaDao();
        mHeadlineDao = db.getHeadlineDao();


        mAllArticles = mArticleDao.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
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
