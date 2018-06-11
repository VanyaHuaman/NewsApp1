package com.example.vanya.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.vanya.newsapp.Article;
import com.example.vanya.newsapp.QueryUtility;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {


    private static final String LOG_TAG = ArticleLoader.class.getName();


    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Article> articles = QueryUtility.fetchArticleData(mUrl);
        return articles;
    }
}