package com.example.vanya.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtility {


    private static final String LOG_TAG = QueryUtility.class.getSimpleName();

    public QueryUtility() {
    }

    public static List<Article> fetchArticleData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Article> articles = extractResponseFromJson(jsonResponse);

        return articles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Article> extractResponseFromJson(String articleJSON) {

        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        List<Article> articles = new ArrayList<Article>();

        try {

            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            JSONArray resultsArray = responseJSONObject.getJSONArray("results");


            for (int i = 0; i < resultsArray.length(); i++) {


                JSONObject currentArticle = resultsArray.getJSONObject(i);

                String section = currentArticle.getString("sectionId");
                String title = currentArticle.getString("webTitle");
                String date = currentArticle.getString("webPublicationDate");
                String url = currentArticle.getString("webUrl");
                String author = "No Author Found";


                JSONArray tagsArray = currentArticle.getJSONArray("tags");

                if(!tagsArray.isNull(0)) {
                    JSONObject tags = tagsArray.getJSONObject(0);


                    if (tags.getString("webTitle") != null) {
                        author = tags.getString("webTitle");
                        Log.i("BUILDER: ", "author: " + author);
                    }
                }
                Article article = new Article(section, title, date, url,author);

                articles.add(article);

            }

        } catch (JSONException e) {
            Log.e("QueryUtility", "Problem parsing the article JSON results", e);
        }

        return articles;
    }

}
