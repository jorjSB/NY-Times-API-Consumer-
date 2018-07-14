package com.george.balasca.articlesregistry.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.balasca.articlesregistry.R;
import com.george.balasca.articlesregistry.data.Article;

import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView articleItemView;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            articleItemView = itemView.findViewById(R.id.content);
        }
    }

    private final LayoutInflater mInflater;
    private List<Article> mArticles; // Cached copy of words

    public ArticleListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.article_list_content, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        if (mArticles != null) {
            Article current = mArticles.get(position);
            holder.articleItemView.setText( current.getPub_date() );
        } else {
            // Covers the case of data not being ready yet.
            holder.articleItemView.setText("No Article");
        }
    }

    public void setArticles(List<Article> articles){
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
