package tc.easygo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tc.easygo.adapter.RencanaAdapter;
import tc.easygo.connection.PostRequest;
import tc.easygo.models.RencanaModel;

public class PlanActivity extends AppCompatActivity implements AsyncResponse {

    private ListView lvRencana;
    private String id_user;
    private ArrayList<RencanaModel> rencanaItemList;
    private String cekJenisRencana = "wisata";

    private String ObjectNamaWisata,ObjectGambarWisata,ObjectNamaEvent,ObjectGambarEvent;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //variable
        lvRencana = (ListView)findViewById(R.id.lv_Rencana);
        Button btnCreatePlan = (Button)findViewById(R.id.btn_create_plan);
        rencanaItemList = new ArrayList<>();

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rencana Perjalanan");

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to activity_plan_create
                Intent intent = new Intent(PlanActivity.this, PlanCreateActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        CheckPreference();
        /*
        Bundle extras = getIntent().getExtras();
        if (extras == null) {cekLog("Intent error");}
        else {
            id_user = extras.getString("id_user");
            //cekLog(id);
        }
        */
        //Post Data
        GetDataPost(id_user);

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

    }

    private void CheckPreference() {
        String cekIdUser = sharedPreferences.getString("id", "");
        //cekLog("id user : " + cekIdUser);
        id_user = cekIdUser;
    }

    private void GetDataPost(String idUser) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id_user", idUser);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("/getrencanaperjalanan");
    }

    public void processFinish(String output) {
        try {
            JSONObject postParentObject = new JSONObject(output);
            JSONArray Objectdata = postParentObject.getJSONArray("data");

            RencanaModel rencanaList = null;
            for(int i=0;i<Objectdata.length();i++){
                rencanaList = new RencanaModel();
                JSONObject finalObjectData = Objectdata.getJSONObject(i);

                String ObjectJenisRencana = finalObjectData.getString("jenis_rencana");
                if(ObjectJenisRencana.equals("wisata")){
                    String ObjectNamaWisata = finalObjectData.getString("nama_wisata");
                    String ObjectGambarWisata = finalObjectData.getString("gambar_wisata");
                    rencanaList.setNama_wisata(ObjectNamaWisata);
                    rencanaList.setGambar_wisata(ObjectGambarWisata);
                }
                else{
                    cekJenisRencana = "event";
                    String ObjectNamaEvent = finalObjectData.getString("nama_event");
                    String ObjectGambarEvent = finalObjectData.getString("gambar_event");
                    rencanaList.setNama_event(ObjectNamaEvent);
                    rencanaList.setGambar_event(ObjectGambarEvent);
                }

                String ObjectCatatan = finalObjectData.getString("catatan");
                String ObjectTanggal= finalObjectData.getString("tanggal");
                String ObjectWaktu = finalObjectData.getString("waktu");

                rencanaList.setJenis_rencana(ObjectJenisRencana);

                rencanaList.setCatatan(ObjectCatatan);
                rencanaList.setTanggal(ObjectTanggal);
                rencanaList.setWaktu(ObjectWaktu);

                rencanaItemList.add(rencanaList);
            }

            lvRencana = (ListView)findViewById(R.id.lv_Rencana);
            RencanaAdapter rencanaAdapter = new RencanaAdapter(PlanActivity.this, rencanaItemList);
            lvRencana.setAdapter(rencanaAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
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
}

