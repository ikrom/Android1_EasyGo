package tc.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tc.easygo.models.TipsModel;

public class TipsActivity extends AppCompatActivity {

    private ListView lvTipsItem;
    private ArrayList<TipsModel> tipsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tips Travelling");

        //variabel
        lvTipsItem = (ListView)findViewById(R.id.lv_tips);
        tipsItem = new ArrayList<>();

        //JSON
        new JSONTask().execute("http://navits.esy.es/index.php/services/gettipstravelling");

    }
    public class JSONTask extends AsyncTask<String, String, List<TipsModel> > {

        @Override
        protected List<TipsModel> doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line;
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("data");


                List<TipsModel> tipsModelList = new ArrayList<>();

                for(int i = 0; i<parentArray.length();i++){
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    TipsModel tipsModel = new TipsModel();
                    tipsModel.setId(finalObject.getString("id"));
                    tipsModel.setDeskripsi(finalObject.getString("deskripsi"));

                    //adding the final object in the list
                    tipsModelList.add(tipsModel);

                }
                return tipsModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override

        protected void onPostExecute(List<TipsModel> result) {
            super.onPostExecute(result);
            TipsAdapter adapter = new TipsAdapter(getApplicationContext(), R.layout.row_tips, result);
            lvTipsItem.setAdapter(adapter);

            // TODO need to set data to the list

        }
    }
    public void cekLog(String iniCek){
        Log.d("asd", iniCek);

        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage(iniCek)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public class TipsAdapter extends ArrayAdapter {

        private List<TipsModel> tipsModelList;
        private int resource;
        private LayoutInflater inflater;
        public TipsAdapter(Context context, int resource, List<TipsModel> objects) {
            super(context, resource, objects);
            tipsModelList = objects;
            this.resource=resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder=new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.tvTips = (TextView)convertView.findViewById(R.id.tv_tips);
                holder.llTipsAll = (LinearLayout)convertView.findViewById(R.id.ll_TipsAll);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTips.setText("" + tipsModelList.get(position).getDeskripsi());

            holder.llTipsAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(TipsActivity.this,TipsItemDetail.class);
                    TipsModel tipsIntent = tipsModelList.get(position);
                    myIntent.putExtra("tips", tipsIntent);
                    startActivityForResult(myIntent, 0);
                }
            });

            return convertView;
        }

        class ViewHolder{
            private TextView tvTips;
            private LinearLayout llTipsAll;
        }
    }
}
