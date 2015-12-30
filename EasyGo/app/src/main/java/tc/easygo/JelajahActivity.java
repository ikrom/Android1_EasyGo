package tc.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

import tc.easygo.models.PopularModel;

public class JelajahActivity extends AppCompatActivity {

    private ListView lvPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jelajah);

        //variable
        lvPopular = (ListView) findViewById(R.id.lvPopular);
        SearchView search = (SearchView)findViewById(R.id.sv_jelajah);


        //image universal loder
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        //JSON
        new JSONTask().execute("http://navits.esy.es/index.php/services/getjelajah");

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jelajah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public class JSONTask extends AsyncTask<String, String, List<PopularModel>> {

        @Override
        protected List<PopularModel> doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                //cekLog(String.valueOf(parentObject));
                JSONArray parentArray = parentObject.getJSONArray("data");


                List<PopularModel> popularModelList = new ArrayList<>();

                List<PopularModel> filteredPopularModelList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    PopularModel popularModel = new PopularModel();
                    popularModel.setId(finalObject.getInt("id"));
                    popularModel.setNama(finalObject.getString("nama"));
                    popularModel.setJenisWisata(finalObject.getString("jenis_wisata"));
                    popularModel.setJumlahReview(finalObject.getInt("jumlah_review"));
                    popularModel.setRataRating((float) finalObject.getDouble("rata_rating"));
                    popularModel.setGambar(finalObject.getString("gambar"));


                    //adding the final object in the list
                    popularModelList.add(popularModel);

                }
                return popularModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override

        protected void onPostExecute(List<PopularModel> result) {
            super.onPostExecute(result);
            PopularAdapter adapter = new PopularAdapter(getApplicationContext(), R.layout.row_jelajah, result);
            lvPopular.setAdapter(adapter);
            // TODO need to set data to the list
        }
    }

    public void cekLog(String iniCek) {
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

    public class PopularAdapter extends ArrayAdapter {

        private List<PopularModel> popularModelList;
        private int resource;
        private LayoutInflater inflater;

        public PopularAdapter(Context context, int resource, List<PopularModel> objects) {
            super(context, resource, objects);
            popularModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder=new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.ivPhoto= (ImageView)convertView.findViewById(R.id.iv_Photo);
                holder.tvNama = (TextView)convertView.findViewById(R.id.tv_nama);
                holder.tvJenisWisata= (TextView)convertView.findViewById(R.id.tv_JenisWisata);
                holder.rbRataRating= (RatingBar)convertView.findViewById(R.id.rb_RataRating);
                holder.tvJumlahReview= (TextView)convertView.findViewById(R.id.tv_JumlahReview);
                holder.rlJelajahAll = (RelativeLayout) convertView.findViewById(R.id.rl_JelajahAll);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }
            final ProgressBar pbJelajah = (ProgressBar)convertView.findViewById(R.id.pb_Jelajah);


            holder.rlJelajahAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(JelajahActivity.this, PopularDestinationDetailActivity.class);
                    PopularModel pmdl = popularModelList.get(position);
                    myIntent.putExtra(PopularDestinationDetailActivity.KEY_ITEM, pmdl);
                    startActivityForResult(myIntent, 0);
                }
            });

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(popularModelList.get(position).getGambar(), holder.ivPhoto, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pbJelajah.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    pbJelajah.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pbJelajah.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    pbJelajah.setVisibility(View.GONE);
                }
            });

            //tvId.setText("" + popularModelList.get(position).getId());
            holder.tvNama.setText(popularModelList.get(position).getNama());
            holder.tvJenisWisata.setText(popularModelList.get(position).getJenisWisata());
            holder.tvJumlahReview.setText(popularModelList.get(position).getJumlahReview() + " ulasan");

            //rating bar
            holder.rbRataRating.setRating(popularModelList.get(position).getRataRating());

            return convertView;
        }
        class ViewHolder{
            private ImageView ivPhoto;
            private TextView tvNama;
            private TextView tvJenisWisata;
            private RatingBar rbRataRating;
            private TextView tvJumlahReview;
            private RelativeLayout rlJelajahAll;
        }
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
