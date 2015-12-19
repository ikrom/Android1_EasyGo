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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import tc.easygo.adapter.ItemGridAdapter;
import tc.easygo.models.PopularModel;

public class GalleryActivity extends AppCompatActivity {

    public static String KEY_ITEM = "item";
    private PopularModel item;

    private String[] images = new String[]{
            "http://navits.esy.es/images/ijen1.png",
            "http://navits.esy.es/images/glry-1-2.png",
            "http://navits.esy.es/images/glry-1-3.png",
            "http://navits.esy.es/images/glry-1-4.png",
            "http://navits.esy.es/images/glry-1-4.png",
            "http://navits.esy.es/images/glry-1-1.png"
    };

    private GridView gvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gallery");

        //item = (PopularModel) getIntent().getSerializableExtra(KEY_ITEM);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        //cekLog(id);

        gvItem = (GridView)findViewById(R.id.gv_item);
        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(GalleryActivity.this, images);
        gvItem.setAdapter(itemGridAdapter);
        gvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailImageActivity.toDetailImageActivity(GalleryActivity.this, images[position]);
            }
        });

        ImageButton ibNavPreview = (ImageButton)findViewById(R.id.ib_NavPreview);
        ImageButton ibNavJelajah = (ImageButton)findViewById(R.id.ib_NavJelajah);
        ImageButton ibNavPlan = (ImageButton)findViewById(R.id.ib_NavPlan);
        ImageButton ibNavTips = (ImageButton)findViewById(R.id.ib_NavTips);

        ibNavJelajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
