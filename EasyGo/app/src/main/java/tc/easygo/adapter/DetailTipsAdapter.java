package tc.easygo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tc.easygo.R;
import tc.easygo.models.DetailsTipsModel;

/**
 * Created by ikrom on 12/29/2015.
 */
public class DetailTipsAdapter extends BaseAdapter {
    // params
    ArrayList<DetailsTipsModel> listItem;
    Activity activity;

    public DetailTipsAdapter(Activity activity, ArrayList<DetailsTipsModel> listItem){
        this.activity = activity;
        this.listItem = listItem;
    }

    //method ini akan menentukan seberapa banyak data yang akan ditampilkan didalam ListView
    //listItem.size() == jumlah data dalam object List yang ada
    @Override
    public int getCount() { return listItem.size();}

    //method ini untuk mengakses per-item objek dalam list
    @Override
    public Object getItem(int position) { return listItem.get(position);}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //method ini yang akan menampilkan baris per baris dari item yang ada di ListView
    //dengan menggunakan pattern ViewHolder untuk optimasi performa dari ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_details_tips, null);
            holder.tvDeskripsi = (TextView)view.findViewById(R.id.tv_detail_tips_item);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        DetailsTipsModel detailTips = (DetailsTipsModel)getItem(position);

        holder.tvDeskripsi.setText(detailTips.getDeskripsi());
        return view;
    }

    static class ViewHolder{
        TextView tvDeskripsi;
    }
}
