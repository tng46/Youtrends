package com.example.santo.youtrends2.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.pojo.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by Santo on 26/04/2015.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Information> data = Collections.emptyList();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater inflater;
    private Context context;


    public DrawerAdapter(Context context, List<Information> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_HEADER){
            View view = inflater.inflate(R.layout.navigation_drawer_header, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }
        else
        {
            View view = inflater.inflate(R.layout.navigation_drawer_item, parent, false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return TYPE_HEADER;
        }
        else {
            return TYPE_ITEM;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  HeaderHolder) {}
        else {
            ItemHolder itemHolder = (ItemHolder)holder;
            Information current = data.get(position-1);
            itemHolder.title.setText(current.getTitle());
            itemHolder.icon.setImageResource(current.getIconId());
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);


        }
    }
}
