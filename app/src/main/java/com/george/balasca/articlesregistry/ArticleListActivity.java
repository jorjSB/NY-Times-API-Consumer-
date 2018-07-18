package com.george.balasca.articlesregistry;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.george.balasca.articlesregistry.data.ArticleWithHeadlineAndMultimedia;
import com.george.balasca.articlesregistry.api.UpdaterService;
import com.george.balasca.articlesregistry.ui.ArticleListAdapter;
import com.george.balasca.articlesregistry.viewmodel.ArticleViewModel;

/**
 * An activity representing a list of Articles. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArticleListActivity extends AppCompatActivity {

    private static final String TAG = ArticleListActivity.class.getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ArticleViewModel mArticleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fetchData(1);

            }
        });

        if (findViewById(R.id.article_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.article_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


//        fetchData(0);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData(0);
            }
        }, 4000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                fetchData(1);
//            }
//        }, 8000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver,
                new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    private boolean mIsLoading = false;

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                mIsLoading = intent.getBooleanExtra(UpdaterService.EXTRA_LOADING, false);
                Log.d(TAG, "Loading ?? " + mIsLoading);
            }
        }
    };

    private void fetchData(int page) {
        Intent i = new Intent(this, UpdaterService.class);
        i.putExtra("page", page);
        startService(i);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        // recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        final ArticleListAdapter adapter = new ArticleListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        mArticleViewModel.deleteAllArticles();

        mArticleViewModel.getArticleWithHeadlineAndMultimediaList().observe(this, new Observer<PagedList<ArticleWithHeadlineAndMultimedia>>() {
            @Override
            public void onChanged(@Nullable PagedList<ArticleWithHeadlineAndMultimedia> articleWithHeadlineAndMultimedia) {
                for (ArticleWithHeadlineAndMultimedia art :  articleWithHeadlineAndMultimedia) {
                    if(art != null && art.headline != null){
                        // Log.d(TAG, "\n" + ">>>>>>>>>>>>>>>>>>>>>>>>" + art.headline.get(0).getHeadline_main() + "\n");
                         }
                    }
                adapter.submitList(articleWithHeadlineAndMultimedia);
            }
        });

        recyclerView.setAdapter(adapter);
    }

}
