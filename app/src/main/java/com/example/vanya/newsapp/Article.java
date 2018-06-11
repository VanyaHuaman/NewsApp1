package com.example.vanya.newsapp;

public class Article {

    private String mTitle;
    private String mSectionName;
    private String mDate;
    private String mUrl;

    public Article(String sectionName, String title, String date, String url) {

        mSectionName = sectionName;
        mTitle = title;
        mDate = formatDate(date);
        mUrl = url;
    }

    public String formatDate(String raw){
        String date;

        if (raw.contains("T")) {

            String[] parts = raw.split("T");

            date = parts[0];
        }else{
            date = raw;
        }
     return date;
    }


    public String getArticleTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }


    public String getPublishDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
