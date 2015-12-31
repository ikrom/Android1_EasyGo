package tc.easygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        final ImageView homeJelajah = (ImageView)findViewById(R.id.home_jelajah);

        homeJelajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,JelajahActivity.class);
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
                Intent intent = new Intent(HomeActivity.this,MapsActivity.class);
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
        getSupportActionBar().setTitle("Menu");
    }

}
