package com.example.vanya.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {




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
        List<Article> articles = QueryUtility.fetchArticleData(mUrl);
        return articles;
    }


}