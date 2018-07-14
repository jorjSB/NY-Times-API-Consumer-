package com.george.balasca.articlesregistry.utils;

import android.util.Log;

import com.george.balasca.articlesregistry.secret.Keys;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetUtils {

    public static final String TAG = NetUtils.class.getSimpleName();
    public final static String BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    public void NetUtils(){
    }

    public static String fetchJsonArray() {
        String itemsJson = null;
        try {
            return  fetchPlainText(BASE_URL);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }
    }

    static String fetchPlainText(String urlString) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
        urlBuilder.addQueryParameter("api-key", Keys.NYT_API_KEY);


        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
