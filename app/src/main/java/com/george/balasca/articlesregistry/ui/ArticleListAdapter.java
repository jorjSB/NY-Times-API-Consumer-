package com.george.balasca.articlesregistry.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.balasca.articlesregistry.R;
import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;

public class ArticleListAdapter extends PagedListAdapter< ArticleWithHeadlineAndMultimedia , ArticleListAdapter.ArticleViewHolder> {


    public ArticleListAdapter() {
        super(DIFF_CALLBACK);
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.article_list_content, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        ArticleWithHeadlineAndMultimedia articleWithHeadlineAndMultimedia = getItem(position);
        if(articleWithHeadlineAndMultimedia != null) {
            holder.bindTo(articleWithHeadlineAndMultimedia);
        }
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView articleItemView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            articleItemView = itemView.findViewById(R.id.content);
        }

        public void bindTo(ArticleWithHeadlineAndMultimedia articleWithHeadlineAndMultimedia) {
            articleItemView.setText(articleWithHeadlineAndMultimedia.headline.get(0).getHeadline_main());
        }
    }


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
}
