package tc.easygo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CheckPreferenceActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //HAPUS
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        //BATAS HAPUS
        CheckPreference();

    }
    private void CheckPreference() {
        Boolean cekStatus = sharedPreferences.getBoolean("Status", false);

        if(cekStatus.equals(true)) {
            Intent intent = new Intent(CheckPreferenceActivity.this,HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(CheckPreferenceActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

}
