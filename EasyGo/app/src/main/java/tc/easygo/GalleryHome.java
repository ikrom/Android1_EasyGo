package tc.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
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

import tc.easygo.adapter.ItemGridAdapter;
import tc.easygo.models.PopularModel;

public class GalleryHome extends AppCompatActivity {

    private StaggeredGridView staggeredGridView;
    private String[] items = new String[]{
            "https://s-media-cache-ak0.pinimg.com/736x/31/72/90/3172904c6591b127ac71b66b300a87f4.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/c4/93/d1/c493d196b1b4e0ff61d8cb4a13727916.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/54/a3/23/54a323dcafda85b7fda6883c00f2860a.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/80/d5/b8/80d5b8ccfef474f5c895400acdbea63b.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/0e/d7/7a/0ed77a3208f982a6f552fbf30f7d4a4c.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/7f/2e/79/7f2e790b6ebb34699b887181d7a6a3ed.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/fd/66/f0/fd66f045d95449469fa8b41b4b975edb.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/d7/57/ca/d757ca1df5224dc46633a42e3c34bb90.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/96/2a/d1/962ad10951a221f4ca26bb08e911da10.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/ac/89/c7/ac89c7aaef5754bfd5d7b4a698fdb6cc.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_home);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");

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
        //new JSONTask().execute("http://navits.esy.es/index.php/services/getallgallery");

        staggeredGridView = (StaggeredGridView)findViewById(R.id.gv_staggered);
        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(GalleryHome.this, items);
        staggeredGridView.setAdapter(itemGridAdapter);
        staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailImageActivity.toDetailImageActivity(GalleryHome.this, items[position]);
            }
        });

    }

    /*public class JSONTask extends AsyncTask<String, String, List<PopularModel> > {

        @Override
        protected List<PopularModel> doInBackground(String... params) {

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
                //cekLog(String.valueOf(parentObject));
                JSONArray parentArray = parentObject.getJSONArray("data");


                List<PopularModel> popularModelList = new ArrayList<>();

                for(int i = 0; i<parentArray.length();i++){
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

        protected void onPostExecute(List<PopularModel> result) {
            super.onPostExecute(result);
            PopularAdapter adapter = new PopularAdapter(getApplicationContext(), R.layout.row_popular, result);
            lvPopular.setAdapter(adapter);

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

    public class PopularAdapter extends ArrayAdapter {

        private List<PopularModel> popularModelList;
        private int resource;
        private LayoutInflater inflater;
        public PopularAdapter(Context context, int resource, List<PopularModel> objects) {
            super(context, resource, objects);
            popularModelList = objects;
            this.resource=resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder=new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.tvId = (TextView)convertView.findViewById(R.id.tv_IdPopular);
                holder.ivPhoto= (ImageView)convertView.findViewById(R.id.iv_Photo);
                holder.tvNama = (TextView)convertView.findViewById(R.id.tv_nama);
                holder.tvJenisWisata= (TextView)convertView.findViewById(R.id.tv_JenisWisata);
                holder.rbRataRating= (RatingBar)convertView.findViewById(R.id.rb_RataRating);
                holder.tvJumlahReview= (TextView)convertView.findViewById(R.id.tv_JumlahReview);
                holder.rlPopularAll = (RelativeLayout)convertView.findViewById(R.id.rl_PopularAll);
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }

            final ProgressBar pbPopular = (ProgressBar)convertView.findViewById(R.id.pb_popular);



            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(popularModelList.get(position).getGambar(), holder.ivPhoto, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pbPopular.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    pbPopular.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pbPopular.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    pbPopular.setVisibility(View.GONE);
                }
            });

            holder.tvId.setText("" + popularModelList.get(position).getId());
            holder.tvNama.setText(popularModelList.get(position).getNama());
            holder.tvJenisWisata.setText(popularModelList.get(position).getJenisWisata());
            holder.tvJumlahReview.setText(popularModelList.get(position).getJumlahReview() + " ulasan");

            //rating bar
            holder.rbRataRating.setRating(popularModelList.get(position).getRataRating());


            holder.rlPopularAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(PopularDestinationActivity.this, PopularDestinationDetailActivity.class);
                    PopularModel pmdl = popularModelList.get(position);
                    myIntent.putExtra(PopularDestinationDetailActivity.KEY_ITEM, pmdl);
                    startActivityForResult(myIntent, 0);
                }
            });

            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private ImageView ivPhoto;
            private TextView tvNama;
            private TextView tvJenisWisata;
            private RatingBar rbRataRating;
            private TextView tvJumlahReview;
            private RelativeLayout rlPopularAll;
        }
    }*/
}
