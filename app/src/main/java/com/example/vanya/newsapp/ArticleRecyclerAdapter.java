package com.example.vanya.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        Article currentArticle = mArticles.get(position);
        holder.section.setText(currentArticle.getSectionName());
        holder.title.setText(currentArticle.getArticleTitle());
        holder.author.setText(currentArticle.getAuthor());
        holder.date.setText(currentArticle.getPublishDate());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout parentLayout;
        private TextView section;
        private TextView title;
        private TextView author;
        private TextView date;

        public ViewHolder(View itemView){
            super(itemView);
            parentLayout = itemView.findViewById(R.id.root_layout);
            section = itemView.findViewById(R.id.section_text_view);
            author = itemView.findViewById(R.id.author_text_view);
            date = itemView.findViewById(R.id.date_text_view);
        }

    }



}
