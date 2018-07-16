package com.george.balasca.articlesregistry.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class ArticleWithHeadlineAndMultimedia {

    @Embedded
    public Article article;

    @Relation(parentColumn = "original_id",
            entityColumn = "article_original_id") public List<Headline> headline;

    @Relation(parentColumn = "original_id",
            entityColumn = "article_original_id", entity = Multimedia.class) public List<Multimedia> multimediaList;

    public static DiffUtil.ItemCallback<ArticleWithHeadlineAndMultimedia> DIFF_CALLBACK = new DiffUtil.ItemCallback<ArticleWithHeadlineAndMultimedia>() {
        @Override
        public boolean areItemsTheSame(@NonNull ArticleWithHeadlineAndMultimedia oldItem, @NonNull ArticleWithHeadlineAndMultimedia newItem) {
            return oldItem.article.getUid() == newItem.article.getUid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ArticleWithHeadlineAndMultimedia oldItem, @NonNull ArticleWithHeadlineAndMultimedia newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        ArticleWithHeadlineAndMultimedia article = (ArticleWithHeadlineAndMultimedia) obj;

        return article.article.getUid() == this.article.getUid() && article.article.getOriginal_id() == this.article.getOriginal_id();
    }
}
