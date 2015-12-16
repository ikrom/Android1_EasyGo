package tc.easygo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import tc.easygo.connection.PostRequest;
import tc.easygo.models.PopularModel;

public class PopularDestinationDetailActivity extends AppCompatActivity implements AsyncResponse {
    public static String KEY_ITEM = "item";
    private PopularModel item;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_destination_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        item = (PopularModel) getIntent().getSerializableExtra(KEY_ITEM);

        String id = String.valueOf(item.getId());
        String idUser = String.valueOf(1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Destinasi Populer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //JSON
        //new PopularDestinationActivity.JSONTask().execute("http://navits.esy.es/index.php/services/getwisatapopuler");


        ImageView ivSlideShow = (ImageView) findViewById(R.id.iv_SlideShow);
        //ivSlideShow.setImageURI("http://s24.postimg.org/i8wloq3ud/foto_1.jpg");
        ImageButton ibNavPlan = (ImageButton) findViewById(R.id.ib_NavPlan);
        ImageButton ibNavTips = (ImageButton) findViewById(R.id.ib_NavTips);

        ibNavPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDestinationDetailActivity.this, PlanActivity.class);
                startActivity(intent);
            }
        });

        ibNavTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopularDestinationDetailActivity.this, TipsActivity.class);
                startActivity(intent);
            }
        });


        GetDataPost(id, idUser);
    }

    private void GetDataPost(String idWisata, String idUser) {
        HashMap<String, String> registerParams = new HashMap<>();
        registerParams.put("id", idWisata);
        registerParams.put("id_user", idUser);

        PostRequest tmp = new PostRequest(this, "http://navits.esy.es/index.php/services/", registerParams);
        tmp.delegate = this;
        tmp.execute("gettourismbyid");
        return;
    }

    @Override
    public void processFinish(String output) {

        Log.d("asd", output);

        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage(output)
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

        //get response
        String finalJson = output.toString();
        JSONObject response = new JSONObject();
        try {
            response = new JSONObject(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String status = "";
        String result = "";
        try {
            status = response.getString("status");
            result = response.getString("result");
        } catch (JSONException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Koneksi Terputus")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            e.printStackTrace();
            //register.setEnabled(true);
            //progress.dismiss();
            return;
        }

        //this.statusField.setText(output);
        //register.setEnabled(true);
        //progress.dismiss();
//        return;
    }
}
