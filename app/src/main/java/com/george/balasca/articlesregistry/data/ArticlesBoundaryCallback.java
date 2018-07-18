package com.george.balasca.articlesregistry.data;

import android.arch.paging.PagedList;
import android.util.Log;

public class ArticlesBoundaryCallback extends PagedList.BoundaryCallback<ArticleWithHeadlineAndMultimedia>{
    private final String TAG = ArticlesBoundaryCallback.class.getSimpleName();

    public void ArticlesBoundaryCallback(String query){

    }

    @Override
    public void onZeroItemsLoaded(){
        // fetch data from service
//        requestAndSaveData(query);
        Log.d(TAG , "onZeroItemsLoaded");

    }

    @Override
    public void onItemAtEndLoaded(ArticleWithHeadlineAndMultimedia article){
        // fetch data from service
//        requestAndSaveData(query);
        Log.d(TAG , "onItemAtEndLoaded " + article.headline.get(0).getHeadline_main());
    }


}
