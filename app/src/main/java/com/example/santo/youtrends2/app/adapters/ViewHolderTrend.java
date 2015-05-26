package com.example.santo.youtrends2.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santo.youtrends2.app.R;

/**
 * Created by Santo on 13/05/2015.
 */
class ViewHolderTrend extends  RecyclerView.ViewHolder {

    protected ImageView trendThumb;
    protected TextView trendTitle;
    protected TextView trendId;
    protected TextView trendPosition;
    protected TextView trendViewDifference;
    protected ImageView trendImage;


    public ViewHolderTrend(View itemView) {
        super(itemView);

        trendThumb = (ImageView)itemView.findViewById(R.id.trendThumbnail);
        //trendId = (TextView)itemView.findViewById(R.id.trendId);
        trendViewDifference = (TextView)itemView.findViewById(R.id.trendViewCount);
        trendTitle = (TextView)itemView.findViewById(R.id.trendTitle);
        trendImage = (ImageView)itemView.findViewById(R.id.trendImage);

        trendPosition = (TextView)itemView.findViewById(R.id.trendPosition);


    }
}