package tc.easygo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tc.easygo.R;
import tc.easygo.models.GalleryAllModel;
import tc.easygo.models.RencanaModel;

/**
 * Created by ikrom on 12/31/2015.
 */
public class GalleryAllAdapter extends BaseAdapter {// params
    ArrayList<GalleryAllModel> listItem;
    Activity activity;

    public GalleryAllAdapter(Activity activity, ArrayList<GalleryAllModel> listItem){
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
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
            view = inflater.inflate(R.layout.row_grid, null);
            holder.imgItem = (ImageView)view.findViewById(R.id.item_img_grid);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        ImageLoader.getInstance().displayImage(listItem.get(position).getGambar(), holder.imgItem);
        /*Picasso.with(activity).load(listItem.get(position).placeholder(ContextCompat.getDrawable(activity, R.drawable.placeholder))
                .into(holder.imgItem);*/
        return view;
    }

    static class ViewHolder{
        ImageView imgItem;
    }
}