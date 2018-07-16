package com.george.balasca.articlesregistry.ui;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.balasca.articlesregistry.R;
import com.george.balasca.articlesregistry.data.Article;
import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;

import java.util.List;

public class ArticleListAdapter extends PagedListAdapter< ArticleWithHeadlineAndMultimedia ,ArticleListAdapter.ArticleViewHolder> {

    private LayoutInflater mInflater;
    private List<ArticleWithHeadlineAndMultimedia> mArticles; // Cached copy of words

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView articleItemView;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            articleItemView = itemView.findViewById(R.id.content);
        }

        public void bindTo(ArticleWithHeadlineAndMultimedia articleWithHeadlineAndMultimedia) {
            // sets the title
            if(articleWithHeadlineAndMultimedia.headline.size() == 1)
                articleItemView.setText( articleWithHeadlineAndMultimedia.headline.get(0).getHeadline_main());
            else
                articleItemView.setText("n/a");
        }
    }

    public ArticleListAdapter() {
        super(ArticleWithHeadlineAndMultimedia.DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View itemView = mInflater.inflate(R.layout.article_list_content, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        
        if (mArticles != null && mArticles.get(position) != null) {
            holder.bindTo(mArticles.get(position));

        } else {
            // Covers the case of data not being ready yet.
            holder.articleItemView.setText("No Article");
        }
    }

    public void setArticles(List<ArticleWithHeadlineAndMultimedia> articles){
        mArticles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mArticles != null)
            return mArticles.size();
        else return 0;
    }

}
