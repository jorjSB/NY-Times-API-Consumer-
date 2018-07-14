package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MultimediaDao {

    @Insert(onConflict = REPLACE)
    void insert(Multimedia multimedia);

    @Insert(onConflict = REPLACE)
    void update(Multimedia... multimedia);

    @Delete
    void delete(Multimedia... multimedia);

    @Query("SELECT * FROM multimedia")
    List<Multimedia> getAllMultimedia();

    @Query("SELECT * FROM multimedia WHERE article_original_id=:article_original_id LIMIT 1")
    List<Multimedia> findMultimediaByArticleUid(final String article_original_id);

}
