package com.george.balasca.articlesregistry.data;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Article.class, Multimedia.class, Headline.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private final static String TAG = AppDatabase.class.getSimpleName();
    private static AppDatabase INSTANCE;

    public abstract ArticleDao getArticleDao();
    public abstract MultimediaDao getMultimediaDao();
    public abstract HeadlineDao getHeadlineDao();


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ArticleDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.getArticleDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            Log.d(TAG, "reached");

//            mDao.deleteAllArticlesExceptFavourites();
            mDao.deleteAll();

            Article article = new Article();
            article.setSource("Hello");
            article.setOriginal_id("ttttt");
            article.setFavourite(false);
            mDao.insert(article);

            article = new Article();
            article.setSource("World");
            article.setOriginal_id("qqqq");
            article.setFavourite(true);
            mDao.insert(article);

            return null;
        }
    }

}
