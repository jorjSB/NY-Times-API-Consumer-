package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.george.balasca.articlesregistry.entities.Article;
import com.george.balasca.articlesregistry.entities.Headline;
import com.george.balasca.articlesregistry.entities.Multimedia;

import java.util.List;

public class ArticleWithHeadlineAndMultimedia {

    @Embedded
    public Article article;

    @Relation(parentColumn = "_id",
            entityColumn = "article_original_id") public List<Headline> headline;

    @Relation(parentColumn = "_id",
            entityColumn = "article_original_id", entity = Multimedia.class) public List<Multimedia> multimediaList;



    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        ArticleWithHeadlineAndMultimedia article = (ArticleWithHeadlineAndMultimedia) obj;

        return article.article == this.article && article.article.getWeb_url() == this.article.getWeb_url();
    }

}
