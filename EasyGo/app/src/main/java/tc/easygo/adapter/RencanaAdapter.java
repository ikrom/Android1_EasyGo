package tc.easygo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import tc.easygo.R;
import tc.easygo.models.RencanaModel;

/**
 * Created by ikrom on 12/30/2015.
 */
public class RencanaAdapter  extends BaseAdapter {
    // params
    public static final int TYPE_WISATA = 0;
    public static final int TYPE_EVENT = 1;

    ArrayList<RencanaModel> listItem;
    Activity activity;

    public RencanaAdapter(Activity activity, ArrayList<RencanaModel> listItem){
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        return (listItem.get(position).getJenis_rencana().equals("wisata")) ? 0 : 1;
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
        int type = getItemViewType(position);
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(type == 0){
                view = inflater.inflate(R.layout.row_rencana_wisata, null);
                holder.tvNamaWisata = (TextView)view.findViewById(R.id.tv_NamaWisata);
                holder.tvTanggalWisata = (TextView)view.findViewById(R.id.tv_TanggalWisata);
                holder.ivFotoWisata = (ImageView)view.findViewById(R.id.iv_FotoWisata);

                final ProgressBar pbWisata = (ProgressBar)view.findViewById(R.id.pb_Wisata);

                // Then later, when you want to display image
                ImageLoader.getInstance().displayImage(listItem.get(position).getGambar_wisata(), holder.ivFotoWisata, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        pbWisata.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        pbWisata.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        pbWisata.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        pbWisata.setVisibility(View.GONE);
                    }
                });
            }
            else{
                view = inflater.inflate(R.layout.row_rencana_event, null);
                holder.tvNamaEvent = (TextView)view.findViewById(R.id.tv_NamaEvent);
                holder.tvCatatanEvent = (TextView)view.findViewById(R.id.tv_CatatanEvent);
                holder.tvTanggalEvent = (TextView)view.findViewById(R.id.tv_TanggalEvent);
                holder.tvWaktuEvent = (TextView)view.findViewById(R.id.tv_WaktuEvent);
                holder.ivFotoEvent = (ImageView)view.findViewById(R.id.iv_FotoEvent);

                final ProgressBar pbEvent = (ProgressBar)view.findViewById(R.id.pb_Event);

                // Then later, when you want to display image
                ImageLoader.getInstance().displayImage(listItem.get(position).getGambar_event(), holder.ivFotoEvent, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        pbEvent.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        pbEvent.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        pbEvent.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        pbEvent.setVisibility(View.GONE);
                    }
                });
            }
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        RencanaModel rencanaModel = (RencanaModel)getItem(position);
        if(type==0){
            holder.tvNamaWisata.setText(rencanaModel.getNama_wisata());
            holder.tvTanggalWisata.setText(rencanaModel.getTanggal());
            //holder.ivFotoWisata.setText(rencanaModel.getWaktu());
        }
        else{
            holder.tvNamaEvent.setText(rencanaModel.getNama_event());
            holder.tvCatatanEvent.setText(rencanaModel.getCatatan());
            holder.tvTanggalEvent.setText(rencanaModel.getTanggal());
            holder.tvWaktuEvent.setText(rencanaModel.getWaktu());
            //holder.ivFotoEvent.set(rencanaModel.getNama_wisata());
        }




        return view;
    }

    static class ViewHolder{
        TextView tvNamaEvent,tvCatatanEvent,tvTanggalEvent,tvWaktuEvent,tvTanggalWisata,tvNamaWisata;
        ImageView ivFotoEvent,ivFotoWisata;
    }
}