package com.example.santo.youtrends2.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.activity.VideoPlayerActivity;
import com.example.santo.youtrends2.app.network.VolleySingleton;
import com.example.santo.youtrends2.app.pojo.Trend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class TrendsAdapter extends RecyclerView.Adapter<ViewHolderTrend>  {

    private ArrayList<Trend> listTrends = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int previousPosition=0;
    private Context mContext;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ImageView imageView;


    public TrendsAdapter(Context context){
        this.mContext = context;


        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getmInstance();
        imageLoader = volleySingleton.getImageLoader();
        //imageView = new ImageView(mContext);

    }

    public void setTrendList(ArrayList<Trend> listTrends){
        this.listTrends = listTrends;

        notifyItemRangeChanged(0, listTrends.size());
        //notifyDataSetChanged();
    }


    @Override
    public ViewHolderTrend onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_trend_list_item, parent, false);

        ViewHolderTrend viewHolder = new ViewHolderTrend(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderTrend holder, int position) {
        final Trend currentTrend = listTrends.get(position);
        holder.trendTitle.setText(currentTrend.getTitle());

        //holder.trendId.setText(currentTrend.getId());
        //img = (ImageView)activity.findViewById(R.id.trendImage);
        String trendDifference;
        int imageId=-1;
        if (currentTrend.getTrendDifference()<-100) {
            trendDifference = "";
            imageId = R.drawable.new_trend;

        }
        else if (currentTrend.getTrendDifference()<0) {
            trendDifference = "" + currentTrend.getTrendDifference();
            imageId = R.drawable.negative_trend;
        }
        else if (currentTrend.getTrendDifference()==0) {
            trendDifference = "";
            imageId = R.drawable.stable_trend;
        }
        else {
            trendDifference = "+" + currentTrend.getTrendDifference();
            imageId = R.drawable.positive_trend;
        }
        holder.trendImage.setImageResource(imageId);
        holder.trendViewDifference.setText(trendDifference);
        holder.trendPosition.setText (currentTrend.getPosition());
        String urlThumb = currentTrend.getUrlThumb();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra("youtubeTitle", currentTrend.getTitle());
                intent.putExtra("youtubeId", currentTrend.getId());
                intent.putExtra("youtubePosition", currentTrend.getPosition());
                intent.putExtra("youtubeUrlThumb", currentTrend.getUrlThumb());

                String formatttedDate = dateFormat.format(currentTrend.getPublishedAt());

                intent.putExtra("youtubeDate", formatttedDate);
                intent.putExtra("youtubeDescription", currentTrend.getDescription());
                intent.putExtra("youtubeViewCount", currentTrend.getViewCount());
                intent.putExtra("youtubeLikeCount", currentTrend.getLikeCount());
                intent.putExtra("youtubeDislikeCount", currentTrend.getDislikeCount());
                intent.putExtra("youtubeCategory", currentTrend.getCategoryId());
                intent.putExtra("youtubeRegion", currentTrend.getRegion());
                //TODO:completare
                mContext.startActivity(intent);
            }
        });
        if (urlThumb!=null) {
            imageLoader.get(urlThumb, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.trendThumb.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
       /* if (position==previousPosition)
        {
            AnimationUtils.animateSunblind(holder, true);
        }
        else {
            AnimationUtils.animateSunblind(holder, false);
        }
        previousPosition = position;*/
    }

    @Override
    public int getItemCount() {
        return listTrends.size();
    }



}
