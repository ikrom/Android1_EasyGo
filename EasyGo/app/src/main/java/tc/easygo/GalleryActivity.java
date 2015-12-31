package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tc.easygo.adapter.ItemGridAdapter;
import tc.easygo.connection.PostRequest;
import tc.easygo.models.PopularModel;

public class GalleryActivity extends AppCompatActivity implements AsyncResponse {

    public static String KEY_ITEM = "item";

    private String[] linkGambar;

    private GridView gvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");
/*
        String id = null;
        String idUser = null;


        Bundle extras = getIntent().getExtras();
        if (extras == null) {cekLog("Intent error");}
        else {
            id = extras.getString(id);
            cekLog(id);
            idUser = extras.getString(idUser);
            //cekLog(id);
        }*/

        Bundle getBundle = getIntent().getExtras();

        String id = getBundle.getString("id");
        final String idUser = getBundle.getString("id_user");

        //Post Data
        GetDataPost(id);

        ImageButton ibNavMap = (ImageButton) findViewById(R.id.ib_NavMap);
        ImageButton ibNavPreview = (ImageButton)findViewById(R.id.ib_NavPreview);
        ImageButton ibNavPlan = (ImageButton)findViewById(R.id.ib_NavPlan);
        ImageButton ibNavTips = (ImageButton)findViewById(R.id.ib_NavTips);

        ibNavMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        ibNavPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, PopularDestinationDetailActivity.class);
                startActivity(intent);
            }
        });
        ibNavPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, PlanActivity.class);
                intent.putExtra("id_user", idUser);
                startActivity(intent);
            }
        });
        ibNavTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, TipsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetDataPost(String idWisata) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id", idWisata);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("/getgallerywisatabyid");
        return;
    }

    @Override
    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            JSONArray parentArray = postParentObject.getJSONArray("data");

            //String[] linkGambar;
            ArrayList<String> linkGambarList = new ArrayList<String>();

            //cekLog(String.valueOf(parentArray.length()));
            for(int i=0; i<parentArray.length();i++){
                JSONObject finalObject = parentArray.getJSONObject(i);
                String ObjectRatingData = finalObject.getString("gambar");

                linkGambarList.add(ObjectRatingData.toString());
                //linkGambar[i] = String.valueOf(ObjectRatingData.toString());
            }
            //cekLog(linkGambarList.toString());
            /*ArrayList to Array Conversion */
            String linkGambarString[] = new String[linkGambarList.size()];
            for(int j =0;j<linkGambarList.size();j++){
                linkGambarString[j] = linkGambarList.get(j);
            }
            //cekLog(linkGambarString.toString());
            SetGridView(linkGambarString);

            //cekLog(linkGambar.toString());
            //String[] linkGambarString = new String[linkGambarList.size()];
            //linkGambarString = linkGambarList.toArray(linkGambarString);



        } catch (JSONException e) {
            e.printStackTrace();
            //cekLog("Gangguan koneksi internet");
            cekLog(String.valueOf(e));
        }
    }

    public void cekLog(String iniCek) {
        //Log.d("asd", iniCek);

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

    public void SetGridView(final String[] linkGambar){
        gvItem = (GridView)findViewById(R.id.gv_item);
        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(GalleryActivity.this, linkGambar);
        gvItem.setAdapter(itemGridAdapter);
        gvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailImageActivity.toDetailImageActivity(GalleryActivity.this, linkGambar[position]);
            }
        });
    }
}
