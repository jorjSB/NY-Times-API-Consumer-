package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.george.balasca.articlesregistry.entities.Headline;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface HeadlineDao {

    @Insert(onConflict = REPLACE)
    void insert(Headline headline);

    @Insert(onConflict = REPLACE)
    void update(Headline... headlines);

    @Delete
    void delete(Headline... headlines);

    @Query("SELECT * FROM headline")
    List<Headline> getAllHeadlines();

    @Query("SELECT * FROM headline WHERE article_original_id=:article_original_id LIMIT 1")
    Headline findHeadlineByArticleUid(final String article_original_id);

}
