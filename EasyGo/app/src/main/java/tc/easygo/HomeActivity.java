package tc.easygo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class HomeActivity extends AppCompatActivity {

    //id user manual
    String id_user = String.valueOf(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView homePopular = (ImageView)findViewById(R.id.home_popular_destination);
        ImageView homeTips = (ImageView)findViewById(R.id.home_tips);
        ImageView homeAgenda = (ImageView)findViewById(R.id.home_agenda);
        ImageView homePlan = (ImageView)findViewById(R.id.home_plan);
        ImageView homePeta = (ImageView)findViewById(R.id.home_peta);
        ImageView homeGallery = (ImageView)findViewById(R.id.home_gallery);
        final ImageView homeJelajah = (ImageView)findViewById(R.id.home_jelajah);

        homeJelajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,JelajahActivity.class);
                startActivity(intent);
            }
        });

        homeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GalleryHomeActivity.class);
                startActivity(intent);
            }
        });

        homePopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PopularDestinationActivity.class);
                startActivity(intent);
            }
        });

        homePeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PetaActivity.class);
                startActivity(intent);
            }
        });

        homePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PlanActivity.class);
                intent.putExtra("id_user",id_user);
                startActivity(intent);
            }
        });

        homeTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,TipsActivity.class);
                startActivity(intent);
            }
        });
        homeAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CalenderActivity.class);
                startActivity(intent);
            }
        });

        //Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        cekLog("Leave Application?");
        return;
    }

    public void cekLog(String iniCek){
        Log.d("", iniCek);

        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(iniCek)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
