package com.george.balasca.articlesregistry.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ArticleDao {

    @Insert(onConflict = REPLACE)
    void insert(Article article);

    @Insert(onConflict = REPLACE)
    void update(Article... repos);

    @Delete
    void delete(Article... articles);

    @Query("DELETE FROM article")
    void deleteAll();

    @Query("SELECT * FROM article ORDER BY pub_date ASC")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM article WHERE uid=:uid ORDER BY pub_date ASC")
    LiveData<List<Article>> findArticlesById(final String uid);

    @Query("DELETE FROM article WHERE favourite != 1")
    void deleteAllArticlesExceptFavourites();

}
