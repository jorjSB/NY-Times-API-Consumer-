package com.george.balasca.articlesregistry.api;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.george.balasca.articlesregistry.AppRepository;
import com.george.balasca.articlesregistry.entities.Article;
import com.george.balasca.articlesregistry.entities.Headline;
import com.george.balasca.articlesregistry.entities.Multimedia;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UpdaterService extends IntentService{

    private static final String TAG = "UpdaterService";

    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.george.balasca.articlesregistry.intent.action.STATE_CHANGE";
    public static final String EXTRA_LOADING
            = "com.george.balasca.articlesregistry.intent.extra.LOADING";

    public UpdaterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // check network
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            Log.w(TAG, "Not online, not refreshing.");
            return;
        }

        // broadcast start loading
        sendBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_LOADING, true));

        // get response from API
        NetUtils netUtils = new NetUtils();
        String response = netUtils.fetchJsonArray(intent.getIntExtra("page", 0));

        // if response: store into DB
        if(response != null)
            storeNewDataIntoDB(response);

        // broadcast finish loading
        sendBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_LOADING, false));

    }

    private void storeNewDataIntoDB(String data) {

        Log.d(TAG, data);

        // DB
        AppRepository mAppRepository = new AppRepository(getApplication());

        // get articles
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        JsonArray articleList = new JsonArray();
        if(jsonObject != null)
            articleList = jsonObject.getAsJsonObject("response").getAsJsonArray("docs");

        // parse each article from JsonArray into POJO's
        for (int i=0; i< articleList.size(); i++) {
            // Gson: map articles json to articles list array
            Article article = new Gson().fromJson(articleList.get(i), Article.class);

            if(article == null){
                Log.e(TAG, "ARTICLE IS NULL!?!");
                break;
            }

            // insert the Article into the DB
            mAppRepository.insertArticle(article);

            /* ****************** Multimedia ****************** */// get multimedia json array
            JsonArray multimediaJsonArray =  articleList.get(i).getAsJsonObject().getAsJsonArray("multimedia");

            // store Multimedia into DB
            Multimedia[] mmdArray = new Gson().fromJson(multimediaJsonArray, Multimedia[].class);
            // insert article's MMD into DB
            for (Multimedia mmd: mmdArray){
                // set FK (article's _id)
                mmd.setArticle_original_id(article.getUid());
                mAppRepository.insertMultimedia(mmd);
            }

            /* ****************** Headline(s) ****************** */
            JsonObject headlineJsonObject =  articleList.get(i).getAsJsonObject().getAsJsonObject("headline");
            // store Multimedia into DB
            Headline headlineObject = new Gson().fromJson(headlineJsonObject, Headline.class);
            headlineObject.setArticle_original_id(article.getUid());
            // insert article's Headline into DB
            mAppRepository.insertHeadline(headlineObject);

        }
    }
}
