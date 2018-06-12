package com.example.vanya.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>>{

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int ARTICLE_LOADER_ID = 1;
    private RecyclerView mArticleRecycleView;
    private TextView mEmptyStateTextView;
    private ArticleRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArticleRecycleView = findViewById(R.id.list);

        mAdapter = new ArticleRecyclerAdapter(this,0,new ArrayList<Article>());

        mArticleRecycleView.setAdapter(mAdapter);

        mArticleRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mEmptyStateTextView = findViewById(R.id.empty);

        mProgressBar = findViewById(R.id.progress_bar);

        LoaderManager loaderManager = getLoaderManager();
        Log.e(LOG_TAG,"getLoaderManager Called");

        loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        Log.e(LOG_TAG,"initLoader Called");

    }


    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG,"On Create Loader called");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String section = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Log.i("BUILDING: ","Section: "+section);
        String userKey = "&api-key=ef9f2bb6-df78-4fd6-8c3e-be20492824f5";
        GUARDIAN_REQUEST_URL = GUARDIAN_REQUEST_URL+section+userKey;

        Log.i("BUILDING: ","URL: "+GUARDIAN_REQUEST_URL);

        return new ArticleLoader(this, GUARDIAN_REQUEST_URL);
    }




    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if(hasConnection(this)){
            mEmptyStateTextView.setText(R.string.no_articles);
            if (data != null && !data.isEmpty()) {

                ArrayList<Article> passArray = new ArrayList<Article>(data);

                String passArraySize = Integer.toString(passArray.size());
                Log.i("BUILDING:","passArray size: "+passArraySize);

                mAdapter = new ArticleRecyclerAdapter(this,0,passArray);
                mArticleRecycleView.setAdapter(mAdapter);
                mEmptyStateTextView.setVisibility(View.INVISIBLE);

            }else{
                mEmptyStateTextView.setVisibility(View.VISIBLE);
                mArticleRecycleView.setVisibility(View.GONE);
            }

        }else {
            mEmptyStateTextView.setText(R.string.no_connection);
        }


        Log.e(LOG_TAG,"on Load Finished Called");
        mProgressBar.setVisibility(View.GONE);


    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        Log.e(LOG_TAG,"on Loader reset called");

        mAdapter = new ArticleRecyclerAdapter(this,0,new ArrayList<Article>());
    }



    public boolean hasConnection(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
