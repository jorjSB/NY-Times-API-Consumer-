package com.george.balasca.articlesregistry.data;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.george.balasca.articlesregistry.AppRepository;
import com.george.balasca.articlesregistry.utils.NetUtils;
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
        String response = netUtils.fetchJsonArray();

        // if response: store into DB
        if(response != null)
            storeNewDataIntoDB(response);

        // broadcast finish loading
        sendBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_LOADING, false));

    }

    private void storeNewDataIntoDB(String data) {
        // DB
        AppRepository mAppRepository = new AppRepository(getApplication());

        // get articles
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        JsonArray articleList = jsonObject.getAsJsonObject("response").getAsJsonArray("docs");


        // Gson: map articles json to articles list array
        Article[] articleArrayList = new Gson().fromJson(articleList, Article[].class);
        for (Article article : articleArrayList){
            mAppRepository.insertArticle(article);
        }

        // parse Articles to ghet multimedia and headlines
        for (int i=0; i < articleList.size(); i++) {

            /* ****************** Multimedia ****************** */// get multimedia json array
            JsonArray multimediaJsonArray =  articleList.get(i).getAsJsonObject().getAsJsonArray("multimedia");

            // store Multimedia into DB
            Multimedia[] mmdArray = new Gson().fromJson(multimediaJsonArray, Multimedia[].class);
            for (Multimedia mmd: mmdArray){
                // set FK (article's _id)
                mmd.setArticle_original_id(articleArrayList[i].getOriginal_id());
                mAppRepository.insertMultimedia(mmd);
            }

            /* ****************** Headline(s) ****************** */
            JsonObject headlineJsonObject =  articleList.get(i).getAsJsonObject().getAsJsonObject("headline");
            // store Multimedia into DB
            Headline headlineObject = new Gson().fromJson(headlineJsonObject, Headline.class);
            headlineObject.setArticle_original_id(articleArrayList[i].getOriginal_id());
            mAppRepository.insertHeadline(headlineObject);
        }
    }
}
