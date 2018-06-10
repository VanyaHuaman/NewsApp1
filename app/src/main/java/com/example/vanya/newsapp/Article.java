package com.example.vanya.newsapp;

import android.support.v7.app.AppCompatActivity;

public class Article extends AppCompatActivity {

    String mTitle;
    String mSectionName;
    String mAuthor;
    String mDate;
    String mUrl;

    public Article(String title, String sectionName, String author, String date, String url) {
        mTitle = title;
        mSectionName = sectionName;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }


    public String getArticleTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
