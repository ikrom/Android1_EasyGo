package tc.easygo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by ikromaulia on 12/1/2015.
 */
/*public class TipsAdapter extends BaseAdapter {
    ArrayList<TipsModel> tipsItem;
    Activity activity;

    public TipsAdapter(Activity activity, ArrayList<TipsModel> tipsItem){
        this.activity=activity;
        this.tipsItem=tipsItem;
    }

    @Override
    public int getCount(){return tipsItem.size();}

    @Override
    public Object getItem(int position){return tipsItem.get(position);}

    @Override
    public long getItemId(int position){return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder holder = null;

        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_tips_item_list, null);
            holder.tvTips=(TextView)view.findViewById(R.id.tv_tips);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }

        TipsModel tips = (TipsModel)getItem(position);
        holder.tvTips.setText(tips.getText());

        return view;
    }
    static class ViewHolder{
        TextView tvTips;
    }
}*/
