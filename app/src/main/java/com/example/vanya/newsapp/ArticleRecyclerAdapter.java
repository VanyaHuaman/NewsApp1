package com.example.vanya.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>{

    private int mColor;
    private ArrayList<Article> mArticles;
    private Context mContext;

    public ArticleRecyclerAdapter( Context context,int color, ArrayList<Article> articles) {
        mColor = color;
        mArticles = articles;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article currentArticle = mArticles.get(position);
        holder.section.setText(currentArticle.getSectionName());
        mColor = findColor(currentArticle.getSectionName());
        holder.section.setBackgroundColor(mColor);
        holder.title.setText(currentArticle.getArticleTitle());
        holder.date.setText(currentArticle.getPublishDate());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri earthquakeUri = Uri.parse(currentArticle.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(websiteIntent);
            }
        });

    }

    @Override
    public int getItemCount() {


        Log.i("BUILDING:","mArticles Size: "+Integer.toString(mArticles.size()));
        return mArticles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout parentLayout;
        private TextView section;
        private TextView title;
        private TextView date;

        public ViewHolder(View itemView){
            super(itemView);
            parentLayout = itemView.findViewById(R.id.root_layout);
            title = itemView.findViewById(R.id.title_text_view);
            section = itemView.findViewById(R.id.section_text_view);
            date = itemView.findViewById(R.id.date_text_view);
        }

    }

    private int findColor(String section){


        switch(section){
            case "politics":
                return mContext.getColor(R.color.blue);
            case "business":
                return mContext.getColor(R.color.green);
            case "sports":
                return mContext.getColor(R.color.red);
            case "news":
                return mContext.getColor(R.color.orange);
            default:
                return mContext.getColor(R.color.red);
        }
    }

}
