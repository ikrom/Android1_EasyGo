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
import tc.easygo.TipsModel;
import tc.easygo.models.FasilitasModel;

/**
 * Created by ikromaulia on 12/17/2015.
 */
public class FasilitasAdapter extends BaseAdapter {
    ArrayList<FasilitasModel> fasilitasItem;
    Activity activity;

    public FasilitasAdapter(Activity activity, ArrayList<FasilitasModel> fasilitasItem){
        this.activity=activity;
        this.fasilitasItem=fasilitasItem;
    }

    @Override
    public int getCount(){return fasilitasItem.size();}

    @Override
    public Object getItem(int position){return fasilitasItem.get(position);}

    @Override
    public long getItemId(int position){return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder holder = null;

        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_fasilitas, null);
            holder.tvFasilitas=(TextView)view.findViewById(R.id.tv_Fasilitas);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }

        FasilitasModel fasilitas = (FasilitasModel)getItem(position);
        holder.tvFasilitas.setText(fasilitas.getTextFasilitas());

        return view;
    }
    static class ViewHolder{
        TextView tvFasilitas;
    }
}
