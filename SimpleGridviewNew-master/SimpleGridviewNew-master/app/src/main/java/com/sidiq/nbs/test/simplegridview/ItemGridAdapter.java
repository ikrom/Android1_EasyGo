package com.sidiq.nbs.test.simplegridview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sidiq on 27/08/2015.
 */
public class ItemGridAdapter extends BaseAdapter {
    String[] items;
    Activity activity;

    public ItemGridAdapter(Activity activity, String[] items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_grid, null);
            holder.imgItem = (ImageView)view.findViewById(R.id.item_img_grid);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        Picasso.with(activity).load(items[position]).placeholder(ContextCompat.getDrawable(activity, R.drawable.placeholder))
                .into(holder.imgItem);
        return view;
    }

    static class ViewHolder{
        ImageView imgItem;
    }
}
